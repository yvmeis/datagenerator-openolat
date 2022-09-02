package com.frentix.datagenerator.VOs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;



import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 * Initial date: 5 Oct 2017<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "taxonomyLevelVO")
public class TaxonomyLevelVO {
	
	private Long key;
	private String identifier;
	private String displayName;
	private String description;
	private String externalId;
	
	private Long parentKey;
	private Long typeKey;
	
	@Schema(required = true, description = "Action to be performed on managedFlags", allowableValues = { 
			"all",
			 "identifier(all)",
			 "displayName(all)",
			 "description(all)",
			 "externalId(all)",
			 "sortOrder(all)",
			 "type(all)",
			 "competences(all)",
			   "manageCompetence(competences, all)",
			   "teachCompetence(competences, all)",
			   "haveCompetence(competences, all)",
			   "targetCompetence(competences, all)",
			 "move(all)",
			 "delete(all)"})
	private String managedFlags;
	
	public TaxonomyLevelVO() {
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

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getManagedFlags() {
		return managedFlags;
	}

	public void setManagedFlags(String managedFlags) {
		this.managedFlags = managedFlags;
	}

	public Long getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(Long typeKey) {
		this.typeKey = typeKey;
	}

	public Long getParentKey() {
		return parentKey;
	}

	public void setParentKey(Long parentKey) {
		this.parentKey = parentKey;
	}
}