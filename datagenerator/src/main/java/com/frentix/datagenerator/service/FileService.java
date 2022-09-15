package com.frentix.datagenerator.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.frentix.datagenerator.VOs.LoginVO;

@Service
public class FileService {
    

    public FileService(){
    }

    /**
     * Gets a random entry from a .csv file
     * 
     * @param path the path of the CSV to be accessed
     * @return a random entry in the CSV
     * @throws FileNotFoundException
     */
    public String returnRandomCSVEntry(String path) throws FileNotFoundException{

        Scanner sc = new Scanner(new File(path));
        sc.useDelimiter(",");
        int count = 0;
        String entry = "";
    
         while (sc.hasNext()) {
            count++;
            sc.next();
        }
        sc.close();
        int searchFor = Integer.parseInt(this.genRandomNumberString(1, count));

        Scanner sc2 = new Scanner(new File(path));
        sc2.useDelimiter(",");
    
         while (sc2.hasNext() && searchFor>0) {
            entry = sc2.next();
            searchFor--;
        }
        sc2.close();

        return entry;
    }

    /**
     * Gets a random name and its gender from a .csv file
     * 
     * @param path the path of the CSV to be accessed
     * @return a random name and gender in the CSV
     * @throws FileNotFoundException
     */
    public String[] returnRandomCSVNameEntry(String path) throws FileNotFoundException{

        Scanner sc = new Scanner(new File(path));
        sc.useDelimiter(",");
        int count = 0;
        String entry = "";
        String gender = "";
    
         while (sc.hasNext()) {
            count++;
            sc.next();
        }
        sc.close();
        int searchFor = Integer.parseInt(this.genRandomNumberString(1, count));

        Scanner sc2 = new Scanner(new File(path));
        sc2.useDelimiter(",");
    
        while (sc2.hasNext() && searchFor>0) {
            entry = sc2.next();
            if (searchFor == 2 && !entry.equals("male") && !entry.equals("female")){
                searchFor--;
                gender=sc2.next();
            }
            else if (searchFor ==2){
                searchFor++;
            }
            searchFor--;
        }
        sc2.close();

        String[] nameGender = {entry,gender};
        return nameGender;
    }

     /**
     * get all entries of a CSV file
     * 
     * @param path the path of the CSV to be accessed
     * @return all entries of CSV file
     * @throws IOException
     */
    public ArrayList<ArrayList<String>> returnCompleteCSVEntry(String path) throws IOException{

        String delimiter = ",";
        String tempString;
        int counter = 0;
        ArrayList<ArrayList<String>> fileData = new ArrayList<>();
        
        //Reads the .csv line by line
        BufferedReader br2 = new BufferedReader(new FileReader(path));
        while((tempString = br2.readLine()) != null){
            String[] lineContent = tempString.split(delimiter,-1);
            fileData.add(new ArrayList<String>());
            for (int i=0; i<lineContent.length; i++){
                fileData.get(counter).add(lineContent[i]);
            } 
            counter++;
        }
        br2.close();
        fileData.remove(0);
        return fileData;
    }

    /**
     * checks the length of a CSV file
     * 
     * @param path the path of the CSV to be accessed
     * @return amount of entries in CSV
     * @throws FileNotFoundException
     */
    public int getCSVLength(String path) throws FileNotFoundException{

        Scanner sc = new Scanner(new File(path));
        sc.useDelimiter(",");
        int count = 0;

         while (sc.hasNext()) {
            count++;
            sc.next();
        }
        sc.close();
        return count;
    }

    /**
     * Generates a List out of a strings seperated by "/"
     * 
     * @param path String seperated by "/"
     * @return List of all Strings seperated by "/"
     */
    public ArrayList<String> getPath(String path){

        ArrayList<String> arrayList = new ArrayList<String>();
        String[] arrOfStr = path.split("/");
        for(String text:arrOfStr) {
            arrayList.add(text);
        }
        return arrayList;
    }

    /**
     * Generates the authorization header for REST calls to OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @return the authorization header for REST calls
     * @throws IOException
     */
    public String getAuth(LoginVO loginVO) throws IOException{

        //Retrieve Username and Password
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();

        //Creating the authentication
        String auth = username+":"+password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        return authHeaderValue;
    }

    /**
     * Picks a random picture out of the Females directory
     * 
     * @return path to a random picture in the Females directory
     */
    public String getFemalePicture(){
        File dir = new File("src/main/resources/pictures/Females");
        File[] files = dir.listFiles();
        int length = files.length;
        int rndm = genRandomNumber(0, length-1);
        String file = files[rndm].getAbsolutePath();
        return file;
    }

    /**
     * Picks a random picture out of the Males directory
     * 
     * @return path to a random picture in the Males directory
     */
    public String getMalePicture(){
        File dir = new File("src/main/resources/pictures/Males");
        File[] files = dir.listFiles();
        int length = files.length;
        int rndm = genRandomNumber(0, length-1);
        String file = files[rndm].getAbsolutePath();
        return file;
    }

    /**
     * Picks a random picture out of the specified course image folder
     * 
     * @param folder name of the course image folder
     * @return path to a random course image
     */
    public String getCourseImage(String folder){
        File dir = new File("src/main/resources/pictures/coursePictures/"+folder);
        File[] files = dir.listFiles();
        int length = files.length;
        int rndm = genRandomNumber(0, length-1);
        String file = files[rndm].getAbsolutePath();
        return file;
    }

