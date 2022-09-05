package com.frentix.datagenerator.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frentix.datagenerator.VOs.LoginVO;
import com.frentix.datagenerator.VOs.RepositoryEntryVO;
import com.frentix.datagenerator.VOs.TaxonomyLevelTypeVO;
import com.frentix.datagenerator.VOs.TaxonomyLevelVO;
import com.frentix.datagenerator.VOs.TaxonomyVO;

@Service
public class TaxonomyService {
    
    private final FileService fileService;
    private final OpenOlatService openOlatService;
    private final ObjectMapper mapper;
    
    public TaxonomyService(ObjectMapper mapper, FileService fileService, OpenOlatService openOlatService){

        this.mapper = mapper;
        this.fileService = fileService;
        this.openOlatService = openOlatService;
    }

    /**
     * Gets all Taxonomies on OpenOLAT
     * 
     * @return Taxonomies
     * @throws IOException
     */
    public TaxonomyVO[] getTaxonomies(LoginVO loginVO) throws IOException{

        String output = openOlatService.getTaxonomies(loginVO);
        String editedString = output.substring(14);
        TaxonomyVO[] taxonomy = mapper.readValue(editedString, TaxonomyVO[].class);
        return taxonomy;
    }

    /**
     * Adds 3 Types to a Taxonomy
     * 
     * @param key the Taxonomy Key
     * @throws IOException
     */
    private ArrayList<TaxonomyLevelTypeVO> addTypesCustom(int key, LoginVO loginVO, String dirName) throws IOException{
        String jsonString = "";
        String returnString = "";
        TaxonomyLevelTypeVO taxType = new TaxonomyLevelTypeVO();
        ArrayList<TaxonomyLevelTypeVO> taxList = new ArrayList<TaxonomyLevelTypeVO>();
        ArrayList<ArrayList<String>> content = fileService.returnCompleteCSVEntry(dirName+"/types.csv");


        //Iterating through all typenames and adding them
        for (int i=0; i<content.size(); i++){
            taxType.setIdentifier(content.get(i).get(1));
            taxType.setDisplayName(content.get(i).get(0));
            taxType.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
            taxType.setVisible(true);
            jsonString = mapper.writeValueAsString(taxType);
            returnString = openOlatService.addTypeToTaxonomy(key, jsonString, loginVO);
            taxList.add(mapper.readValue(returnString, TaxonomyLevelTypeVO.class));
            if (i>0){
                openOlatService.taxonomyAddSubTypeToType(key, taxList.get(i-1).getKey(), taxList.get(i).getKey(), loginVO);
            }
        }
        return taxList;
    }

    /**
     * Adds 3 Types to a Taxonomy
     * 
     * @param key the Taxonomy Key
     * @throws IOException
     */
    private ArrayList<TaxonomyLevelTypeVO> addTypesRandom(int key, LoginVO loginVO) throws IOException{
        String jsonString = "";
        String returnString = "";
        TaxonomyLevelTypeVO taxType = new TaxonomyLevelTypeVO();
        ArrayList<TaxonomyLevelTypeVO> taxList = new ArrayList<TaxonomyLevelTypeVO>();
        ArrayList<ArrayList<String>> content = fileService.returnCompleteCSVEntry("src/main/resources/random/taxonomy/types.csv");

        //Iterating though all typenames and adding them
        for (int i=0; i<content.size(); i++){
            taxType.setIdentifier(content.get(i).get(1));
            taxType.setDisplayName(content.get(i).get(0));
            taxType.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
            taxType.setVisible(true);
            jsonString = mapper.writeValueAsString(taxType);
            returnString = openOlatService.addTypeToTaxonomy(key, jsonString, loginVO);
            taxList.add(mapper.readValue(returnString, TaxonomyLevelTypeVO.class));
            if (i>0){
                openOlatService.taxonomyAddSubTypeToType(key, taxList.get(i-1).getKey(), taxList.get(i).getKey(), loginVO);
            }
        }
        return taxList;
    }

