package com.frentix.datagenerator.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frentix.datagenerator.VOs.CourseVO;
import com.frentix.datagenerator.VOs.GroupVO;
import com.frentix.datagenerator.VOs.LoginVO;
import com.frentix.datagenerator.VOs.UserVO;

@Service
public class GroupService {
    
    private final FileService fileService;
    private final OpenOlatService openOlatService;
    private final ObjectMapper mapper;
    private final UserService userService;
    
    public GroupService(ObjectMapper mapper, FileService fileService, OpenOlatService openOlatService, UserService userService){

        this.mapper = mapper;
        this.fileService = fileService;
        this.openOlatService = openOlatService;
        this.userService = userService;
    }

    /**
     * Makes a call to this GroupService's OpenOlatService to retrieve the current Groups
     * in OpenOLAT
     * 
     * @return all Groups in OpenOlat
     * @throws IOException
     */
    public String getAllGroups(LoginVO loginVO) throws IOException{

        return openOlatService.getGroups(loginVO);
    }

     /**
     * Generates Groups, gives them attributes and then sends them to an OpenOlatService
     * to be sent to OpenOLAT
     * 
     * @param number number of new Groups to be created
     * @throws IOException
     */
    public void fillOpenOlatWithGroups(int number, LoginVO loginVO) throws IOException{

        ArrayList<GroupVO> newGroups = this.generateEmptyGroups(number);

        for (int i=0; i<newGroups.size(); i++){
            this.giveDisplayName(newGroups.get(i), loginVO);
            newGroups.get(i).setExternalId("DataGeneratorCreated"+loginVO.getUsername());
        }

        //Randomly adds Groups to OLAT or Courses
        int placeDecider = this.genRandomNumber(1, 100);
        if (placeDecider<10){
            this.sendToOpenOLAT(newGroups, loginVO);
        }
        else{
            this.sendToCourses(newGroups, loginVO);
        }

    }

     /**
     * Generates an ArrayList and fills it with a number of empty GroupVO objects
     * 
     * @param number the number of Groups to create
     * @return the empty ArrayList of Groups
     */
    private ArrayList<GroupVO> generateEmptyGroups(int number){

        ArrayList<GroupVO> newGroups = new ArrayList<GroupVO>();
        
        for (int i=0; i<number; i++){
            GroupVO group = new GroupVO();
            newGroups.add(group);
        }

        return newGroups;
    }

    /**
     * Generates a random groupname
     * 
     * @param GroupVO a Group
     * @throws FileNotFoundException
     */
    private void giveDisplayName(GroupVO GroupVO, LoginVO loginVO) throws FileNotFoundException{

        String path = "src/main/resources/random/groupnames.csv";
        String name = fileService.returnRandomCSVEntry(path);
        
        GroupVO.setName(name);
    }

    /**
     * Calls the objects OpenOlatService and tells it to add a Group to OpenOLAT
     * 
     * @param newGroups Groups to be put in OpenOLAT
     * @throws IOException
     */
    private void sendToOpenOLAT(ArrayList<GroupVO> newGroups, LoginVO loginVO) throws IOException{

        for (int i=0; i<newGroups.size(); i++){
            String jsonString = mapper.writeValueAsString(newGroups.get(i));
            GroupVO group = openOlatService.putGroup(jsonString, loginVO);
            Long ownerKey = userService.makeSingularUser(loginVO);
            openOlatService.addOwnerToGroup(ownerKey, group.getKey(), loginVO);
        }
    }

    /**
     * Calls the objects OpenOlatService and tells it to add a Group to OpenOLAT Courses
     * 
     * @param newGroups Groups to be put in OpenOLAT Courses
     * @throws IOException
     */
    private void sendToCourses(ArrayList<GroupVO> newGroups, LoginVO loginVO) throws IOException{

        for (int i=0; i<newGroups.size(); i++){
            String jsonString = mapper.writeValueAsString(newGroups.get(i));
            GroupVO group = openOlatService.putGroupinCourse(jsonString, loginVO);
            Long ownerKey = userService.makeSingularUser(loginVO);
            openOlatService.addOwnerToGroup(ownerKey, group.getKey(), loginVO);
        }
    }