    /**
     * Retrieves the file structure under /custom
     * 
     * @return List of names of the custom templates
     */
    public ArrayList<String> getCustomFileStructure(){

        ArrayList<String> fileStructure = new ArrayList<String>();
        File dir = new File("src/main/resources/custom");
        File[] files = dir.listFiles();

        for (int i=0; i<files.length; i++){
            fileStructure.add(files[i].getName());
        }
        return fileStructure;
    }

    /**
     * Adds a new custom template Directory
     * 
     * @param file a zipped directory of a custom template
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void addNewCustomFiles(MultipartFile file) throws FileNotFoundException, IOException {

        //Reading the .zip file and writing it to new.zip
        InputStream initialStream = file.getInputStream();
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);
        File targetFile = new File("src/main/resources/custom/new.zip");
        try (OutputStream outStream = new FileOutputStream(targetFile)) {
            outStream.write(buffer);
            outStream.close();
        }
        
        //Unzips the file and then deletes the new.zip
        String zipFilePath = "src/main/resources/custom/new.zip";
        String zipOutputPath = "src/main/resources/custom";
        unzip(zipFilePath, zipOutputPath);
        targetFile.delete();
    }

    /**
     * unzips a zip file at zipFilePath and adds it to outputDir
     * 
     * @param zipFilePath path to the zip file
     * @param outputDir path where directory should be put
     * @throws IOException
     */
    private void unzip(String zipFilePath, String outputDir) throws IOException {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = new File(outputDir + File.separator, zipEntry.getName());
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }
                FileOutputStream fos = new FileOutputStream(newFile);
                int len = 0;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    /**
     * Deletes a directory
     * 
     * @param targetFile the target directory
     */
    public void deleteFiles(File targetFile) {

        File[] allContents = targetFile.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFiles(file);
            }
        }
        targetFile.delete();
    }

    /* 
    public void addNewDirectory(String dirName) throws IOException {

        File newCustom = new File("src/main/resources/custom/"+dirName+"/courses/");
        newCustom.mkdirs();
        newCustom = new File("src/main/resources/custom/"+dirName+"/courses/courses.csv");
        newCustom.createNewFile();
        newCustom = new File("src/main/resources/custom/"+dirName+"/courseZips");
        newCustom.mkdirs();
        newCustom = new File("src/main/resources/custom/"+dirName+"/curricula/");
        newCustom.mkdirs();
        newCustom = new File("src/main/resources/custom/"+dirName+"/groups/");
        newCustom.mkdirs();
        newCustom = new File("src/main/resources/custom/"+dirName+"/groups/groups.csv");
        newCustom.createNewFile();
        newCustom = new File("src/main/resources/custom/"+dirName+"/taxonomies");
        newCustom.mkdirs();
        newCustom = new File("src/main/resources/custom/"+dirName+"/users/");
        newCustom.mkdirs();
        newCustom = new File("src/main/resources/custom/"+dirName+"/users/users.csv");
        newCustom.createNewFile();
    }
    */

    /* 
    public void addNewCourse(CourseTemplateVO courseTemplateVO){
        System.out.println(courseTemplateVO.getName());
        System.out.println(courseTemplateVO.getCourseName());
        System.out.println(courseTemplateVO.getCourseTemplateName());
        System.out.println(courseTemplateVO.getOwners());
        System.out.println(courseTemplateVO.getLectureBlocks());

    }
    */

    /**
     * Zips a Custom Template and converts it to a Byte Array
     * 
     * @param dirName name of the custom template
     * @return byteArray of the newly created zip file
     * @throws IOException
     */
    public byte[] getTemplate(String dirName) throws IOException{
        List<String> filesListInDir = new ArrayList<String>();
        File dir = new File("src/main/resources/custom/"+dirName);
        String zipDirName = "src/main/resources/custom/"+dirName+".zip";        
        zipDirectory(dir, zipDirName, filesListInDir);
        File file = new File(zipDirName);
        byte[] bytes = Files.readAllBytes(file.toPath());
        file.delete();
        return bytes;
    }

    /**
     * This method zips the directory
     * @param dir directory
     * @param zipDirName name and spot of new zipped file
     * @param filesListInDir utility list for saving files
     */
    private void zipDirectory(File dir, String zipDirName, List<String> filesListInDir) {
        
        try {
            populateFilesList(dir, filesListInDir);
            //now zip files one by one
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipDirName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(String filePath : filesListInDir){
                //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length()+1, filePath.length()));
                zos.putNextEntry(ze);
                //read the file and write to ZipOutputStream
                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method populates all the files in a directory to a List
     * @param dir
     * @param filesListInDir utility list for saving files
     * @throws IOException
     */
    private void populateFilesList(File dir, List<String> filesListInDir) throws IOException {
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isFile()) filesListInDir.add(file.getAbsolutePath());
            else populateFilesList(file, filesListInDir);
        }
    }

    /**
     * Generates a random stringified integer
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
     * @return the generated int
     */
    private int genRandomNumber(int min, int max){

        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        
        return randomNum;
    }
}
