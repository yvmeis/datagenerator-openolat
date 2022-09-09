package com.frentix.datagenerator.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frentix.datagenerator.VOs.CourseVO;
import com.frentix.datagenerator.VOs.LectureBlockVO;
import com.frentix.datagenerator.VOs.LoginVO;
import com.frentix.datagenerator.VOs.RepositoryEntryLectureConfigurationVO;
import com.frentix.datagenerator.VOs.RepositoryEntryMetadataVO;
import com.frentix.datagenerator.VOs.RolesVO;
import com.frentix.datagenerator.VOs.UserVO;

@Service
public class CourseService {

    private final FileService fileService;
    private final OpenOlatService openOlatService;
    private final ObjectMapper mapper;
    private final UserService userService;
    
    public CourseService(ObjectMapper mapper, FileService fileService, OpenOlatService openOlatService, UserService userService){

        this.mapper = mapper;
        this.fileService = fileService;
        this.openOlatService = openOlatService;
        this.userService = userService;
    }

    /**
     * Makes a call to this CourseService's OpenOlatService to retrieve the current Courses
     * in OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @return all Courses in OpenOlat
     * @throws IOException
     */
    public String getAllCourses(LoginVO loginVO) throws IOException{

        return openOlatService.getCourses(loginVO);
    }

    /**
     * Gives a name
     * 
     * @return name
     * @throws FileNotFoundException
     */
    private String giveDisplayName() throws FileNotFoundException{
        String path = "src/main/resources/random/coursenames.csv";
        String name = fileService.returnRandomCSVEntry(path);
        
        return name;
    }

    /**
     * Calls the objects OpenOlatService and tells it to add a Course to OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @param newCourses Courses to be put in OpenOLAT
     * @throws IOException
     */
    public void sendToOpenOLAT(int number, LoginVO loginVO) throws IOException{
        
        //Prepares metadata and Config
        RepositoryEntryMetadataVO metadata = new RepositoryEntryMetadataVO();
        RepositoryEntryLectureConfigurationVO courseConfig = new RepositoryEntryLectureConfigurationVO();
        courseConfig.setLectureEnabled(true);
        CourseVO createdCourse = new CourseVO();
        String  jsonStringMetadata = "";
        String jsonStringConfig = mapper.writeValueAsString(courseConfig);;

        //Repeats for every Course to be made
        for (int i=0; i<number; i++){

            //Adds Template to OpenOLAT
            String createdCourseString = openOlatService.postCourse("src/main/resources/random/courseZips/TemplateCourse.zip", loginVO);            
            createdCourse = mapper.readValue(createdCourseString, CourseVO.class);

            //Changes Coursedata
            metadata.setDisplayname(this.giveDisplayName());
            metadata.setDescription("rds4edf"+loginVO.getUsername());
            metadata.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
            jsonStringMetadata = mapper.writeValueAsString(metadata);
            openOlatService.setCourseMetadata(createdCourse.getKey(), jsonStringMetadata, loginVO);
            openOlatService.setCourseConfig(createdCourse.getKey(), jsonStringConfig, loginVO);

            //Sets Owner and Tutor
            Long ownerKey = userService.makeSingularUser(loginVO);
            openOlatService.addOwnerToCourse(ownerKey, createdCourse.getKey(), loginVO);
            Long tutorKey = userService.makeSingularUser(loginVO);
            openOlatService.addTutorToCourse(tutorKey, createdCourse.getKey(), loginVO);

            //Sets Course Image
            openOlatService.setImage(createdCourse.getKey(), fileService.getCourseImage("blue"), loginVO);
        }
    }