    /**
     * Deletes all Groups on OpenOLAT
     * 
     * @throws IOException
     */
    public void delGroups(LoginVO loginVO) throws IOException{

        String currentGroups = openOlatService.getGroups(loginVO);
        GroupVO[] groups = mapper.readValue(currentGroups, GroupVO[].class);

        for (int i=0; i<groups.length; i++){
            Long key = groups[i].getKey();
            if (!(groups[i].getExternalId() == null) && groups[i].getExternalId().equals("DataGeneratorCreated"+loginVO.getUsername())){
                openOlatService.delGroup(key, loginVO);
            }
        }
    }

    /**
     * Adds Owners to all Groups currently in the system
     * 
     * @throws IOException
     */
    public void addOwnersToGroups(LoginVO loginVO) throws IOException {

        String currentGroupsString = openOlatService.getGroups(loginVO);
        GroupVO[] currentGroupsArray = mapper.readValue(currentGroupsString, GroupVO[].class);


        for (int i=0; i<currentGroupsArray.length; i++){
            Long key = currentGroupsArray[i].getKey();
            this.putOwnersInGroup(key, loginVO);
        }
    }

    /**
     * Adds an Owner to existing Groups
     * 
     * @param groupKey the key of a Group
     * @throws IOException
     */
    public void putOwnersInGroup(Long groupKey, LoginVO loginVO) throws IOException{
        
        String output = openOlatService.getUsers(loginVO);

        UserVO[] users = mapper.readValue(output, UserVO[].class);
        int index = this.genRandomNumber(0, users.length-1);
        Long userKey = users[index].getKey();

        openOlatService.addOwnerToGroup(userKey, groupKey, loginVO);
    }

    /**
     * Creates Groups according to the filesystem
     * 
     * @param dirName custom template directory name
     * @throws IOException
     */
    public void createCustomGroups(String dirName, LoginVO loginVO) throws IOException {

        String dirPath = "src/main/resources/custom/"+dirName+"/groups/groups.csv";
        ArrayList<ArrayList<String>> content = fileService.returnCompleteCSVEntry(dirPath);
        String coursesString = openOlatService.getCourses(loginVO);
        CourseVO[] courses = mapper.readValue(coursesString, CourseVO[].class);
        GroupVO group = new GroupVO();
        String jsonString = "";

        //Iterating through all groups and adding them to OLAT
        for (int i=0; i<content.size(); i++){
            group.setName(content.get(i).get(0));
            group.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
            jsonString = mapper.writeValueAsString(group);
            GroupVO createdGroup = openOlatService.putGroup(jsonString, loginVO);

            //Adding specified number of owners
            for (int j=0; j<Integer.valueOf(content.get(i).get(1)); j++){
                Long ownerKey = userService.makeSingularUser(loginVO);
                openOlatService.addOwnerToGroup(ownerKey, createdGroup.getKey(), loginVO);
            }

            for (int j=2; j<content.get(i).size(); j++){

                //Checking for empty Coursenames
                if (content.get(i).get(j).equals("")){
                    continue;
                }

                //Adding Group to specified Courses
                for (int k=0; k<courses.length; k++){
                    if (content.get(i).get(j).equals(courses[k].getDisplayName())){
                        openOlatService.addGroupToCourse(createdGroup.getKey(), courses[k].getKey(), loginVO);
                    }
                }
            }
        }

    }

    /**
     * Generates a random Integer between min and max 
     * 
     * @param min minimum int to be generated
     * @param max maximum int to be generated
     * @return the  generated int
     */
    private int genRandomNumber(int min, int max){

        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        
        return randomNum;
    }
}
