package com.frentix.datagenerator.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frentix.datagenerator.VOs.CurriculumElementTypeVO;
import com.frentix.datagenerator.VOs.CurriculumElementVO;
import com.frentix.datagenerator.VOs.CurriculumVO;
import com.frentix.datagenerator.VOs.LoginVO;
import com.frentix.datagenerator.VOs.RepositoryEntryVO;

@Service
public class CurriculumService {
    
    private final FileService fileService;
    private final OpenOlatService openOlatService;
    private final ObjectMapper mapper;
    
    public CurriculumService(ObjectMapper mapper, FileService fileService, OpenOlatService openOlatService){

        this.mapper = mapper;
        this.fileService = fileService;
        this.openOlatService = openOlatService;
    }

    /**
     * Gets all Curricula from OpenOLAT
     * 
     * @return 
     * @throws IOException
     */
    public CurriculumVO[] getAllCurricula(LoginVO loginVO) throws IOException{

        String output = openOlatService.getCurricula(loginVO);
        CurriculumVO[] curricula = mapper.readValue(output, CurriculumVO[].class);
        return curricula;
    }

    /**
     * Creates custom Curricula according to the filesystem
     * 
     * @param dirName name of the cutom template directory
     * @throws IOException
     */
    public void createCustomCurriculaFromFiles(String dirName, LoginVO loginVO) throws IOException{

        //Checks amount of Curricula to be created
        dirName = dirName+"/curricula";
        File dir = new File("src/main/resources/custom/"+dirName);
        File[] files = dir.listFiles();

        //Generating Curricula
        for (int i=0; i<files.length; i++){
            this.createCustomCurriculumFromFiles(dirName+"/"+files[i].getName(), loginVO);
        }
    }
    
    /**
     * Creates a Curriculum according to the filesystem
     * 
     * @throws IOException
     */
    public void createCustomCurriculumFromFiles(String dirName, LoginVO loginVO) throws IOException{

        //Create Types
        ArrayList<CurriculumElementTypeVO> curriculumTypes = this.addTypesCustom(dirName, loginVO);

        //Create Elements
        CurriculumVO curriculum = this.addElementsCustom(curriculumTypes, dirName, loginVO);

        //Fill Elements
        this.fillElementsWithCoursesCustom(curriculum, dirName, loginVO);
    }

    /**
     * Creates a Curriculum according to the filesystem
     * 
     * @throws IOException
     */
    public void createRandomCurriculumFromFiles(LoginVO loginVO) throws IOException{

        //Create Types
        ArrayList<CurriculumElementTypeVO> curriculumTypes = this.addTypesRandom(loginVO);

        //Create Elements
        CurriculumVO curriculum = this.addElementsRandom(curriculumTypes, loginVO);

        //Fill Elements
        this.fillElementsWithCoursesRandom(curriculum, loginVO);
    }

    /**
     * Adds Types to the Curriculum
     * 
     * @return List of TypeKeys
     * @throws IOException
     */
    private ArrayList<CurriculumElementTypeVO> addTypesCustom(String dirName, LoginVO loginVO) throws IOException{

        String jsonString = "";
        String returnString = "";
        CurriculumElementTypeVO currType = new CurriculumElementTypeVO();
        ArrayList<CurriculumElementTypeVO> currList = new ArrayList<CurriculumElementTypeVO>();
        ArrayList<ArrayList<String>> content = fileService.returnCompleteCSVEntry("src/main/resources/custom/"+dirName+"/types.csv");


        for (int i=0; i<content.size(); i++){
            currType.setIdentifier(content.get(i).get(1));
            currType.setDisplayName(content.get(i).get(0));
            jsonString = mapper.writeValueAsString(currType);
            returnString = openOlatService.addTypeToCurriculum(jsonString, loginVO);
            currList.add(mapper.readValue(returnString, CurriculumElementTypeVO.class));
            if (i>0){
                openOlatService.curriculumAddSubTypeToType(currList.get(i-1).getKey(), currList.get(i).getKey(), loginVO);
            }
        }
        return currList;
    }