    /**
     * Adss Levels to a Taxonomy according to the filesystem
     * 
     * @param key Taxonomy Key
     * @param dirName name of custom template directory
     * @throws IOException
     */
    public void addLevelsCustomOld(Long longKey, String dirName, LoginVO loginVO) throws IOException
    {

        //Creating a baseLevel to start counting Keys
        int key = longKey.intValue();
        TaxonomyLevelVO baseLevel = new TaxonomyLevelVO();
        baseLevel.setIdentifier("bslvl");
        baseLevel.setDisplayName("bsnm");
        String jsonString = mapper.writeValueAsString(baseLevel);
        String returnedBaseLevelString = openOlatService.addBaseLevelToTaxonomy(key, jsonString, loginVO);
        TaxonomyLevelVO returnedBaseLevel = mapper.readValue(returnedBaseLevelString, TaxonomyLevelVO.class);
        Long baseKey = returnedBaseLevel.getKey();
        Long currKey = baseKey+1;

        //Retrieve types and prepare Arrays
        TaxonomyLevelVO newTxLvl = new TaxonomyLevelVO();
        ArrayList<TaxonomyLevelTypeVO> types = this.addTypesCustom(key, loginVO, dirName);
        ArrayList<ArrayList<ArrayList<String>>> megArray = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<String>> levelToKeyArray = new ArrayList<ArrayList<String>>();

        //Checking how many levels the taxonomy should have
        File dir = new File(dirName+"/levels");
        File[] files = dir.listFiles();
        int length = files.length;
        if (!(length>0)){
            return;
        }

        //Adding data to the mega Array for each depth of levels
        for (int i=0; i<length; i++){
            megArray.add(fileService.returnCompleteCSVEntry(dir+"/level"+i+".csv"));
        }

        //Adding all highest-order levels to taxonomy
        for (int i=0; i<megArray.get(0).size(); i++){
            ArrayList<String> entry = new ArrayList<String>();
            entry.add(megArray.get(0).get(i).get(0));
            entry.add(""+currKey);
            levelToKeyArray.add(entry);
            currKey++;

            newTxLvl.setDisplayName(megArray.get(0).get(i).get(0));
            newTxLvl.setIdentifier(newTxLvl.getDisplayName());
            newTxLvl.setTypeKey(types.get(0).getKey());
            newTxLvl.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
            newTxLvl.setParentKey(null);
            String input = mapper.writeValueAsString(newTxLvl);
            openOlatService.addLevelsToTaxonomy(key, input, loginVO);
        }

        //Iterating through all other levels
        for (int i=0; i<megArray.size(); i++){
            for (int j=0; j<megArray.get(i).size(); j++){
                for (int k=1; k<megArray.get(i).get(j).size(); k++){

                    //Checking for empty levels
                    if (megArray.get(i).get(j).get(k).equals("")){
                        continue;
                    }

                    ArrayList<String> entry = new ArrayList<String>();
                    entry.add(megArray.get(i).get(j).get(k));
                    entry.add(""+currKey);
                    levelToKeyArray.add(entry);
                    currKey++;

                    String parentName = megArray.get(i).get(j).get(0);
                    Long parentKey = 0L;
                    for (int l=0;l<levelToKeyArray.size();l++){
                        if (levelToKeyArray.get(l).get(0).equals(parentName)){
                            parentKey = Long.parseLong(levelToKeyArray.get(l).get(1));
                            break;
                        }
                    }   

                    //Preparing the level and adding it to the Taxonomy
                    newTxLvl.setDisplayName(megArray.get(i).get(j).get(k));
                    newTxLvl.setIdentifier(newTxLvl.getDisplayName());
                    newTxLvl.setTypeKey(types.get(i+1).getKey());
                    newTxLvl.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
                    newTxLvl.setParentKey(parentKey);
                    String input = mapper.writeValueAsString(newTxLvl);
                    openOlatService.addLevelsToTaxonomy(key, input, loginVO);
                }
            }
        }

        //Delete baseLevel to clean taxonomy
        openOlatService.deleteTaxLevel(key,baseKey, loginVO);
    }

