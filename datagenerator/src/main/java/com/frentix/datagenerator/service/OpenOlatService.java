package com.frentix.datagenerator.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frentix.datagenerator.VOs.CourseVO;
import com.frentix.datagenerator.VOs.CurriculumElementVO;
import com.frentix.datagenerator.VOs.CurriculumVO;
import com.frentix.datagenerator.VOs.GroupVO;
import com.frentix.datagenerator.VOs.LoginVO;
import com.frentix.datagenerator.VOs.UserVO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class OpenOlatService {

    private final FileService fileService;
    private final ObjectMapper mapper;

    public OpenOlatService(ObjectMapper mapper, FileService fileService){
        this.mapper = mapper;
        this.fileService = fileService;
    }

    /**
     * Retrieves all Users on OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @return all Users on OpenOlat
     * @throws IOException
     */
    public String getUsers(LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/users");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
            sb.append(output);
		}
        output = sb.toString();

		conn.disconnect();
        //UserVO[] users = mapper.readValue(output, UserVO[].class);
        return output;
    }

    /**
     * Gives a User a Portrait
     * 
     * @param loginVO credentials of the logged in user
     * @param key a User Key
     * @param imgPath the path to an image
     * @throws IOException
     */
    public void setPortrait(Long key, String imgPath, LoginVO loginVO) throws IOException{

        try {
            Unirest.setTimeouts(0, 0);
            Unirest.post(loginVO.getBaseURL()+"/users/"+key+"/portrait")
                .header("Authorization", fileService.getAuth(loginVO))
                .field("file", new File(imgPath))
                .asString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gives a Course an Image
     * 
     * @param loginVO credentials of the logged in user
     * @param key a Course Key
     * @param imgPath the path to an image
     * @throws IOException
     */
    public void setImage(Long key, String imgPath, LoginVO loginVO) throws IOException{

        try {
            Unirest.setTimeouts(0, 0);
            Unirest.post(loginVO.getBaseURL()+"/repo/courses/"+key+"/image")
                .header("Authorization", fileService.getAuth(loginVO))
                .field("file", new File(imgPath))
                .asString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new User on OpenOLAT and adds them to a Group
     * 
     * @param loginVO credentials of the logged in user
     * @param input User to be added
     * @return Created Users Key
     * @throws IOException
     */
    public Long putUserinRandomGroup(String input, LoginVO loginVO) throws IOException{

        String output = this.putUserHelper(input, loginVO);
        
        UserVO user = mapper.readValue(output, UserVO.class);        
        String groupsString = this.getGroups(loginVO);
        GroupVO[] groups = mapper.readValue(groupsString, GroupVO[].class);
        List<GroupVO> groupsList = new ArrayList<GroupVO>();
        groupsList = Arrays.asList(groups);
        Collections.shuffle(groupsList);
        if (!(groupsList.size() == 0)){
            this.addUserToGroup(user.getKey(), groupsList.get(0).getKey(), loginVO);
        }
        return user.getKey();
    }

    /**
     * Adds a User to a Group
     * 
     * @param loginVO credentials of the logged in user
     * @param userKey key of a User
     * @param groupKey key of a Group
     * @throws IOException
     */
    public void addUserToGroup(Long userKey, Long groupKey, LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/groups/"+groupKey+"/participants/"+userKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Adds a User to a Group as Owner
     * 
     * @param loginVO credentials of the logged in user
     * @param userKey key of a User
     * @param groupKey key of a Group
     * @throws IOException
     */
    public void addOwnerToGroup(Long userKey, Long groupKey, LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/groups/"+groupKey+"/owners/"+userKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Adds a User to a Course as Tutor
     * 
     * @param loginVO credentials of the logged in user
     * @param userKey key of a User
     * @param courseKey key of a Course
     * @throws IOException
     */
    public void addTutorToCourse(Long userKey, Long courseKey, LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/repo/courses/"+courseKey+"/tutors/"+userKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Adds a User to a Course as Owner
     * 
     * @param loginVO credentials of the logged in user
     * @param userKey key of a User
     * @param courseKey key of a Course
     * @throws IOException
     */
    public void addOwnerToCourse(Long userKey, Long courseKey, LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/repo/courses/"+courseKey+"/authors/"+userKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Creates a new User on OpenOLAT and adds them to a Course
     * 
     * @param loginVO credentials of the logged in user
     * @param input User to be added
     * @return Created Users Key
     * @throws IOException
     */
    public Long putUserinCourse(String input, LoginVO loginVO) throws IOException{

        String output = this.putUserHelper(input, loginVO);
        
        UserVO user = mapper.readValue(output, UserVO.class);
        String coursesString = this.getCourses(loginVO);
        CourseVO[] courses = mapper.readValue(coursesString, CourseVO[].class);
        List<CourseVO> coursesList = new ArrayList<CourseVO>();
        coursesList = Arrays.asList(courses);
        Collections.shuffle(coursesList);
        if (!(coursesList.size() == 0)){

            int amount = this.genRandomNumber(1, coursesList.size());
            for (int i=0;i<amount;i++){

                this.addUserToCourse(user.getKey(), coursesList.get(i).getKey(), loginVO);
            }
        }
        return user.getKey();
    }

    /**
     * Adds a User to a Course
     * 
     * @param loginVO credentials of the logged in user
     * @param userKey key of a User
     * @param courseKey key of a Course
     * @throws IOException
     */
    public void addUserToCourse(Long userKey, Long courseKey, LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/repo/courses/"+courseKey+"/participants/"+userKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Creates a new User on OpenOLAT and adds them to a Curriculum
     * 
     * @param loginVO credentials of the logged in user
     * @param input User to be added
     * @return Created Users Key
     * @throws IOException
     */
    public Long putUserinCurriculum(String input, LoginVO loginVO) throws IOException{

        String output = this.putUserHelper(input, loginVO);
        
        UserVO user = mapper.readValue(output, UserVO.class);
        String curriculaString = this.getCurricula(loginVO);
        CurriculumVO[] curricula = mapper.readValue(curriculaString, CurriculumVO[].class);
        List<CurriculumVO> curriculumList = new ArrayList<CurriculumVO>();
        curriculumList = Arrays.asList(curricula);
        Collections.shuffle(curriculumList);
        if (!(curriculumList.size() == 0)){

            String curriculumElementString = this.getElements(curriculumList.get(0).getKey(), loginVO);
            CurriculumElementVO[] curriculumElements = mapper.readValue(curriculumElementString, CurriculumElementVO[].class);
            ArrayList<CurriculumElementVO> curriculumElementList = new ArrayList<CurriculumElementVO>();
            for (int i=0; i<curriculumElements.length; i++){
                if (!(curriculumElements[i].getParentElementKey() == null)){
                    curriculumElementList.add(curriculumElements[i]);
                }
            }
            Collections.shuffle(curriculumElementList);
            CurriculumElementVO currentCurriculumElement = curriculumElementList.get(0);
            
            this.addUserToCurriculum(user.getKey(), curriculumElementList.get(0).getKey(), curriculumList.get(0).getKey(), loginVO);
            for (int i=1; i<curriculumElementList.size(); i++){
                if (curriculumElementList.get(i).getParentElementKey().equals(currentCurriculumElement.getParentElementKey())){
                    this.addUserToCurriculum(user.getKey(), curriculumElementList.get(i).getKey(), curriculumList.get(0).getKey(), loginVO);
                }
                else if (curriculumElementList.get(i).getKey().equals(currentCurriculumElement.getParentElementKey())){
                    this.addUserToCurriculum(user.getKey(), curriculumElementList.get(i).getKey(), curriculumList.get(0).getKey(), loginVO);
                }
            }
            
            


            }
        return user.getKey();
    }

    /**
     * Adds a User to a Curriculum
     * 
     * @param loginVO credentials of the logged in user
     * @param userKey key of a User
     * @param elementKey key of a CurriculumElement
     * @param curriculumKey key of a Curriculum
     * @throws IOException
     */
    public void addUserToCurriculum(Long userKey, Long elementKey, Long curriculumKey, LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/curriculum/"+curriculumKey+"/elements/"+elementKey+"/participants/"+userKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * A helper method to reduce Code duplication
     * 
     * @param loginVO credentials of the logged in user
     * @param input User to be added
     * @return added User
     * @throws IOException
     */
    private String putUserHelper(String input, LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/users");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {

            sb.append(output);
		}
        output = sb.toString();
        conn.disconnect();

        return output;
    }

    /**
     * Creates a new User on OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @param input User to be added
     * @return created Users Key
     * @throws IOException
     */
    public Long putUser(String input, LoginVO loginVO) throws IOException{

        String output = this.putUserHelper(input, loginVO);
        UserVO user = mapper.readValue(output, UserVO.class);

        return user.getKey();
    }

    /**
     * Creates a new Course on OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @param input Course to be added
     * @throws IOException
     */
    public String putCourse(String input, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/repo/courses");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {

            sb.append(output);
		}
        output = sb.toString();
        conn.disconnect();

        return output;

    }

    /**
     * Copies a Course
     * 
     * @param courseName the title of the new Course
     * @param externalId the externalId of the new Course
     * @param loginVO credentials of the logged in user
     * @param parentKey Key of parent Course
     * @return the new Course
     * @throws IOException
     */
    public String copyCourse(String courseName, String externalId, LoginVO loginVO, Long parentKey) throws IOException{

        //Editing the String to make it URL compatible
        courseName = courseName.replace(" ", "%20");
        courseName = courseName.replace("ä", "%C3%A4");
        courseName = courseName.replace("ö", "%C3%B6");
        courseName = courseName.replace("ü", "%C3%BC");
        courseName = courseName.replace("Ä", "%C3%84");
        courseName = courseName.replace("Ö", "%C3%96");
        courseName = courseName.replace("Ü", "%C3%9C");

        URL url = new URL(loginVO.getBaseURL()+"/repo/courses?copyFrom="+parentKey+"&title="+courseName+"&externalId="+externalId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
            sb.append(output);
		}
        output = sb.toString();

		conn.disconnect();

        return output;

    }

    /**
     * Adds a Course according to a zip file
     * 
     * @param loginVO credentials of the logged in user
     * @param filePath path to the zip fle
     * @return jsonString of the created Course
     * @throws IOException
     */
    public String postCourse(String filePath, LoginVO loginVO) throws IOException{

        try {

            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post(loginVO.getBaseURL()+"/repo/courses")
            .header("Authorization", fileService.getAuth(loginVO))
            .header("Accept", "application/json")
            .field("file", new File(filePath))
            .asString();
            
            return response.getBody();

        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;    
    }


   /**
    * Changes a Courses Metadata 
    *
    @param loginVO credentials of the logged in user
    * @param key Course Key
    * @param input String of RepositoryEntryMetadataVO
    * @throws IOException
    */
    public void setCourseMetadata(Long key, String input, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/repo/courses/"+key+"/metadata");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Changes a Courses Configuration
     * 
     * @param loginVO credentials of the logged in user
     * @param key Course Key
     * @param input String of RepositoryEntryConfigurationVO
     * @throws IOException
     */
    public void setCourseConfig(Long key, String input, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/repo/courses/"+key+"/lectureblocks/configuration");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Retrieves all Courses from OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @return all the Courses on OpenOLAT
     * @throws IOException
     */
    public String getCourses(LoginVO loginVO) throws IOException {
        URL url = new URL(loginVO.getBaseURL()+"/repo/courses");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
            sb.append(output);
		}
        output = sb.toString();

		conn.disconnect();

        return output;
    }

    /**
     * Creates a new Group on OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @param input Group to be added
     * @throws IOException
     */
    public GroupVO putGroup(String input, LoginVO loginVO) throws IOException{

        String output = this.putGroupHelper(input, loginVO);
        GroupVO group = mapper.readValue(output, GroupVO.class);
        return group;
    }

    /**
     * Creates a new Group and adds it to a Course
     * 
     * @param loginVO credentials of the logged in user
     * @param input Group to be created
     * @throws IOException
     */
    public GroupVO putGroupinCourse(String input, LoginVO loginVO) throws IOException{

        String output = this.putGroupHelper(input, loginVO);
        
        GroupVO group = mapper.readValue(output, GroupVO.class);
        String coursesString = this.getCourses(loginVO);
        CourseVO[] courses = mapper.readValue(coursesString, CourseVO[].class);
        List<CourseVO> coursesList = new ArrayList<CourseVO>();
        coursesList = Arrays.asList(courses);
        Collections.shuffle(coursesList);
        if (!(coursesList.size() == 0)){

            int maxGroups = Math.min(coursesList.size(), 5);
            int amount = this.genRandomNumber(1, maxGroups);
            for (int i=0;i<amount;i++){

                this.addGroupToCourse(group.getKey(), coursesList.get(i).getKey(), loginVO);
            }
        }
        return group;
    }

    /**
     *  A Helper method to reduce code duplication
     * 
     * @param loginVO credentials of the logged in user
     * @param input Group to be created
     * @return created Group
     * @throws IOException
     */
    private String putGroupHelper(String input, LoginVO loginVO) throws IOException{
        
        URL url = new URL(loginVO.getBaseURL()+"/groups");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {

            sb.append(output);
		}
        output = sb.toString();
        conn.disconnect();

        return output;
    }

    /**
     * Adds a Group to a Course
     * 
     * @param loginVO credentials of the logged in user
     * @param groupKey a Group Key
     * @param courseKey a Course Key
     * @throws IOException
     */
    public void addGroupToCourse(Long groupKey, Long courseKey, LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/repo/courses/"+courseKey+"/groups/"+groupKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Retrieves all Groups from OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @return all the Groups on OpenOLAT
     * @throws IOException
     */
    public String getGroups(LoginVO loginVO) throws IOException {
        URL url = new URL(loginVO.getBaseURL()+"/groups");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
            sb.append(output);
		}
        output = sb.toString();

		conn.disconnect();

        return output;
    }

    /**
     * Sets the given Roles to the User with the given Key
     * 
     * @param loginVO credentials of the logged in user
     * @param key a Key belonging to a User
     * @param input jsonString of a RolesVO
     * @throws IOException
     */
    public void setRole(Long key, String input, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/users/"+key+"/roles");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Retrieves the Roles of a User
     * 
     * @param loginVO credentials of the logged in user
     * @param key the key of a User
     * @return the Roles
     * @throws IOException
     */
    public String getRoles(Long key, LoginVO loginVO) throws IOException {

        URL url = new URL(loginVO.getBaseURL()+"/users/"+key+"/roles");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
            sb.append(output);
		}
        output = sb.toString();

		conn.disconnect();

        return output;
    }

    /**
     * Deletes a Course on OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @param key the key of the Course
     * @throws IOException
     */
    public void delCourse(Long key, LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/repo/courses/"+key);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

        conn.getInputStream();
        conn.disconnect();

    }

    /**
     * Deletes a Group on OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @param key the key of the Group
     * @throws IOException
     */
    public void delGroup(Long key, LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/groups/"+key);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

        conn.getInputStream();
        conn.disconnect();

    }

    /**
     * Deletes a User on OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @param key the key of the User
     * @throws IOException
     */
    public void delUser(Long key, LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/users/"+key);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

        conn.getInputStream();
        conn.disconnect();

    }

     /**
     * Deletes a Curriculum on OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @param key the key of the Curriculum
     * @throws IOException
     */
    public void delCurriculum(Long key, LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/curriculum/"+key);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

        conn.getInputStream();
        conn.disconnect();

    }


    /**
     * Gets all Taxonomies on OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @return Taxonomies
     * @throws IOException
     */
    public String getTaxonomies(LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/taxonomy");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
            sb.append(output);
		}
        output = sb.toString();

		conn.disconnect();

        return output;
    }

    /**
     * Adds a Level to a Taxonomy
     * 
     * @param loginVO credentials of the logged in user
     * @param key the Taxonomy Key
     * @param input the json String of a TaxonomyLevelVO
     * @throws IOException
     */
    public void addLevelsToTaxonomy(int key, String input, LoginVO loginVO) throws IOException {

        URL url = new URL(loginVO.getBaseURL()+"/taxonomy/"+key+"/levels");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Adds a Base Level to a Taxonomy. This method is to be uses as a Helper for other methods.
     * 
     * @param loginVO credentials of the logged in user
     * @param key the Taxonomy Key
     * @param input json String of a TaxonomyLevelVO
     * @return json String of the BaseLevel
     * @throws IOException
     */
    public String addBaseLevelToTaxonomy(int key, String input, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/taxonomy/"+key+"/levels");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {

            sb.append(output);
		}
        output = sb.toString();
        conn.disconnect();
        return output;

    }

    /**
     * Deletes a Level from the Taxonomy
     * 
     * @param loginVO credentials of the logged in user
     * @param key the Taxonomy Key
     * @param levelKey the Level's Key
     * @throws IOException
     */
    public void deleteTaxLevel(int key ,Long levelKey, LoginVO loginVO) throws IOException {

        URL url = new URL(loginVO.getBaseURL()+"/taxonomy/"+key+"/levels/"+levelKey);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Adds a Type to a Taxonomy
     * 
     * @param loginVO credentials of the logged in user
     * @param key the Taxonomy Key
     * @param input jsonString of a TaxonomyLevelTypeVO
     * @return jsonString of the added Type
     * @throws IOException
     */
    public String addTypeToTaxonomy(int key, String input, LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/taxonomy/"+key+"/types");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {

            sb.append(output);
		}
        output = sb.toString();
        conn.disconnect();
        return output;
    }

    /**
     * Makes one Type the SubType of another Type
     * 
     * @param loginVO credentials of the logged in user
     * @param key the Taxonomy Key
     * @param parentKey the parent Types Key
     * @param subKey the new SubTypes Key
     * @throws IOException
     */
    public void taxonomyAddSubTypeToType(int key, Long parentKey, Long subKey, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/taxonomy/"+key+"/types/"+parentKey+"/allowedSubTypes/"+subKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Gets all TaxonomyLevels of a Taxonomy
     * 
     * @param loginVO credentials of the logged in user
     * @param taxKey a Taxonomy Key
     * @return jsonString of all Taxonomy Levels
     * @throws IOException
     */
    public String getTaxLevels(Long taxKey, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/taxonomy/"+taxKey+"/levels");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
            sb.append(output);
		}
        output = sb.toString();

		conn.disconnect();

        return output;
    }

    /**
     * Adds a TaxonomyLevel to a Course as a tag
     * 
     * @param loginVO credentials of the logged in user
     * @param entryKey Course's Repo Entry Key
     * @param taxLevelKey TaxonomyLevel Key
     * @throws IOException
     */
    public void addTaxLevelToCourse(Long entryKey, Long taxLevelKey, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/repo/entries/"+entryKey+"/taxonomy/levels/"+taxLevelKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        conn.getInputStream();
        conn.disconnect();
    }


    /**
     * Gets all Curricula from OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @return the Curricula from OpenOLAT
     * @throws IOException
     */
    public String getCurricula(LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/curriculum");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
            sb.append(output);
		}
        output = sb.toString();

		conn.disconnect();

        return output;
    }

    /**
     * Creates a Curriculum
     * 
     * @param loginVO credentials of the logged in user
     * @param input jsonString of a CurriculumVO
     * @return jsonString of the created Curriculum
     * @throws IOException
     */
    public String makeCurriculum(String input, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/curriculum");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {

            sb.append(output);
		}
        output = sb.toString();
        conn.disconnect();
        return output;
    }

    /**
     * Adds a Type to a Curriculum
     * 
     * @param loginVO credentials of the logged in user
     * @param input jsnString of a CurriculumElementTypeVO
     * @return jsonString of the created Type
     * @throws IOException
     */
    public String addTypeToCurriculum(String input, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/curriculum/types");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {

            sb.append(output);
		}
        output = sb.toString();
        conn.disconnect();
        return output;
    }

    /**
     * Adds a SubType Type Relation
     * 
     * @param loginVO credentials of the logged in user
     * @param parentKey Key of the parentType
     * @param subKey Key of the subType
     * @throws IOException
     */
    public void curriculumAddSubTypeToType(Long parentKey, Long subKey, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/curriculum/types/"+parentKey+"/allowedSubTypes/"+subKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Adds a Base Element to a Curriculum for simplicity
     * 
     * @param loginVO credentials of the logged in user
     * @param key CurriculumKey
     * @param input jsonString of an Element
     * @return jsonString of the Element
     * @throws IOException
     */
    public String addBaseElementToCurriculum(Long key, String input, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/curriculum/"+key+"/elements");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {

            sb.append(output);
		}
        output = sb.toString();
        conn.disconnect();
        return output;

    }

    /**
     * Adds an Element to a Curriculm
     * 
     * @param loginVO credentials of the logged in user
     * @param key Curriculum Key
     * @param input jsonString of an Element
     * @throws IOException
     */
    public void addElementsToCurriculum(Long key, String input, LoginVO loginVO) throws IOException {

        URL url = new URL(loginVO.getBaseURL()+"/curriculum/"+key+"/elements");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * retrieves all Elements of a Curriculum
     * 
     * @param loginVO credentials of the logged in user
     * @param key CurriculumKey
     * @return jsonString of all Elements
     * @throws IOException
     */
    public String getElements(Long key, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/curriculum/"+key+"/elements");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
            sb.append(output);
		}
        output = sb.toString();

		conn.disconnect();

        return output;
    }

    /**
     * Adds a Course to a Curricuum Element
     * 
     * @param loginVO credentials of the logged in user
     * @param curriculumKey Curriculum Key
     * @param entryKey Course's Repo Entry Key
     * @param curriculumElementKey CurriculumElement Key
     * @throws IOException
     */
    public void addCourseToCurriculum(Long curriculumKey, Long entryKey, Long curriculumElementKey, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/curriculum/"+curriculumKey+"/elements/"+curriculumElementKey+"/entries/"+entryKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Retrieves all repo entries
     * 
     * @param loginVO credentials of the logged in user
     * @return jsonString of all repo entries
     * @throws IOException
     */
    public String getRepoEntries(LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/repo/entries");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
            sb.append(output);
		}
        output = sb.toString();

		conn.disconnect();

        return output;
    }

    /**
     * Adds a Lectureblock to a Course
     * 
     * @param loginVO credentials of the logged in user
     * @param repoEntryKey Course's Repo Entry Key
     * @param input String of LectureBlockVO
     * @return the created LectureBlock
     * @throws IOException
     */
    public String addLectureToCourse(Long repoEntryKey, String input, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/repo/entries/"+repoEntryKey+"/lectureblocks");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {

            sb.append(output);
		}
        output = sb.toString();
        conn.disconnect();
        return output;
    }

    /**
     * Retrieves the Tutors from a Course
     * 
     * @param loginVO credentials of the logged in user
     * @param key Course Key
     * @return Tutors of the Course
     * @throws IOException
     */
    public String getTutorsFromCourse(Long key, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/repo/courses/"+key+"/tutors");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
            sb.append(output);
		}
        output = sb.toString();

		conn.disconnect();
        return output;
    }

    /**
     * Adds a Tutor to a LectureBlock
     * 
     * @param loginVO credentials of the logged in user
     * @param courseKey Course Key
     * @param lectureKey LectureBlock Key
     * @param tutorKey Tutor's User Key
     * @throws IOException
     */
    public void addTutorToLecture(Long courseKey, Long lectureKey, Long tutorKey, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/repo/courses/"+courseKey+"/lectureblocks/"+lectureKey+"/teachers/"+tutorKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Adds all participants of a Course to a LectureBlock as Participants
     * 
     * @param loginVO credentials of the logged in user
     * @param courseKey Course Key
     * @param lectureKey LectureBlock Key
     * @throws IOException
     */
    public void addCourseParticipantsToLecture(Long courseKey, Long lectureKey, LoginVO loginVO) throws IOException{

        URL url = new URL(loginVO.getBaseURL()+"/repo/courses/"+courseKey+"/lectureblocks/"+lectureKey+"/participants/repositoryentry");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));


        conn.getInputStream();
        conn.disconnect();
    }

    /**
     * Retrieves the logged in User
     * 
     * @param loginVO credentials of the logged in user
     * @return String of logged in User
     * @throws IOException
     */
    public String getMe(LoginVO loginVO) throws IOException{
        URL url = new URL(loginVO.getBaseURL()+"/users/me");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", fileService.getAuth(loginVO));

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
        StringBuilder sb = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
            sb.append(output);
		}
        output = sb.toString();

		conn.disconnect();

        return output;
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