    /**
     * Adds Types to the Curriculum
     * 
     * @return List of TypeKeys
     * @throws IOException
     */
    private ArrayList<CurriculumElementTypeVO> addTypesRandom(LoginVO loginVO) throws IOException{

        String jsonString = "";
        String returnString = "";
        CurriculumElementTypeVO currType = new CurriculumElementTypeVO();
        ArrayList<CurriculumElementTypeVO> currList = new ArrayList<CurriculumElementTypeVO>();
        ArrayList<ArrayList<String>> content = fileService.returnCompleteCSVEntry("src/main/resources/random/curriculum/types.csv");


        for (int i=0; i<content.size(); i++){
            currType.setIdentifier(content.get(i).get(1));
            currType.setDisplayName(content.get(i).get(0));
            jsonString = mapper.writeValueAsString(currType);
            returnString = openOlatService.addTypeToCurriculum(jsonString, loginVO);
            currList.add(mapper.readValue(returnString, CurriculumElementTypeVO.class));
            if (i>0){
                openOlatService.curriculumAddSubTypeToType(currList.get(i-1).getKey(), currList.get(i).getKey(), loginVO);
            }
        }
        return currList;
    }

    /**
     * Creates a specific custom Curriculum
     * 
     * @param types A list of the Curriculum's types
     * @param dirPath name of the custom template directory
     * @return the created Curriculum
     * @throws IOException
     */
    private CurriculumVO addElementsCustom(ArrayList<CurriculumElementTypeVO> types, String dirPath, LoginVO loginVO) throws IOException{

        //Retrieves the Curriculum name
        ArrayList<ArrayList<String>> currData = fileService.returnCompleteCSVEntry("src/main/resources/custom/"+dirPath+"/curriculumname.csv");

        //Sets up the Currculum
        CurriculumVO curriculum = new CurriculumVO();
        curriculum.setDisplayName(currData.get(0).get(0));
        curriculum.setIdentifier(currData.get(0).get(1));
        curriculum.setOrganisationKey(1L);
        String currString = mapper.writeValueAsString(curriculum);
        currString = openOlatService.makeCurriculum(currString, loginVO);
        curriculum = mapper.readValue(currString, CurriculumVO.class);
        
        String elementString = "";
        CurriculumElementVO createdElement = new CurriculumElementVO();
        CurriculumElementVO newElement = new CurriculumElementVO();
        ArrayList<ArrayList<String>> levelsArray = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> pathsArray = new ArrayList<ArrayList<String>>();
        
        levelsArray = fileService.returnCompleteCSVEntry("src/main/resources/custom/"+dirPath+"/levels.csv");
        for (int i=0; i<levelsArray.size(); i++){
            pathsArray.add(fileService.getPath(levelsArray.get(i).get(0)));
        }

        int maxDepth = 0;
        for (int i=0; i<levelsArray.size(); i++){
            maxDepth = Math.max(maxDepth, pathsArray.get(i).size());
            if(pathsArray.get(i).size()==2){
                newElement.setDisplayName(levelsArray.get(i).get(1));
                newElement.setIdentifier(pathsArray.get(i).get(1));
                newElement.setParentElementKey(null);
                for (int j=0; j<types.size(); j++){
                    if (types.get(j).getDisplayName().equals(levelsArray.get(i).get(2))){
                        newElement.setCurriculumElementTypeKey(types.get(j).getKey());
                    }
                }
            String input = mapper.writeValueAsString(newElement);
            elementString = openOlatService.addBaseElementToCurriculum(curriculum.getKey(), input, loginVO);
            createdElement = mapper.readValue(elementString, CurriculumElementVO.class);
            levelsArray.get(i).add(createdElement.getKey()+"");
            }
        }

        int depthCounter = 2;
        String parentPath = "";
        String childPath = "";
        
        while (depthCounter<maxDepth){
            for (int i=0; i<levelsArray.size(); i++){
                if ((pathsArray.get(i).size()==depthCounter+1)){
                    newElement.setDisplayName(levelsArray.get(i).get(1));
                    newElement.setIdentifier(pathsArray.get(i).get(pathsArray.get(i).size()-1));
                    for (int j=0; j<types.size(); j++){
                        if (types.get(j).getDisplayName().equals(levelsArray.get(i).get(2))){
                            newElement.setCurriculumElementTypeKey(types.get(j).getKey());
                        }
                    }
                    childPath="";
                    for (int j=1; j<pathsArray.get(i).size()-1; j++){
                        childPath = childPath + "/" + pathsArray.get(i).get(j);
                    }
                    for (int j=0; j<levelsArray.size(); j++){
                        parentPath = "";
                        for (int k=1; k<pathsArray.get(j).size(); k++){
                            parentPath = parentPath + "/" + pathsArray.get(j).get(k);
                        }
                        if (childPath.equals(parentPath)){
                            newElement.setParentElementKey(Long.parseLong(levelsArray.get(j).get(3)));
                        }
                    }
                    String input = mapper.writeValueAsString(newElement);
                    elementString = openOlatService.addBaseElementToCurriculum(curriculum.getKey(), input, loginVO);
                    createdElement = mapper.readValue(elementString, CurriculumElementVO.class);
                    levelsArray.get(i).add(createdElement.getKey()+"");
                }
            }
            depthCounter+=1;
        }
        return curriculum;
    }