    /**
     * Deletes all Courses on OpenOLAT created by the logged-in user
     * 
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    public void delCourses(LoginVO loginVO) throws IOException{

        String currentCourses = openOlatService.getCourses(loginVO);
        CourseVO[] courses = mapper.readValue(currentCourses, CourseVO[].class);
        Long key = 1L;

        for (int i=0; i<courses.length; i++){
            key = courses[i].getKey();
            if ((!(courses[i].getDescription() == null) && courses[i].getDescription().equals("rds4edf"+loginVO.getUsername())) || (!(courses[i].getExternalId() == null) && courses[i].getExternalId().equals("DataGeneratorCreated"+loginVO.getUsername()))){
                openOlatService.delCourse(key, loginVO);
            }
           
        }
    }

    /**
     * Adds Owners and Tutors to all Courses on OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    public void addOwnersToCourses(LoginVO loginVO) throws IOException {

        String currentCoursesString = openOlatService.getCourses(loginVO);
        CourseVO[] currentCoursesArray = mapper.readValue(currentCoursesString, CourseVO[].class);

        for (int i=0; i<currentCoursesArray.length; i++){
            Long key = currentCoursesArray[i].getKey();
            this.putOwnersInCourse(key, loginVO);
            this.putTutorsInCOurse(key, loginVO);
        }
    }

    /**
     * Adds a random User as Owner of the Course
     * 
     * @param loginVO credentials of the logged in user
     * @param courseKey Key of a Course
     * @throws IOException
     */
    public void putOwnersInCourse(Long courseKey, LoginVO loginVO) throws IOException{

        //Retrieves a random User from OpenOLAT
        String output = openOlatService.getUsers(loginVO);
        UserVO[] users = mapper.readValue(output, UserVO[].class);
        int index = this.genRandomNumber(0, users.length-1);    
        Long userKey = users[index].getKey();

        //Makes Course Owner an Author
        String roleString = openOlatService.getRoles(userKey, loginVO);
        RolesVO roles = mapper.readValue(roleString, RolesVO.class);
        roles.setAuthor(true);
        String jsonString = mapper.writeValueAsString(roles);
        openOlatService.setRole(userKey, jsonString, loginVO);

        //Adds User to Course as Owner
        openOlatService.addOwnerToCourse(userKey, courseKey, loginVO);
    }

     /**
     * Adds a random User as Tutor of the Course
     * 
     * @param loginVO credentials of the logged in user
     * @param courseKey Key of a Course
     * @throws IOException
     */
    public void putTutorsInCOurse(Long courseKey, LoginVO loginVO) throws IOException{

        //Takes a random OpenOLAT User
        String output = openOlatService.getUsers(loginVO);
        UserVO[] users = mapper.readValue(output, UserVO[].class);
        int index = this.genRandomNumber(0, users.length-1);    
        Long userKey = users[index].getKey();

        //Adds User to Course as Tutor
        openOlatService.addTutorToCourse(userKey, courseKey, loginVO);
    }