    public void addLevelsCustom(Long longKey, String dirName, LoginVO loginVO) throws IOException
    {

        //Retrieve types and prepare Arrays
        int key = longKey.intValue();
        String levelString = "";
        TaxonomyLevelVO createdLevel = new TaxonomyLevelVO();
        TaxonomyLevelVO newTxLvl = new TaxonomyLevelVO();
        ArrayList<TaxonomyLevelTypeVO> types = this.addTypesCustom(key, loginVO, dirName);
        ArrayList<ArrayList<String>> levelsArray = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> pathsArray = new ArrayList<ArrayList<String>>();

        levelsArray = fileService.returnCompleteCSVEntry(dirName+"/levels.csv");
        for (int i=0; i<levelsArray.size(); i++){
            pathsArray.add(fileService.getPath(levelsArray.get(i).get(0)));
        }

        int maxDepth = 0;
        for (int i=0; i<levelsArray.size(); i++){
            maxDepth = Math.max(maxDepth, pathsArray.get(i).size());
            if(pathsArray.get(i).size()==2){
                newTxLvl.setDisplayName(levelsArray.get(i).get(1));
                newTxLvl.setIdentifier(pathsArray.get(i).get(1));
                newTxLvl.setParentKey(null);
                newTxLvl.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
                for (int j=0; j<types.size(); j++){
                    if (types.get(j).getDisplayName().equals(levelsArray.get(i).get(2))){
                        newTxLvl.setTypeKey(types.get(j).getKey());
                    }
                }
            String input = mapper.writeValueAsString(newTxLvl);
            levelString = openOlatService.addBaseLevelToTaxonomy(key, input, loginVO);
            createdLevel = mapper.readValue(levelString, TaxonomyLevelVO.class);
            levelsArray.get(i).add(createdLevel.getKey()+"");
            }
        }

        int depthCounter = 2;
        String parentPath = "";
        String childPath = "";

        while (depthCounter<maxDepth){
            for (int i=0; i<levelsArray.size(); i++){
                if ((pathsArray.get(i).size()==depthCounter+1)){
                    newTxLvl.setDisplayName(levelsArray.get(i).get(1));
                    newTxLvl.setIdentifier(pathsArray.get(i).get(pathsArray.get(i).size()-1));
                    newTxLvl.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
                    for (int j=0; j<types.size(); j++){
                        if (types.get(j).getDisplayName().equals(levelsArray.get(i).get(2))){
                            newTxLvl.setTypeKey(types.get(j).getKey());
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
                            newTxLvl.setParentKey(Long.parseLong(levelsArray.get(j).get(3)));
                        }
                    }
                    String input = mapper.writeValueAsString(newTxLvl);
                    levelString = openOlatService.addBaseLevelToTaxonomy(key, input, loginVO);
                    createdLevel = mapper.readValue(levelString, TaxonomyLevelVO.class);
                    levelsArray.get(i).add(createdLevel.getKey()+"");
                }
            }
            depthCounter+=1;
        }
    }

