package com.frentix.datagenerator.VOs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * Initial Date:  7 apr. 2010 <br>
 * @author srosse, stephane.rosse@frentix.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "repositoryEntryVO")
public class RepositoryEntryVO {
	
	private Long key;
	private String softkey;
	private String resourcename;
	private String displayname;
	private String description;
	private String teaser;
	@XmlAttribute(name="authors",required=false)
	private String authors;
	@XmlAttribute(name="location",required=false)
	private String location;
	private String externalId;
	private String externalRef;
	 @Schema(required = true, description = "Action to be performed on managedFlags", allowableValues = { "all",
			  "editcontent(all)",
			    "details(all) //details tab",
			      "title(details,all)",
			      "description(details,all)",
			      "objectives(details,all)",
			      "requirements(details,all)",
			      "credits(details,all)",
			      "location(details,all)",
			      "organisations(details,all)",
			    "settings(all) //max num of participants...",
			     "access(settings,all)",
			      "search(settings, all)",
			      "chat(settings,all)",
			      "layout(settings,all)",
			      "resourcefolder(settings,all)",
			      "efficencystatement(settings,all)",
			      "calendar(settings,all)",
			      "glossary(settings,all)",
			    "lecture(all)",
			      "lectureconfig(lecture, all)",
			      "lecturemanagement(lecture, all)",
			    "bookings(all) // change booking rules",
			    "membersmanagement(all)",
			    "groups(all)",
			    "close(all)",
			    "copy(all)",
			    "delete(all)"})	
	private String managedFlags;
	private Long resourceableId;
	private String resourceableTypeName;

	private Long olatResourceKey;
	private Long olatResourceId;
	private String olatResourceTypeName;
	
	private String entryStatus;
	
	private RepositoryEntryLifecycleVO lifecycle;
	
	public RepositoryEntryVO() {
		//
	}
	
	public Long getKey() {
		return key;
	}
	
	public void setKey(Long key) {
		this.key = key;
	}
	
	public String getSoftkey() {
		return softkey;
	}
	
	public void setSoftkey(String softkey) {
		this.softkey = softkey;
	}
	
	public String getResourcename() {
		return resourcename;
	}
	
	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}
	
	public String getDisplayname() {
		return displayname;
	}
	
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTeaser() {
		return teaser;
	}

	public void setTeaser(String teaser) {
		this.teaser = teaser;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getExternalRef() {
		return externalRef;
	}

	public void setExternalRef(String externalRef) {
		this.externalRef = externalRef;
	}

	public String getManagedFlags() {
		return managedFlags;
	}

	public void setManagedFlags(String managedFlags) {
		this.managedFlags = managedFlags;
	}

	public Long getResourceableId() {
		return resourceableId;
	}

	public void setResourceableId(Long resourceableId) {
		this.resourceableId = resourceableId;
	}

	public String getResourceableTypeName() {
		return resourceableTypeName;
	}

	public void setResourceableTypeName(String resourceableTypeName) {
		this.resourceableTypeName = resourceableTypeName;
	}

	public Long getOlatResourceKey() {
		return olatResourceKey;
	}

	public void setOlatResourceKey(Long olatResourceKey) {
		this.olatResourceKey = olatResourceKey;
	}

	public Long getOlatResourceId() {
		return olatResourceId;
	}

	public void setOlatResourceId(Long olatResourceId) {
		this.olatResourceId = olatResourceId;
	}

	public String getOlatResourceTypeName() {
		return olatResourceTypeName;
	}

	public void setOlatResourceTypeName(String olatResourceTypeName) {
		this.olatResourceTypeName = olatResourceTypeName;
	}

	public String getEntryStatus() {
		return entryStatus;
	}

	public void setEntryStatus(String entryStatus) {
		this.entryStatus = entryStatus;
	}

	public RepositoryEntryLifecycleVO getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(RepositoryEntryLifecycleVO lifecycle) {
		this.lifecycle = lifecycle;
	}

	@Override
	public String toString() {
		return "RepositoryEntryVO[key=" + key + ":name=" + resourcename + ":display=" + displayname + "]";
	}
	
	@Override
	public int hashCode() {
		return getKey() == null ? -9331238 : getKey().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(obj instanceof RepositoryEntryVO) {
			RepositoryEntryVO vo = (RepositoryEntryVO)obj;
			return key != null && key.equals(vo.key);
		}
		return false;
	}
}