    /**
     * Adds a Lectureblock to every Course
     * 
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    public void generateLectures(LoginVO loginVO) throws IOException{

        //Getting all Courses
        String coursesString = openOlatService.getCourses(loginVO);
        CourseVO[] courses = mapper.readValue(coursesString, CourseVO[].class);
        
        //Settng hours and Start/End Time
        int numberOfHours = 4;
        Date currentDate = new Date(System.currentTimeMillis());
        Date inTwoDays = new Date(System.currentTimeMillis()+(3600000*numberOfHours));

        //Preparing LectureBlockVO
        LectureBlockVO lectureBlock = new LectureBlockVO();
        lectureBlock.setTitle("First Lecture");
        lectureBlock.setStartDate(currentDate);
        lectureBlock.setEndDate(inTwoDays);
        lectureBlock.setPlannedLectures(numberOfHours);
        String jsonString = mapper.writeValueAsString(lectureBlock);

        //Iterates through all Courses
        for (int i=0; i<courses.length; i++){
            if (courses[i].getExternalId().equals("DataGeneratorCreated"+loginVO.getUsername())){

                //Gives Course a LecureBlock
                String lectureString = openOlatService.addLectureToCourse(courses[i].getRepoEntryKey(), jsonString, loginVO);
                LectureBlockVO lecture = mapper.readValue(lectureString, LectureBlockVO.class);
                //Gets Tutors from Course
                String tutorsString = openOlatService.getTutorsFromCourse(courses[i].getKey(), loginVO);
                UserVO[] tutors = mapper.readValue(tutorsString, UserVO[].class);

                //Adds Tutors and Partcipants
                openOlatService.addTutorToLecture(courses[i].getKey(), lecture.getKey(), tutors[0].getKey(), loginVO);
                openOlatService.addCourseParticipantsToLecture(courses[i].getKey(), lecture.getKey(), loginVO);
            }
        }
    }

    /**
     * Creates Courses in OpenOLAT according to the filesystem
     * 
     * @param existentCourses A list of all pre-created Courses
     * @param loginVO credentials of the logged in user
     * @param dirName name of the custom template directory
     * @throws IOException
     */
    public void createCustomCourses(String dirName, LoginVO loginVO, ArrayList<CourseVO> existentCourses) throws IOException {
        
        //Retrieves the data from te filesystem
        String dirPath = "src/main/resources/custom/"+dirName+"/courses/courses.csv";
        ArrayList<ArrayList<String>> content = fileService.returnCompleteCSVEntry(dirPath);

        //Prepares the Course, Metadata and Configurations
        CourseVO course = new CourseVO();
        CourseVO copiedCourse = new CourseVO();
        RepositoryEntryLectureConfigurationVO courseConfig = new RepositoryEntryLectureConfigurationVO();
        String jsonStringCourse = "";
        String jsonStringConfig = "";

        //Iterates through all Courses to be
        for (int i=0; i<content.size(); i++){
            String createdCourseString = "";

            //Case when no Template is chosen
            if (content.get(i).get(1)==""){

                //Course is added to OpenOLAT
                course.setTitle(content.get(i).get(0));
                course.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
                course.setDescription("rds4edf"+loginVO.getUsername());
                jsonStringCourse = mapper.writeValueAsString(course);
                createdCourseString = openOlatService.putCourse(jsonStringCourse, loginVO);
                CourseVO createdCourse = mapper.readValue(createdCourseString, CourseVO.class);

                //Creates the requested amount of Owners
                for (int j=0; j<Integer.valueOf(content.get(i).get(3)); j++){
                    Long ownerKey = userService.makeSingularUser(loginVO);
                    openOlatService.addOwnerToCourse(ownerKey, createdCourse.getKey(), loginVO);
                }

                //Creates the requested amount of Tutors
                for (int j=0; j<Integer.valueOf(content.get(i).get(4)); j++){
                    Long tutorKey = userService.makeSingularUser(loginVO);
                    openOlatService.addTutorToCourse(tutorKey, createdCourse.getKey(), loginVO);
                }

                //Enables LectureBlocks if wanted
                if (content.get(i).get(2).equals("true")){
                    courseConfig.setLectureEnabled(true);
                    jsonStringConfig = mapper.writeValueAsString(courseConfig);
                    openOlatService.setCourseConfig(createdCourse.getKey(), jsonStringConfig, loginVO);

                    //Settng hours and Start/End Time
                    int numberOfHours = 4;
                    Date currentDate = new Date(System.currentTimeMillis());
                    Date inTwoDays = new Date(System.currentTimeMillis()+(3600000*numberOfHours));

                    //Preparing LectureBlockVO
                    LectureBlockVO lectureBlock = new LectureBlockVO();
                    lectureBlock.setTitle("First Lecture");
                    lectureBlock.setStartDate(currentDate);
                    lectureBlock.setEndDate(inTwoDays);
                    lectureBlock.setPlannedLectures(numberOfHours);
                    String jsonString = mapper.writeValueAsString(lectureBlock);

                    //Gives Course a LecureBlock
                    String lectureString = openOlatService.addLectureToCourse(createdCourse.getRepoEntryKey(), jsonString, loginVO);
                    LectureBlockVO lecture = mapper.readValue(lectureString, LectureBlockVO.class);

                    //Gets Tutors from Course
                    String tutorsString = openOlatService.getTutorsFromCourse(createdCourse.getKey(), loginVO);
                    UserVO[] tutors = mapper.readValue(tutorsString, UserVO[].class);

                    openOlatService.addTutorToLecture(createdCourse.getKey(), lecture.getKey(), tutors[0].getKey(), loginVO);
                    openOlatService.addCourseParticipantsToLecture(createdCourse.getKey(), lecture.getKey(), loginVO);
                }
            }

            //Case when a Template is chosen
            else{
                //Finds the Template
                for (int temps=0; temps<existentCourses.size(); temps++){
                    if (existentCourses.get(temps).getDisplayName().equals(content.get(i).get(1))){

                        //Copies the Template on OpenOLAT
                        
                        copiedCourse.setDescription("rds4edf"+loginVO.getUsername());
                        copiedCourse.setTitle(content.get(i).get(0));
                        copiedCourse.setExternalId("DataGeneratorCreated"+loginVO.getUsername());
                        jsonStringCourse = mapper.writeValueAsString(copiedCourse);
                        createdCourseString = openOlatService.copyCourse(jsonStringCourse, loginVO, existentCourses.get(temps).getRepoEntryKey());
                        CourseVO createdCourse = mapper.readValue(createdCourseString, CourseVO.class);

                        //Enables LectureBlocks if wanted
                        if (content.get(i).get(2).equals("true")){
                            courseConfig.setLectureEnabled(true);
                            jsonStringConfig = mapper.writeValueAsString(courseConfig);
                            openOlatService.setCourseConfig(createdCourse.getKey(), jsonStringConfig, loginVO);
                        }

                        //Creates the requested amount of Owners
                        for (int j=0; j<Integer.valueOf(content.get(i).get(3)); j++){
                            Long ownerKey = userService.makeSingularUser(loginVO);
                            openOlatService.addOwnerToCourse(ownerKey, createdCourse.getKey(), loginVO);
                        }

                        //Creates the requested amount of Tutors
                        for (int j=0; j<Integer.valueOf(content.get(i).get(4)); j++){
                            Long tutorKey = userService.makeSingularUser(loginVO);
                            openOlatService.addTutorToCourse(tutorKey, createdCourse.getKey(), loginVO);
                        }
                    }
                }
            }
        }
        for (int temps=0; temps<existentCourses.size(); temps++){
            openOlatService.delCourse(existentCourses.get(temps).getKey(), loginVO);
        }
    }

    /**
     * Uploads all CourseZips, so that they may be copied on OO for future use
     * 
     * @param dirName Name of Custom Template
     * @param loginVO credentials of the logged in user
     * @return List of all newly created Courses
     * @throws IOException
     */
    public ArrayList<CourseVO> setUpCourseZips(String dirName, LoginVO loginVO) throws IOException{
        
        String courseString;
        CourseVO createdCourse;
        ArrayList<CourseVO> courses = new ArrayList<CourseVO>();
        File dir = new File("src/main/resources/custom/"+dirName+"/courseZips");
        File[] files = dir.listFiles();

        for (int i=0; i<files.length; i++){
            courseString = openOlatService.postCourse("src/main/resources/custom/"+dirName+"/courseZips/"+files[i].getName(), loginVO);
            createdCourse = mapper.readValue(courseString, CourseVO.class);
            createdCourse.setDisplayName(files[i].getName().substring(0, files[i].getName().length() - 4)); 
            courses.add(createdCourse);
        }
        return courses;
    }

    /**
     * Generates a random Integer between min and max
     * 
     * @param min minimum int to be generated
     * @param max maximum int to be generated
     * @return a random number
     */
    private int genRandomNumber(int min, int max){

        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;
    }
}