    /**
     * Adds many Levels to a Taxonomy
     * 
     * @param key the Taxonomy Key
     * @throws IOException
     */
    public void addLevelsRandom(int key, LoginVO loginVO) throws IOException{
        //TODO make actually random
        ArrayList<TaxonomyLevelTypeVO> types = this.addTypesRandom(key, loginVO);

        //Creating a baseLevel from where to start counting Keys
        TaxonomyLevelVO baseLevel = new TaxonomyLevelVO();
        baseLevel.setIdentifier("bslvl");
        baseLevel.setDisplayName("bsnm");
        String jsonString = mapper.writeValueAsString(baseLevel);
        String returnedBaseLevelString = openOlatService.addBaseLevelToTaxonomy(key,jsonString, loginVO);
        TaxonomyLevelVO returnedBaseLevel = mapper.readValue(returnedBaseLevelString, TaxonomyLevelVO.class);
        Long baseKey = returnedBaseLevel.getKey();

        //Retrieving the required data from the filesystem
        ArrayList<ArrayList<String>> fileData = fileService.returnCompleteCSVEntry("src/main/resources/random/taxonomy/levels/level0.csv");
        ArrayList<ArrayList<String>> fileDataTier2 = fileService.returnCompleteCSVEntry("src/main/resources/random/taxonomy/levels/level1.csv");
        TaxonomyLevelVO newTxLvl = new TaxonomyLevelVO();
       
        Long currKey = baseKey;
        Long currTier1Key = baseKey;
        Long currTier2Key = baseKey;

        //Iterating through the first level
        for (int i=0;i<fileData.size();i++){
            newTxLvl.setDisplayName(fileData.get(i).get(0));
            newTxLvl.setIdentifier(newTxLvl.getDisplayName());
            newTxLvl.setTypeKey(types.get(0).getKey());
            newTxLvl.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
            newTxLvl.setParentKey(null);
            String input = mapper.writeValueAsString(newTxLvl);
            openOlatService.addLevelsToTaxonomy(key, input, loginVO);
            currKey++;
            currTier1Key = currKey;

            for (int j=1;j<fileData.get(i).size();j++){
                if (fileData.get(i).get(j).equals("")){
                    continue;
                }
                newTxLvl.setDisplayName(fileData.get(i).get(j));
                newTxLvl.setIdentifier(newTxLvl.getDisplayName());
                newTxLvl.setTypeKey(types.get(1).getKey());
                newTxLvl.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
                newTxLvl.setParentKey(currTier1Key);
                String input2 = mapper.writeValueAsString(newTxLvl);
                openOlatService.addLevelsToTaxonomy(key, input2, loginVO);
                currKey++;
                currTier2Key = currKey;

                //Iterating through the second level
                for (int k=0;k<fileDataTier2.size();k++){   
                    if (!fileDataTier2.get(k).get(0).equals(fileData.get(i).get(j))){
                        continue;
                    }
                    for (int l=1; l<fileDataTier2.get(k).size(); l++){
                        if (fileDataTier2.get(k).get(l).equals("")){
                            continue;
                        }
                        newTxLvl.setDisplayName(fileDataTier2.get(k).get(l));
                        newTxLvl.setIdentifier(newTxLvl.getDisplayName());
                        newTxLvl.setTypeKey(types.get(2).getKey());
                        newTxLvl.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
                        newTxLvl.setParentKey(currTier2Key);
                        String input3 = mapper.writeValueAsString(newTxLvl);
                        openOlatService.addLevelsToTaxonomy(key, input3, loginVO);
                        currKey++;
                    }
                }
            }
        }

        
        //Deleting the baselevel to clean up
        openOlatService.deleteTaxLevel(key,baseKey, loginVO);
        Long taxKey = Long.valueOf(key);
        this.addLevelsToRepoEntriesRandom(taxKey, loginVO);
    }

