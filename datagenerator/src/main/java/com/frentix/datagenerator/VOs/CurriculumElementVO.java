package com.frentix.datagenerator.VOs;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 * Initial date: 15 mai 2018<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "curriculumElementVO")
public class CurriculumElementVO {
	
	private Long key;
	private String identifier;
	private String displayName;
	private String description;
	
	private String status;
	private Date beginDate;
	private Date endDate;
	
	private String externalId;
	@Schema(required = true, description = "Action to be performed on managedFlagsString", allowableValues = { 
			"all",
			 "identifier(all)",
			 "displayName(all)",
			 "description(all)",
			 "externalId(all)",
			 "status(all)",
			 "dates(all)",
			 "type(all)",
			 "calendars(all)",
			 "lectures(all)",
			 "members(all)",
			 "resources(all)",
			 "move(all)",
			 "addChildren(all)",
			 "delete(all)"})
	private String managedFlagsString;
	private String calendars;

	private Long parentElementKey;
	private Long curriculumKey;
	private Long curriculumElementTypeKey;
	
	
	public CurriculumElementVO() {
		//
	}
	

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getCalendars() {
		return calendars;
	}

	public void setCalendars(String calendars) {
		this.calendars = calendars;
	}

	public Long getParentElementKey() {
		return parentElementKey;
	}

	public void setParentElementKey(Long parentElementKey) {
		this.parentElementKey = parentElementKey;
	}

	public Long getCurriculumKey() {
		return curriculumKey;
	}

	public void setCurriculumKey(Long curriculumKey) {
		this.curriculumKey = curriculumKey;
	}

	public Long getCurriculumElementTypeKey() {
		return curriculumElementTypeKey;
	}

	public void setCurriculumElementTypeKey(Long curriculumElementTypeKey) {
		this.curriculumElementTypeKey = curriculumElementTypeKey;
	}

	
}