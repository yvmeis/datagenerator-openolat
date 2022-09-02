package com.frentix.datagenerator.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frentix.datagenerator.VOs.UserVO;

import com.frentix.datagenerator.VOs.CourseVO;
import com.frentix.datagenerator.VOs.CurriculumElementVO;
import com.frentix.datagenerator.VOs.CurriculumVO;
import com.frentix.datagenerator.VOs.GroupVO;
import com.frentix.datagenerator.VOs.LoginVO;
import com.frentix.datagenerator.VOs.RolesVO;



@Service
public class UserService {

    private final OpenOlatService openOlatService;
    private final FileService fileService;
    private final ObjectMapper mapper;
    
    public UserService(ObjectMapper mapper, FileService fileService, OpenOlatService openOlatService){

        this.mapper = mapper;
        this.fileService = fileService;
        this.openOlatService = openOlatService;
    }

    /**
     * Makes a call to this UserService's OpenOlatService to retrieve the current Users
     * in OpenOLAT
     * 
     * @return all Users in OpenOlat
     * @throws IOException
     */
    public String getAllUsers(LoginVO loginVO) throws IOException{

        return openOlatService.getUsers(loginVO);
    }

    public Long makeSingularUser(LoginVO loginVO) throws IOException{
        UserVO newUser = new UserVO();
        this.giveFName(newUser);
        this.giveLName(newUser);
        this.giveLogin(newUser);
        this.givePW(newUser);
        this.giveEmail(newUser);

        Long key = this.sendToOpenOLAT(newUser, loginVO);
        return key;
    }

    /**
     * Generates Users, gives them attributes and then sends them to an OpenOlatService
     * to be sent to OpenOLAT
     * 
     * @param number number of new Users to be created
     * @throws IOException
     */
    public void fillOpenOlatWithUsers(int number, LoginVO loginVO) throws IOException{

        ArrayList<UserVO> newUsers = this.generateEmptyUsers(number);

        for (int i=0; i<newUsers.size(); i++){
            this.giveFName(newUsers.get(i));
            this.giveLName(newUsers.get(i));
            this.giveLogin(newUsers.get(i));
            this.givePW(newUsers.get(i));
            this.giveEmail(newUsers.get(i));
            newUsers.get(i).setExternalId("DataGeneratorCreated"+loginVO.getUsername());

            //Puts the User either in a Group,Course,Curriculum or nowhere, with a certain chance.
            int placeDecider = this.genRandomNumber(1, 100);
            if (placeDecider<=5){
                this.sendToOpenOLAT(newUsers.get(i), loginVO);
            }
            else if (placeDecider>75){
                this.sendToGroups(newUsers.get(i), loginVO);
            }
            else if (placeDecider<=75 && placeDecider>50){
                this.sendToCurriculum(newUsers.get(i), loginVO);
            }
            else{
                this.sendToCourses(newUsers.get(i), loginVO);
            }
        }
    }

    /**
     * Generates an ArrayList and fills it with a number of empty UserVO objects
     * 
     * @param number the number of Users to create
     * @return the empty ArrayList of Users
     */
    private ArrayList<UserVO> generateEmptyUsers(int number){

        ArrayList<UserVO> newUsers = new ArrayList<UserVO>();
        
        for (int i=0; i<number; i++){
            UserVO user = new UserVO();
            newUsers.add(user);
        }

        return newUsers;
    }

    /**
     * Generates a reasonably realistic e-mail address for a User
     * 
     * @param userVO the User to get the new email address
     */
    private void giveEmail(UserVO userVO) {
        String emailString = "";
        String first = userVO.getFirstName();
        String last = userVO.getLastName();
        int decider = Integer.parseInt(this.genRandomNumberString(1, 7));

        //Predetermined EMails that make sense
        if (decider == 1){
            emailString = first+"."+last;
        } else if (decider == 2){
            emailString = first.substring(0, 1)+"."+last;
        } else if (decider == 3){
            emailString = first + this.genRandomNumberString(0, 99);
        } else if (decider == 4){
            emailString = last + this.genRandomNumberString(0, 99);
        } else if (decider == 5){
            emailString = first+last;
        } else if (decider == 6){
            emailString = first.substring(0, 2) + last;
        } else if (decider == 7){
            emailString = first + last + this.genRandomString(Integer.parseInt(this.genRandomNumberString(1, 3)));
        }
        userVO.setEmail(emailString+"@example.com");
    }

