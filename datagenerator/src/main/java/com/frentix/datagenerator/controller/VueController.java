package com.frentix.datagenerator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frentix.datagenerator.VOs.CourseVO;
import com.frentix.datagenerator.VOs.CurriculumVO;
import com.frentix.datagenerator.VOs.LoginVO;
import com.frentix.datagenerator.VOs.TaxonomyVO;
import com.frentix.datagenerator.service.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
public class VueController {

    private final EncryptionService encryptionService;
    private final FileService fileService;
    private final ObjectMapper mapper;
    private final OpenOlatService openOlatService;
    private final UserService userService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final TaxonomyService taxonomyService;
    private final CurriculumService curriculumService;

    public VueController() throws Exception{

        encryptionService = new EncryptionService();
        fileService = new FileService();
        mapper = new ObjectMapper();
        openOlatService = new OpenOlatService(mapper, fileService);
        userService = new UserService(mapper, fileService, openOlatService);
        courseService = new CourseService(mapper, fileService, openOlatService, userService);
        groupService = new GroupService(mapper, fileService, openOlatService, userService);
        taxonomyService = new TaxonomyService(mapper, fileService, openOlatService);
        curriculumService = new CurriculumService(mapper, fileService, openOlatService);
    }

    /**
     * 
     * @param number number of Users to be created on OpenOlat
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @PutMapping(path = "/users/{number}")
    public void putUser(@PathVariable("number") int number, @RequestBody LoginVO loginVO) throws IOException {

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        userService.fillOpenOlatWithUsers(number, loginVO); 
    }
       

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @return all Users in OpenOlat
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(path = "/users")
    public String getAllUsers(@RequestBody LoginVO loginVO) throws IOException{

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        String users = userService.getAllUsers(loginVO);

        return users;
    }
    

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @param number number of Courses to be created on OpenOlat
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @PutMapping(path = "/courses/{number}")
    public void putCourse(@PathVariable("number") int number, @RequestBody LoginVO loginVO) throws IOException {

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        courseService.sendToOpenOLAT(number, loginVO);
    }

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @return all Courses in OpenOlat
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(path = "/courses")
    public String getAllCourses(@RequestBody LoginVO loginVO) throws IOException{

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        String courses = courseService.getAllCourses(loginVO);

        return courses;
    }

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @param number number of Groups to be created on OpenOlat
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @PutMapping(path = "/groups/{number}")
    public void putGroup(@PathVariable("number") int number, @RequestBody LoginVO loginVO) throws IOException {

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        groupService.fillOpenOlatWithGroups(number, loginVO);
    }

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @return all Groups in OpenOlat
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(path = "/groups")
    public String getAllGroups(@RequestBody LoginVO loginVO) throws IOException{

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        String groups = groupService.getAllGroups(loginVO);

        return groups;
    }

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @param number amount of users to be given the roles
     * @param roles the roles to be given
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/roles/{number}/{roles}")
    public void postRole(@PathVariable("number") int number, @PathVariable("roles") String[] roles, @RequestBody LoginVO loginVO) throws IOException{

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        userService.giveRoles(number,roles, loginVO);
    }

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @param roles the roles to be inquired
     * @return Keys of Users with specified Roles
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(path = "/role/{roles}")
    public ArrayList<Long> getUsersWithRoles(@PathVariable("roles") String[] roles, @RequestBody LoginVO loginVO) throws IOException{

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        ArrayList<Long> usersWithRoles = userService.getUsersWithRoles(roles, loginVO);

        return usersWithRoles;
    }

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/users")
    public void delUsers(@RequestBody LoginVO loginVO) throws IOException {

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        userService.delUsers(loginVO);
    }

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/courses")
    public void delCourses(@RequestBody LoginVO loginVO) throws IOException {

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        courseService.delCourses(loginVO);
    }

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/groups")
    public void delGroups(@RequestBody LoginVO loginVO) throws IOException {

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        groupService.delGroups(loginVO);
    }

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/curricula")
    public void delCurricula(@RequestBody LoginVO loginVO) throws IOException {

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        curriculumService.delCurricula(loginVO);
    }

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/taxonomies")
    public void cleanTaxonomies(@RequestBody LoginVO loginVO) throws IOException {

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        taxonomyService.cleanTaxonomies(loginVO);
    }

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @PutMapping(path = "/groups/owners")
    public void addOwnersToGroups(@RequestBody LoginVO loginVO) throws IOException{

        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        groupService.addOwnersToGroups(loginVO);
    }

    /**
     * 
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @PutMapping(path = "/courses/owners")
    public void addOwnersToCourses(@RequestBody LoginVO loginVO) throws IOException{
        
        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        courseService.addOwnersToCourses(loginVO);    
    }

    /**
     * Gets all taxonomies on OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @return Taxonomies
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(path = "/taxonomy")
    public ArrayList<String> getTaxonomies(@RequestBody LoginVO loginVO) throws IOException{
        
        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        TaxonomyVO[] taxonomies = taxonomyService.getTaxonomies(loginVO);
        ArrayList<String> taxString = new ArrayList<String>();
        for ( int i=0; i<taxonomies.length; i++){
            taxString.add(""+taxonomies[i].getKey());
            taxString.add(taxonomies[i].getIdentifier());
            taxString.add(taxonomies[i].getDisplayName());
        }

        return taxString;
    }

    /**
     * Fills a Taxonomy with Types and Levels
     * 
     * @param loginVO credentials of the logged in user
     * @param key a taxonomy ID
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(path = "/taxonomy/{key}")
    public void fillTaxonomy(@PathVariable int key, @RequestBody LoginVO loginVO) throws IOException{
        
        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        taxonomyService.addLevelsRandom(key, loginVO);
    }

    /**
     * Gets all current Curricula on OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/curriculum")
    public ArrayList<String> getCurricula(@RequestBody LoginVO loginVO) throws IOException{
   
        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        CurriculumVO[] curricula = curriculumService.getAllCurricula(loginVO);
        ArrayList<String> currString = new ArrayList<String>();
        for ( int i=0; i<curricula.length; i++){
            currString.add(""+curricula[i].getKey());
            currString.add(curricula[i].getDisplayName());
        }

        return currString;
    }

    /**
     * Creates a new Curriculum on OpenOLAT
     * 
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @PutMapping(path = "/curriculum")
    public void makeCurriculum(@RequestBody LoginVO loginVO) throws IOException{
        
        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        curriculumService.createRandomCurriculumFromFiles(loginVO);        
    }

    /**
     * Adds a Lectureblock to all Courses in the system
     * 
     * @param loginVO credentials of the logged in user
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @PutMapping(path = "/lectures")
    public void addLectures(@RequestBody LoginVO loginVO) throws IOException{
        
        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        courseService.generateLectures(loginVO);
    }

    /**
     * Fetches all directory-names under custom
     * 
     * @return directory-names of custom templates
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(path = "/files")
    public ArrayList<String> getFileStructure() throws IOException{

        ArrayList<String> fileStructure = fileService.getCustomFileStructure();

        return fileStructure;
    }

    /**
     * Adds a new custom template to the filesystem
     * 
     * @param file a MultipartFile of a custom template
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(path = "/files")
    public void addFile(@RequestPart MultipartFile file) throws IOException{
        
        fileService.addNewCustomFiles(file);
    }

    /**
     * Sets up the conent of a custom template within openolat
     * 
     * @param loginVO credentials of the logged in user
     * @param dirName the name of the custom template directory
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @PutMapping(path = "/custom/{name}")
    public void setupTemplate(@PathVariable("name") String dirName, @RequestBody LoginVO loginVO) throws IOException{

        ArrayList<CourseVO> existentCourses = new ArrayList<CourseVO>();
        loginVO.setPassword(encryptionService.decrypt(loginVO.getPassword()));
        existentCourses = courseService.setUpCourseZips(dirName, loginVO);
        courseService.createCustomCourses(dirName, loginVO, existentCourses);
        groupService.createCustomGroups(dirName, loginVO);
        curriculumService.createCustomCurriculaFromFiles(dirName, loginVO);
        taxonomyService.fillCustomTaxonomiesFromFiles(dirName, loginVO);
        userService.createCustomUsers(dirName, loginVO);
    }
    
    /**
     * deletes a custom template directory
     * 
     * @param dirName the name of the custom template directory
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(path = "/files/{name}")
    public void deleteFiles(@PathVariable("name") String dirName) throws IOException{
        
        File targetFile = new File("src/main/resources/custom/"+dirName);
        fileService.deleteFiles(targetFile);
    }

    /**
     * Retrieves the specified template zip in byte form
     * 
     * @param dirName the name of the custom template directory
     * @return byteArray of the zip file
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(path = "/files/{name}")
    public byte[] getNewTemplate(@PathVariable("name") String dirName) throws IOException{
        
        byte[] bytes = fileService.getTemplate(dirName);
        fileService.getTemplate(dirName);
        
        return bytes;
    }

    /* 
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/files/courses")
    public void postNewTemplate(@RequestBody CourseTemplateVO courseTemplateVO) throws IOException{
        
        fileService.addNewCourse(courseTemplateVO);
        
    }
    */


    /**
     * Encrypts a password and send it bakc
     * 
     * @param password plain password
     * @return encrypted password
     * @throws IOException
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(path = "/login/{password}")
    public String login(@PathVariable("password") String password) throws IOException{
        String encPass = encryptionService.encrypt(password);

        return encPass;
    }
}