    /**
     * Adds Elements to the Curriculum
     * 
     * @param typeKeys List of TypeKeys
     * @return the finished Curriculum structure
     * @throws IOException
     */
    private CurriculumVO addElementsRandom(ArrayList<CurriculumElementTypeVO> curriculumTypes, LoginVO loginVO) throws IOException{

        //TODO make random
        //Gather data from filesystem
        ArrayList<ArrayList<String>> currData = fileService.returnCompleteCSVEntry("src/main/resources/random/curriculum/curriculumname.csv");
        ArrayList<ArrayList<String>> fileDataTier2 = fileService.returnCompleteCSVEntry("src/main/resources/random/curriculum/levels/level0.csv");

        //Set up the Curriculum
        CurriculumVO curriculum = new CurriculumVO();
        curriculum.setDisplayName(currData.get(0).get(0));
        curriculum.setIdentifier(currData.get(0).get(1));
        curriculum.setOrganisationKey(1L);
        String currString = mapper.writeValueAsString(curriculum);
        currString = openOlatService.makeCurriculum(currString, loginVO);
        curriculum = mapper.readValue(currString, CurriculumVO.class);

        //Set up a baseElement to keep count of keys
        CurriculumElementVO baseElement = new CurriculumElementVO();
        baseElement.setIdentifier("");
        baseElement.setDisplayName("");
        String baseElementString = mapper.writeValueAsString(baseElement);
        baseElementString = openOlatService.addBaseElementToCurriculum(curriculum.getKey(),baseElementString, loginVO);
        baseElement = mapper.readValue(baseElementString, CurriculumElementVO.class);
        
        Long baseKey = baseElement.getKey();
        CurriculumElementVO newElement = new CurriculumElementVO();
        Long currKey = baseKey;
        Long currTier1Key = baseKey;

        //Iterates through first depth of Elements
        for (int i=0;i<fileDataTier2.size();i++){

            newElement.setDisplayName(fileDataTier2.get(i).get(0));
            newElement.setIdentifier(newElement.getDisplayName());
            newElement.setCurriculumElementTypeKey(curriculumTypes.get(0).getKey());
            newElement.setParentElementKey(null);
            String input = mapper.writeValueAsString(newElement);
            openOlatService.addElementsToCurriculum(curriculum.getKey(), input, loginVO);
            currKey++;
            currTier1Key = currKey;

            //Iterates through second depth of Elements
            for (int j=1;j<fileDataTier2.get(i).size();j++){

                //Checks for empty Elements
                if (fileDataTier2.get(i).get(j).equals("")){
                    continue;
                }

                newElement.setDisplayName(fileDataTier2.get(i).get(j));
                newElement.setIdentifier(newElement.getDisplayName());
                newElement.setCurriculumElementTypeKey(curriculumTypes.get(1).getKey());
                newElement.setParentElementKey(currTier1Key);
                String input2 = mapper.writeValueAsString(newElement);
                openOlatService.addElementsToCurriculum(curriculum.getKey(), input2, loginVO);
                currKey++;
            }
        }

        //Would delete baseElement, but is currently not possible in OpenOLAT
        openOlatService.deleteBaseElement(curriculum.getKey(),baseKey);
        return curriculum;
    }

