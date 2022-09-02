package com.frentix.datagenerator.VOs;

public class CourseTemplateVO {
    private String name;
    private String coursename;
    private String coursetemplatename;
    private int owners;
    private int tutors;
    private boolean lectureblocks;

    public CourseTemplateVO(){

    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setCourseName(String coursename){
        this.coursename = coursename;
    }

    public String getCourseName(){
        return this.coursename;
    }

    public void setCourseTemplateName(String coursetemplatename){
        this.coursetemplatename = coursetemplatename;
    }

    public String getCourseTemplateName(){
        return this.coursetemplatename;
    }

    public void setOwners(int owners){
        this.owners = owners;
    }

    public int getOwners(){
        return this.owners;
    }

    public void setTutors(int tutors){
        this.tutors = tutors;
    }

    public int getTutors(){
        return this.tutors;
    }

    public void setLectureBlocks(boolean lectureblocks){
        this.lectureblocks = lectureblocks;
    }

    public boolean getLectureBlocks(){
        return this.lectureblocks;
    }

    
}