    /**
     * Adds TaxonomyLevels to Courses
     * 
     * @param taxonomyKey a Taxonomy Key
     * @throws IOException
     */
    private void addLevelsToRepoEntriesRandom(Long taxonomyKey, LoginVO loginVO) throws IOException{

        ArrayList<ArrayList<String>> courseAllocationData = fileService.returnCompleteCSVEntry("src/main/resources/random/taxonomy/coursetaxonomyrelations.csv");
        String entryString = openOlatService.getRepoEntries(loginVO);
        RepositoryEntryVO[] entries = mapper.readValue(entryString, RepositoryEntryVO[].class);
        String taxLevelString = openOlatService.getTaxLevels(taxonomyKey, loginVO);
        TaxonomyLevelVO[] taxLevels = mapper.readValue(taxLevelString, TaxonomyLevelVO[].class);

        //Iterating through all repo entries
        for (int i=0; i<entries.length; i++){
            String currentCourseName = entries[i].getDisplayname();

            //Iterating thorugh the relations data from the coursetaxonomyrelations.csv file
            for (int j=0; j<courseAllocationData.size(); j++){
                for (int k=1; k<courseAllocationData.get(j).size(); k++){
                    if (courseAllocationData.get(j).get(k).equals(currentCourseName)){

                        //Iterating through all taxonomy levels
                        for (int l=0; l<taxLevels.length; l++){
                            if (taxLevels[l].getDisplayName().equals(courseAllocationData.get(j).get(0))){
                                openOlatService.addTaxLevelToCourse(entries[i].getKey(), taxLevels[l].getKey(), loginVO);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

     /**
     * Adds TaxonomyLevels to Courses
     * 
     * @param taxonomyKey a Taxonomy Key
     * @throws IOException
     */
    private void addLevelsToRepoEntriesCustom(Long taxonomyKey, String dirName, LoginVO loginVO) throws IOException{

        ArrayList<ArrayList<String>> courseAllocationData = fileService.returnCompleteCSVEntry(dirName+"/coursetaxonomyrelations.csv");
        String entryString = openOlatService.getRepoEntries(loginVO);
        RepositoryEntryVO[] entries = mapper.readValue(entryString, RepositoryEntryVO[].class);
        String taxLevelString = openOlatService.getTaxLevels(taxonomyKey, loginVO);
        TaxonomyLevelVO[] taxLevels = mapper.readValue(taxLevelString, TaxonomyLevelVO[].class);

        //Iterating through all repo entries
        for (int i=0; i<entries.length; i++){
            String currentCourseName = entries[i].getDisplayname();

            //Iterating thorugh the relations data from the coursetaxonomyrelations.csv file
            for (int j=0; j<courseAllocationData.size(); j++){
                for (int k=1; k<courseAllocationData.get(j).size(); k++){
                    if (courseAllocationData.get(j).get(k).equals(currentCourseName)){

                        //Iterating through all taxonomy levels
                        for (int l=0; l<taxLevels.length; l++){
                            if (taxLevels[l].getDisplayName().equals(courseAllocationData.get(j).get(0))){
                                openOlatService.addTaxLevelToCourse(entries[i].getKey(), taxLevels[l].getKey(), loginVO);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * 
     * @param dirName
     * @throws IOException
     */
    public void fillCustomTaxonomiesFromFiles(String dirName, LoginVO loginVO) throws IOException {

        //Checks amount of Taxonomies to be filled
        dirName = dirName+"/taxonomies";
        File dir = new File("src/main/resources/custom/"+dirName);
        File[] files = dir.listFiles();
        String fileName = "";
        String exactDir = "";

        //Retrieves all Taxonomies from OpenOLAT
        TaxonomyVO[] taxonomies = this.getTaxonomies(loginVO);
        
        //Iterating through all Taxonomy files
        for (int i=0; i<files.length; i++){

            //Getting the Taxonomy name from the filesystem
            fileName = files[i].getName();
            exactDir = "src/main/resources/custom/"+dirName+"/"+fileName;
            ArrayList<ArrayList<String>> csvEntry = fileService.returnCompleteCSVEntry(exactDir+"/taxonomyname.csv");

            //Iterating through all Taxonomies
            for (int j=0; j<taxonomies.length; j++){

                //Adds Levels
                if(csvEntry.get(0).get(0).equals(taxonomies[j].getDisplayName())){
                    this.addLevelsCustom(taxonomies[j].getKey(), exactDir, loginVO);
                    this.addLevelsToRepoEntriesCustom(taxonomies[j].getKey(), exactDir, loginVO);
                    break;
                }
               
            }
        }
    }


    /**
     * Removes and deletes all Levels from Taxonomies created by the Generator
     * 
     * @throws IOException
     */
    public void cleanTaxonomies(LoginVO loginVO) throws IOException{
        
        TaxonomyVO[] taxonomies = this.getTaxonomies(loginVO);
        ArrayList<Long> keyList = new ArrayList<Long>();

        for (int i=0; i<taxonomies.length; i++){
            keyList.add(taxonomies[i].getKey());
        }

        for (int i=0; i<keyList.size(); i++){
            String taxLevelsString = openOlatService.getTaxLevels(keyList.get(i), loginVO);
            TaxonomyLevelVO[] taxLevels = mapper.readValue(taxLevelsString, TaxonomyLevelVO[].class);
            System.out.println("THIS IS A TEST!!!!!!!!!!!!");
            for (int count=0; count<8; count++){
                for (int j=0; j<taxLevels.length; j++){
                    System.out.println(taxLevels[j].getDisplayName());
                    if (taxLevels[j].getExternalId() == null){
                        continue;
                    }
                    else if(taxLevels[j].getExternalId().equals("DataGeneratorCreated"+loginVO.getUsername())){
                        openOlatService.deleteTaxLevel(Long.valueOf(keyList.get(i)).intValue(), taxLevels[j].getKey(), loginVO);
                        System.out.println(taxLevels[j].getDisplayName()+" deleted!");
                    }
                }
                taxLevelsString = openOlatService.getTaxLevels(keyList.get(i), loginVO);
                taxLevels = mapper.readValue(taxLevelsString, TaxonomyLevelVO[].class);
            }
        }
    }
}