    /**
     * Fills the CurriculumElements with Courses according to the filesystem
     * 
     * @param curriculum a Curriculum
     * @throws IOException
     */
    private void fillElementsWithCoursesRandom(CurriculumVO curriculum, LoginVO loginVO) throws IOException{

        //Setting up variables and retrieving data
        Long curriculumKey = curriculum.getKey();
        ArrayList<ArrayList<String>> courseAllocationData = fileService.returnCompleteCSVEntry("src/main/resources/random/curriculum/coursecurriculumrelations.csv");
        String entryString = openOlatService.getRepoEntries(loginVO);
        RepositoryEntryVO[] entries = mapper.readValue(entryString, RepositoryEntryVO[].class);
        String currElementsString = openOlatService.getElements(curriculumKey, loginVO);
        CurriculumElementVO[] currElements = mapper.readValue(currElementsString, CurriculumElementVO[].class);

        //Iterating through all repo entries
        for (int i=0; i<entries.length; i++){
            String currentCourseName = entries[i].getDisplayname();

            //Iterating through Course-Curriculum relations
            for (int j=0; j<courseAllocationData.size(); j++){
                for (int k=1; k<courseAllocationData.get(j).size(); k++){
                    if (courseAllocationData.get(j).get(k).equals(currentCourseName)){

                        //Iterating through Curriculum Elements
                        for (int l=0; l<currElements.length; l++){
                            if (currElements[l].getDisplayName().equals(courseAllocationData.get(j).get(0))){
                                openOlatService.addCourseToCurriculum(curriculumKey, entries[i].getKey(), currElements[l].getKey(), loginVO);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Fills the CurriculumElements with Courses according to the filesystem
     * 
     * @param curriculum a Curriculum
     * @throws IOException
     */
    private void fillElementsWithCoursesCustom(CurriculumVO curriculum, String dirName, LoginVO loginVO) throws IOException{

        //Setting up variables and retrieving data
        Long curriculumKey = curriculum.getKey();
        ArrayList<ArrayList<String>> courseAllocationData = fileService.returnCompleteCSVEntry("src/main/resources/custom/"+dirName+"/coursecurriculumrelations.csv");
        String entryString = openOlatService.getRepoEntries(loginVO);
        RepositoryEntryVO[] entries = mapper.readValue(entryString, RepositoryEntryVO[].class);
        String currElementsString = openOlatService.getElements(curriculumKey, loginVO);
        CurriculumElementVO[] currElements = mapper.readValue(currElementsString, CurriculumElementVO[].class);

        //Iterating through all repo entries
        for (int i=0; i<entries.length; i++){
            String currentCourseName = entries[i].getDisplayname();

            //Iterating through Course-Curriculum relations
            for (int j=0; j<courseAllocationData.size(); j++){
                for (int k=1; k<courseAllocationData.get(j).size(); k++){
                    if (courseAllocationData.get(j).get(k).equals(currentCourseName)){

                        //Iterating through Curriculum Elements
                        for (int l=0; l<currElements.length; l++){
                            if (currElements[l].getDisplayName().equals(courseAllocationData.get(j).get(0))){
                                openOlatService.addCourseToCurriculum(curriculumKey, entries[i].getKey(), currElements[l].getKey(), loginVO);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
