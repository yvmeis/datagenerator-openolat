package com.frentix.datagenerator.VOs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * Initial date: 8 juin 2017<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "repositoryEntryLectureConfigurationVO")
public class RepositoryEntryLectureConfigurationVO {
	
	private Boolean lectureEnabled;
	private Boolean overrideModuleDefault;
	private Boolean rollCallEnabled;
	private Boolean calculateAttendanceRate;
	private Double requiredAttendanceRate;
	private Boolean teacherCalendarSyncEnabled;
	private Boolean courseCalendarSyncEnabled;
	
	
	public RepositoryEntryLectureConfigurationVO() {
		// make JAXB happy
	}
	
	public Boolean getLectureEnabled() {
		return lectureEnabled;
	}
	
	public void setLectureEnabled(Boolean lectureEnabled) {
		this.lectureEnabled = lectureEnabled;
	}
	
	public Boolean getOverrideModuleDefault() {
		return overrideModuleDefault;
	}
	
	public void setOverrideModuleDefault(Boolean overrideModuleDefault) {
		this.overrideModuleDefault = overrideModuleDefault;
	}
	
	public Boolean getRollCallEnabled() {
		return rollCallEnabled;
	}
	
	public void setRollCallEnabled(Boolean rollCallEnabled) {
		this.rollCallEnabled = rollCallEnabled;
	}
	
	public Boolean getCalculateAttendanceRate() {
		return calculateAttendanceRate;
	}
	
	public void setCalculateAttendanceRate(Boolean calculateAttendanceRate) {
		this.calculateAttendanceRate = calculateAttendanceRate;
	}
	
	public Double getRequiredAttendanceRate() {
		return requiredAttendanceRate;
	}
	
	public void setRequiredAttendanceRate(Double requiredAttendanceRate) {
		this.requiredAttendanceRate = requiredAttendanceRate;
	}
	
	public Boolean getTeacherCalendarSyncEnabled() {
		return teacherCalendarSyncEnabled;
	}
	
	public void setTeacherCalendarSyncEnabled(Boolean teacherCalendarSyncEnabled) {
		this.teacherCalendarSyncEnabled = teacherCalendarSyncEnabled;
	}

	public Boolean getCourseCalendarSyncEnabled() {
		return courseCalendarSyncEnabled;
	}

	public void setCourseCalendarSyncEnabled(Boolean courseCalendarSyncEnabled) {
		this.courseCalendarSyncEnabled = courseCalendarSyncEnabled;
	}
}