    /**
     * Generates a password for a User
     * 
     * @param userVO the User to get the new password
     */
    private void givePW(UserVO userVO) {

        int length = 8;
        int leftLimit = 48; // number '1' ascii
        int rightLimit = 122; // letter 'z' ascii
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int) 
            (random.nextFloat() * (rightLimit - leftLimit + 1));

            //leaving out non alphabetical and numerical signs
            if ((randomLimitedInt>90 && randomLimitedInt<97)||(randomLimitedInt>57 && randomLimitedInt<65)){
                i--;
            } else {
                buffer.append((char) randomLimitedInt);
            }
            
        }

        String generatedString = buffer.toString();
        String generalPW = "password";
        userVO.setPassword(generalPW);
    }

    /**
     * Generates a reasonably realistic username/login for a User
     * 
     * @param userVO the User to get the new username/login
     */
    private void giveLogin(UserVO userVO) {
        String loginString = "";
        String first = userVO.getFirstName();
        String last = userVO.getLastName();
        int decider = Integer.parseInt(this.genRandomNumberString(1, 7));

        //Predetermined Username-Types that make sense
        if (decider == 1){
            loginString = first+"."+last;
        } else if (decider == 2){
            loginString = first.substring(0, 1)+"."+last+ this.genRandomNumberString(0, 99);
        } else if (decider == 3){
            loginString = first + this.genRandomNumberString(0, 99);
        } else if (decider == 4){
            loginString = last + first;
        } else if (decider == 5){
            loginString = first+last;
        } else if (decider == 6){
            loginString = last + this.genRandomNumberString(100, 999);
        } else if (decider == 7){
            loginString = first + this.genRandomString(Integer.parseInt(this.genRandomNumberString(1, 3)));
        }
        userVO.setLogin(loginString.toLowerCase());
    }


    /**
     * Sets Users lastname
     * 
     * @param userVO the User to be edited
     * @throws FileNotFoundException
     */
    private void giveLName(UserVO userVO) throws FileNotFoundException {

        String path = "src/main/resources/random/lastnames.csv";
        String name = fileService.returnRandomCSVEntry(path);
        
        userVO.setLastName(name);
    }

    /**
     * Sets Users firstname
     * 
     * @param userVO the User to be edited
     * @throws FileNotFoundException
     */
    private void giveFName(UserVO userVO) throws FileNotFoundException {

        String path = "src/main/resources/random/firstnames.csv";
        String[] name = fileService.returnRandomCSVNameEntry(path);

        userVO.setFirstName(name[0]);
        userVO.setPortrait(name[1]);
    }


    /**
     * Calls the objects OpenOlatService and tells it to add a User to OpenOLAT
     * 
     * @param newUsers Users to be put in OpenOLAT
     * @return key of created User
     * @throws IOException
     */
    private Long sendToOpenOLAT(UserVO newUsers, LoginVO loginVO) throws IOException{

        String gender = newUsers.getPortrait();
        newUsers.setPortrait(null);
        newUsers.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
        String jsonString = mapper.writeValueAsString(newUsers);
        Long key = openOlatService.putUser(jsonString, loginVO);
        if(gender.equals("male")){
            openOlatService.setPortrait(key, fileService.getMalePicture(), loginVO);
        }
        else{
            openOlatService.setPortrait(key, fileService.getFemalePicture(), loginVO);
        }
        return key;
    }

    /**
     * Calls the objects OpenOlatService and tells it to add a User to OpenOLAT Groups
     * 
     * @param newUsers Users to be put in OpenOLAT Groups
     * @throws IOException
     */
    private void sendToGroups(UserVO newUsers, LoginVO loginVO) throws IOException{
        String gender = newUsers.getPortrait();
        newUsers.setPortrait(null);
        newUsers.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
        String jsonString = mapper.writeValueAsString(newUsers);
        Long key = openOlatService.putUserinRandomGroup(jsonString, loginVO);
        if(gender.equals("male")){
            openOlatService.setPortrait(key, fileService.getMalePicture(), loginVO);
        }
        else{
            openOlatService.setPortrait(key, fileService.getFemalePicture(), loginVO);
        }
    }

    /**
     * Calls the objects OpenOlatService and tells it to add a User to OpenOLAT Courses
     * 
     * @param newUsers Users to be put in OpenOLAT Courses
     * @throws IOException
     */
    private void sendToCourses(UserVO newUsers, LoginVO loginVO) throws IOException{

        String gender = newUsers.getPortrait();
        newUsers.setPortrait(null);
        String jsonString = mapper.writeValueAsString(newUsers);
        Long key = openOlatService.putUserinCourse(jsonString, loginVO);
        if(gender.equals("male")){
            openOlatService.setPortrait(key, fileService.getMalePicture(), loginVO);
        }
        else{
            openOlatService.setPortrait(key, fileService.getFemalePicture(), loginVO);
        }
    }

    /**
     * Calls the objects OpenOlatService and tells it to add a User to OpenOLAT Curriculum
     * 
     * @param newUsers Users to be put in OpenOLAT Curriculum
     * @throws IOException
     */
    private void sendToCurriculum(UserVO newUsers, LoginVO loginVO) throws IOException{

        String gender = newUsers.getPortrait();
        newUsers.setPortrait(null);
        newUsers.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
        String jsonString = mapper.writeValueAsString(newUsers);
        Long key = openOlatService.putUserinCurriculum(jsonString, loginVO);
        if(gender.equals("male")){
            openOlatService.setPortrait(key, fileService.getMalePicture(), loginVO);
        }
        else{
            openOlatService.setPortrait(key, fileService.getFemalePicture(), loginVO);
        }
    }

    /**
     * Converts the roles array into RolesVO and chooses Users to appoint the Roles to. Then 
     * sets the Roles by calling OpenOlatService
     * 
     * @param number amount of Users
     * @param roles array of Roles
     * @throws IOException
     */
    public void giveRoles(int number, String[] roles, LoginVO loginVO) throws IOException{

        RolesVO rolesVO = this.roleArrayToRolesVO(roles);       
        String jsonString = mapper.writeValueAsString(rolesVO);
        String currentUsers = openOlatService.getUsers(loginVO);
        UserVO[] users = mapper.readValue(currentUsers, UserVO[].class);
        Long[] keys = new Long[users.length];

        for (int i=0; i<users.length; i++){
                keys[i] = users[i].getKey();
        }

        int count=0;
        //Checks for Users with no Roles yet. As only they should receive new Roles
        ArrayList<UserVO> usersList = this.checkForNoRoles(users, loginVO);

        //Sets the Roles
        for (int i=0; i<usersList.size(); i++){
            openOlatService.setRole(usersList.get(i).getKey(),jsonString, loginVO);
            count++;
            
            if (count==number){
                break;
            }
        }
    }

    /**
     * Retrieves a List of all Users that have at least the given roles
     * 
     * @param roles List of Roles
     * @return List of User Keys
     * @throws IOException
     */
    public ArrayList<Long> getUsersWithRoles(String[] roles, LoginVO loginVO) throws IOException{

        String currentUsers = openOlatService.getUsers(loginVO);
        UserVO[] users = mapper.readValue(currentUsers, UserVO[].class);
        RolesVO[] allRoles = new RolesVO[users.length];
        ArrayList<Long> keys = new ArrayList<>();
        String rolesString = "";
        ArrayList<Long> usersWithRoles = new ArrayList<>();

        // Looks for the Users that have no Roles
        if (roles[0].equals("NOROLES") ){

            ArrayList<UserVO> noRoleUsers = this.checkForNoRoles(users, loginVO);
            for (int i=0; i<noRoleUsers.size();i++){
                keys.add(noRoleUsers.get(i).getKey());
            }
            return keys;
        }

        for (int i=0; i<users.length; i++){
            keys.add(users[i].getKey());
            rolesString = openOlatService.getRoles(keys.get(i), loginVO);
            allRoles[i] = mapper.readValue(rolesString, RolesVO.class);
        }
        
        RolesVO rolesVO = this.roleArrayToRolesVO(roles); 
        for (int i=0; i<users.length; i++){
            if(rolesVO.isSystemAdmin()){
                if(!allRoles[i].isSystemAdmin()){
                    continue;
                }
            }
            if(rolesVO.isOlatAdmin()){
                if(!allRoles[i].isOlatAdmin()){
                    continue;
                }
            }
            if(rolesVO.isUserManager()){
                if(!allRoles[i].isUserManager()){
                    continue;
                }
            }
            if(rolesVO.isGroupManager()){
                if(!allRoles[i].isGroupManager()){
                    continue;
                }
            }
            if(rolesVO.isGuestOnly()){
                if(!allRoles[i].isGuestOnly()){
                    continue;
                }
            }
            if(rolesVO.isInstitutionalResourceManager()){
                if(!allRoles[i].isInstitutionalResourceManager()){
                    continue;
                }
            }
            if(rolesVO.isPoolAdmin()){
                if(!allRoles[i].isPoolAdmin()){
                    continue;
                }
            }
            if(rolesVO.isCurriculumManager()){
                if(!allRoles[i].isCurriculumManager()){
                    continue;
                }
            }
            if(rolesVO.isInvitee()){
                if(!allRoles[i].isInvitee()){
                    continue;
                }
            }
            if(rolesVO.isAuthor()){
                if(!allRoles[i].isAuthor()){
                    continue;
                }
            }
            usersWithRoles.add(keys.get(i));
        }
        

        return usersWithRoles;
    }

    /**
     * Checks for Users without roles
     * 
     * @param users List of Users
     * @return List of Users without roles
     * @throws IOException
     */
    private ArrayList<UserVO> checkForNoRoles(UserVO[] users, LoginVO loginVO) throws IOException{

        ArrayList<UserVO> userList = new ArrayList<>();
        ArrayList<Long> keys = new ArrayList<>();
        String rolesString;
        RolesVO[] allRoles = new RolesVO[users.length];

        for (int i=0; i<users.length; i++){
            keys.add(users[i].getKey());
            rolesString = openOlatService.getRoles(keys.get(i), loginVO);
            allRoles[i] = mapper.readValue(rolesString, RolesVO.class);
        }


        for (int i=0; i<users.length;i++){
            if(allRoles[i].isSystemAdmin()){
                continue;
            }
            if(allRoles[i].isOlatAdmin()){
                continue;
            }
            if(allRoles[i].isUserManager()){
                continue;
            }
            if(allRoles[i].isGroupManager()){
                continue;
            }
            if(allRoles[i].isAuthor()){
                continue;
            }
            if(allRoles[i].isGuestOnly()){
                continue;
            }
            if(allRoles[i].isInstitutionalResourceManager()){
                continue;
            }
            if(allRoles[i].isPoolAdmin()){
                continue;
            }
            if(allRoles[i].isCurriculumManager()){
                continue;
            }
            if(allRoles[i].isInvitee()){
                continue;
            }
            
            userList.add(users[i]);
        }

        return userList;

    }

    /**
     * Converts a String Array into RolesVO
     * 
     * @param roles roles to be converted
     * @return a RolesVO object with the requested Roles
     */
    private RolesVO roleArrayToRolesVO(String [] roles){

        RolesVO rolesVO = new RolesVO();

        for (int i=0; i<roles.length; i++){

            switch(roles[i]){
                
                case "systemAdmin":
                    rolesVO.setSystemAdmin(true);
                    break;
                case "olatAdmin":
                    rolesVO.setOlatAdmin(true);
                    break;
                case "userManager":
                    rolesVO.setUserManager(true);
                    break;
                case "groupManager":
                    rolesVO.setGroupManager(true);
                    break;
                case "author":
                    rolesVO.setAuthor(true);
                    break;
                case "guestOnly":
                    rolesVO.setGuestOnly(true);
                    break;
                case "institutionalResourceManager":
                    rolesVO.setInstitutionalResourceManager(true);
                    break;
                case "poolAdmin":
                    rolesVO.setPoolAdmin(true);
                    break;
                case "curriculumManager":
                    rolesVO.setCurriculumManager(true);
                    break;
                case "invitee":
                    rolesVO.setInvitee(true);
                    break;  
                default:
            }
        }

        return rolesVO;
    }

    /**
     * Deletes all Users on OpenOLAT, except the admin
     * @throws IOException
     */
    public void delUsers(LoginVO loginVO) throws IOException{

        //Retrieving your own Information
        String loggedInUserString = openOlatService.getMe(loginVO);
        UserVO loggedInUser = mapper.readValue(loggedInUserString, UserVO.class);
        Long myKey = loggedInUser.getKey();
        String currentUsers = openOlatService.getUsers(loginVO);
        UserVO[] users = mapper.readValue(currentUsers, UserVO[].class);
        Long key = 1L;

        //Deleting all Users apart from yourself
        for (int i=0; i<users.length; i++){
            key = users[i].getKey();
            if (key.equals(myKey)){
                continue;
            }
            if (!(users[i].getExternalId() == null) && users[i].getExternalId().equals("DataGeneratorCreated"+loginVO.getUsername())){
                openOlatService.delUser(key, loginVO);
            }
        }
    }

    /**
     * Creates custom Users according to the filesystem
     * 
     * @param dirName name of custom template directory
     * @throws IOException
     */
    public void createCustomUsers(String dirName, LoginVO loginVO) throws IOException {

        String dirPath = "src/main/resources/custom/"+dirName+"/users/users.csv";
        ArrayList<ArrayList<String>> content = fileService.returnCompleteCSVEntry(dirPath);
        String coursesString = openOlatService.getCourses(loginVO);
        CourseVO[] courses = mapper.readValue(coursesString, CourseVO[].class);
        String groupsString = openOlatService.getGroups(loginVO);
        GroupVO[] groups = mapper.readValue(groupsString, GroupVO[].class);
        String curriculaString = openOlatService.getCurricula(loginVO);
        CurriculumVO[] curricula = mapper.readValue(curriculaString, CurriculumVO[].class);
        UserVO user = new UserVO();
        RolesVO rolesVO = new RolesVO();
        String jsonString = "";
        Boolean isCourse = false;
        Boolean isGroup = false;
        Boolean isCurriculum = false;
        Boolean isElement = false;
        Boolean isName = false;
        Boolean isRole = false;
        Long currentUserKey = 1L;
        Long currentGroupKey = 1L;
        Long currentCourseKey = 1L;
        Long currentCurriculumKey = 1L;
        Long currentElementKey = 1L;

        for (int i=0; i<content.size(); i++){

            // Setting up the User
            user.setLogin(content.get(i).get(0));
            user.setPassword(content.get(i).get(1));
            user.setFirstName(content.get(i).get(2));
            this.giveLName(user);
            this.giveEmail(user);
            user.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
            jsonString = mapper.writeValueAsString(user);
            currentUserKey = openOlatService.putUser(jsonString, loginVO);

            //Giving the User a Portrait
            if (content.get(i).get(3).equals("female")){
                openOlatService.setPortrait(currentUserKey, fileService.getFemalePicture(), loginVO);
            }
            else{
                openOlatService.setPortrait(currentUserKey, fileService.getMalePicture(), loginVO);
            }

            //Going through the User's users.csv entry
            for (int j=4; j<content.get(i).size(); j++){
                if (content.get(i).get(j).equals("")){
                    continue;
                }
                else if (content.get(i).get(j).equals("COURSE")){
                    isCourse = true;
                    isGroup = false;
                    isCurriculum = false;
                    isElement = false;
                    isName = true;
                    isRole = false;
                    continue;
                }
                else if (content.get(i).get(j).equals("GROUP")){
                    isCourse = false;
                    isGroup = true;
                    isCurriculum = false;
                    isElement = false;
                    isName = true;
                    isRole = false;
                    continue;
                }
                else if (content.get(i).get(j).equals("CURRICULUM")){
                    isCourse = false;
                    isGroup = false;
                    isCurriculum = true;
                    isElement = false;
                    isName = true;
                    isRole = false;
                    continue;
                }
                else if (content.get(i).get(j).equals("ELEMENT")){
                    isCourse = false;
                    isGroup = false;
                    isCurriculum = false;
                    isElement = true;
                    isName = true;
                    isRole = false;
                    continue;
                }
                else if (content.get(i).get(j).equals("ROLES")){
                    isCourse = false;
                    isGroup = false;
                    isCurriculum = false;
                    isElement = false;
                    isName = false;
                    isRole = true;

                    rolesVO.setSystemAdmin(false);
                    rolesVO.setOlatAdmin(false);
                    rolesVO.setUserManager(false);
                    rolesVO.setGroupManager(false);
                    rolesVO.setAuthor(false);
                    rolesVO.setGuestOnly(false);
                    rolesVO.setInstitutionalResourceManager(false);
                    rolesVO.setPoolAdmin(false);
                    rolesVO.setCurriculumManager(false);
                    rolesVO.setInvitee(false);
                    continue;
                }

                //Looking for the correct Key depending on the Name givin in the users.csv
                else if (isName){
                    if (isGroup){
                        for (int k=0; k<groups.length; k++){
                            if (groups[k].getName().equals(content.get(i).get(j))){
                                currentGroupKey = groups[k].getKey();
                                isName=false;
                                break;
                            }
                        }
                    }
                    else if (isCourse){
                        for (int k=0; k<courses.length; k++){
                            if (courses[k].getDisplayName().equals(content.get(i).get(j))){
                                currentCourseKey = courses[k].getKey();
                                isName=false;
                                break;
                            }
                        }
                    }
                    else if (isCurriculum){
                        for (int k=0; k<curricula.length; k++){
                            if (curricula[k].getDisplayName().equals(content.get(i).get(j))){
                                currentCurriculumKey = curricula[k].getKey();
                                isName=false;
                                break;
                            }
                        }
                    }
                    else if (isElement){
                        String elementsString = openOlatService.getElements(currentCurriculumKey, loginVO);
                        CurriculumElementVO[] elements = mapper.readValue(elementsString, CurriculumElementVO[].class);
                        for (int k=0; k<elements.length; k++){
                            if (elements[k].getDisplayName().equals(content.get(i).get(j))){
                                currentElementKey = elements[k].getKey();
                                openOlatService.addUserToCurriculum(currentUserKey, currentElementKey, currentCurriculumKey, loginVO);
                                break;
                            }
                        }
                    }
                }

                //Making the User participant, tutor or owner of a Group/Course
                else{
                    if (isCourse){
                        if (content.get(i).get(j).equals("participant")){
                            openOlatService.addUserToCourse(currentUserKey, currentCourseKey, loginVO);
                        }
                        if (content.get(i).get(j).equals("tutor")){
                            openOlatService.addTutorToCourse(currentUserKey, currentCourseKey, loginVO);
                        }
                        if (content.get(i).get(j).equals("owner")){
                            openOlatService.addOwnerToCourse(currentUserKey, currentCourseKey, loginVO);
                        }
                    }
                    else if (isGroup){
                        if (content.get(i).get(j).equals("participant")){
                            openOlatService.addUserToGroup(currentUserKey, currentGroupKey, loginVO);
                        }
                        if (content.get(i).get(j).equals("owner")){
                            openOlatService.addOwnerToGroup(currentUserKey, currentGroupKey, loginVO);
                        }
                    }

                    //adding all Roles that the User wants one by one
                    else if(isRole){
                        switch(content.get(i).get(j)){
                
                            case "systemAdmin":
                                rolesVO.setSystemAdmin(true);
                                break;
                            case "olatAdmin":
                                rolesVO.setOlatAdmin(true);
                                break;
                            case "userManager":
                                rolesVO.setUserManager(true);
                                break;
                            case "groupManager":
                                rolesVO.setGroupManager(true);
                                break;
                            case "author":
                                rolesVO.setAuthor(true);
                                break;
                            case "guestOnly":
                                rolesVO.setGuestOnly(true);
                                break;
                            case "institutionalResourceManager":
                                rolesVO.setInstitutionalResourceManager(true);
                                break;
                            case "poolAdmin":
                                rolesVO.setPoolAdmin(true);
                                break;
                            case "curriculumManager":
                                rolesVO.setCurriculumManager(true);
                                break;
                            case "invitee":
                                rolesVO.setInvitee(true);
                                break;  
                            default:
                        }

                        //Setting the gathered Role information when the next entry is no longer Role-related
                        if (content.get(i).get(j+1).equals("COURSE") || content.get(i).get(j+1).equals("ELEMENT") || content.get(i).get(j+1).equals("GROUP") || content.get(i).get(j+1).equals("CURRICULUM")){
                            String roleString = mapper.writeValueAsString(rolesVO);
                            openOlatService.setRole(currentUserKey, roleString, loginVO);
                        }
                    }
                }
            }
        }
    }







    /**
     * Generates a random String of a given length. Available characters are all 26 letters and 10 numerals.
     * 
     * @param length length of generated String
     * @return a String of random letters and numbers
     */
    private String genRandomString(int length){

        int leftLimit = 48; // letter '1'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            int randomLimitedInt = leftLimit + (int) 
            (random.nextFloat() * (rightLimit - leftLimit + 1));

            if ((randomLimitedInt>90 && randomLimitedInt<97)||(randomLimitedInt>57 && randomLimitedInt<65)){
               
                i--;

            } else {

                buffer.append((char) randomLimitedInt);
            }
            
        }

        String generatedString = buffer.toString();
        return  generatedString;
    }

    /**
     * Generates a random Integer between min and max and returns it in String form
     * 
     * @param min minimum int to be generated
     * @param max maximum int to be generated
     * @return the String of the generated int
     */
    private String genRandomNumberString(int min, int max){

        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        String generatedNumber = String.valueOf(randomNum);
        return generatedNumber;
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
