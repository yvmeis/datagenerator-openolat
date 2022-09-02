package com.frentix.datagenerator.VOs;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 * Initial date: 8 juin 2017<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "lectureBlocksVO")
public class LectureBlockVO {
	
	private Long key;
	private String externalId;
	
	@Schema(required = true, description = "Action to be performed on managedFlagsString", allowableValues = { 
			"all",
		      "details(all) //details tab",
		        "title(details,all)",
		        "compulsory(details,all)",
		        "plannedLectures(details,all)",
		        "teachers(details,all)",
		        "groups(details,all)",
		        "description(details,all)",
		        "preparation(details,all)",
		        "location(details,all)",
		        "dates(details,all)",
		      "settings(all)",
		      "delete(all)"})
	private String managedFlagsString;
	
	private String title;
	private String description;
	private String preparation;
	private String location;
	private String comment;
	
	private Date startDate;
	private Date endDate;
	private Boolean compulsory;
	private int plannedLectures;
	
	private String status;
	private String rollCallStatus;

	private Long repoEntryKey;
	
	public LectureBlockVO() {
		//make jaxb happy
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public Long getRepoEntryKey() {
		return repoEntryKey;
	}

	public void setRepoEntryKey(Long repoEntryKey) {
		this.repoEntryKey = repoEntryKey;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getManagedFlagsString() {
		return managedFlagsString;
	}

	public void setManagedFlagsString(String managedFlagsString) {
		this.managedFlagsString = managedFlagsString;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPreparation() {
		return preparation;
	}

	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getCompulsory() {
		return compulsory;
	}

	public void setCompulsory(Boolean compulsory) {
		this.compulsory = compulsory;
	}

	public int getPlannedLectures() {
		return plannedLectures;
	}

	public void setPlannedLectures(int plannedLectures) {
		this.plannedLectures = plannedLectures;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRollCallStatus() {
		return rollCallStatus;
	}

	public void setRollCallStatus(String rollCallStatus) {
		this.rollCallStatus = rollCallStatus;
	}
}