package com.frentix.datagenerator.VOs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 * Initial date: 16 mai 2018<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "curriculumElementTypeVO")
public class CurriculumElementTypeVO {
	
	private Long key;
	private String identifier;
	private String displayName;
	private String description;
	private String cssClass;
	private String externalId;
	@Schema(required = true, description = "Action to be performed on managedFlagsString", allowableValues = { 
			"all",
			 "identifier(all)",
			 "displayName(all)",
			 "cssClass(all)",
			 "description(all)",
			 "externalId(all)",
			 "calendars(all)",
			 "lectures(all)",
			 "subTypes(all)",
			 "copy(all)",
			 "delete(all)"})
	private String managedFlagsString;
	@Schema(required = false, description = "Enable or disable the calendars aggregation", allowableValues = { 
			"enabled",
			"disabled",
			"inherited"})
	@XmlAttribute(name="calendars", required=false)
	private String calendars;
	@Schema(required = false, description = "Enable or disable the lecture block overview and aggregation", allowableValues = { 
			"enabled",
			"disabled",
			"inherited"})
	@XmlAttribute(name="lectures", required=false)
	private String lectures;
	
	public CurriculumElementTypeVO() {
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

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
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

	public String getLectures() {
		return lectures;
	}

	public void setLectures(String lectures) {
		this.lectures = lectures;
	}
}