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
@XmlRootElement(name = "taxonomyLevelTypeVO")
public class TaxonomyLevelTypeVO {
	
	private Long key;
	private String identifier;
	private String displayName;
	private String description;
	private String externalId;

	@Schema(required = true, description = "Action to be performed on managedFlags", allowableValues = { 
			"all",
			 "identifier(all)",
			 "displayName(all)",
			 "description(all)",
			 "cssClass(all)",
			 "externalId(all)",
			 "visibility(all)",
			 "subTypes(all)",
			 "librarySettings(all)",
			 "copy(all)",
			 "delete(all)"})
	private String managedFlags;
	
	private String cssClass;
	private Boolean visible;

	private Boolean documentsLibraryEnabled;
	private Boolean documentsLibraryManagerCompetenceEnabled;
	private Boolean documentsLibraryTeachCompetenceReadEnabled;
	private Integer documentsLibraryTeachCompetenceReadParentLevels;
	private Boolean documentsLibraryTeachCompetenceWriteEnabled;
	private Boolean documentsLibraryHaveCompetenceReadEnabled;
	private Boolean documentsLibraryTargetCompetenceReadEnabled;
	
	public TaxonomyLevelTypeVO() {
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

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Boolean getDocumentsLibraryEnabled() {
		return documentsLibraryEnabled;
	}

	public void setDocumentsLibraryEnabled(Boolean documentsLibraryEnabled) {
		this.documentsLibraryEnabled = documentsLibraryEnabled;
	}

	public Boolean getDocumentsLibraryManagerCompetenceEnabled() {
		return documentsLibraryManagerCompetenceEnabled;
	}

	public void setDocumentsLibraryManagerCompetenceEnabled(Boolean documentsLibraryManagerCompetenceEnabled) {
		this.documentsLibraryManagerCompetenceEnabled = documentsLibraryManagerCompetenceEnabled;
	}

	public Boolean getDocumentsLibraryTeachCompetenceReadEnabled() {
		return documentsLibraryTeachCompetenceReadEnabled;
	}

	public void setDocumentsLibraryTeachCompetenceReadEnabled(Boolean documentsLibraryTeachCompetenceReadEnabled) {
		this.documentsLibraryTeachCompetenceReadEnabled = documentsLibraryTeachCompetenceReadEnabled;
	}

	public Integer getDocumentsLibraryTeachCompetenceReadParentLevels() {
		return documentsLibraryTeachCompetenceReadParentLevels;
	}

	public void setDocumentsLibraryTeachCompetenceReadParentLevels(
			Integer documentsLibraryTeachCompetenceReadParentLevels) {
		this.documentsLibraryTeachCompetenceReadParentLevels = documentsLibraryTeachCompetenceReadParentLevels;
	}

	public Boolean getDocumentsLibraryTeachCompetenceWriteEnabled() {
		return documentsLibraryTeachCompetenceWriteEnabled;
	}

	public void setDocumentsLibraryTeachCompetenceWriteEnabled(Boolean documentsLibraryTeachCompetenceWriteEnabled) {
		this.documentsLibraryTeachCompetenceWriteEnabled = documentsLibraryTeachCompetenceWriteEnabled;
	}

	public Boolean getDocumentsLibraryHaveCompetenceReadEnabled() {
		return documentsLibraryHaveCompetenceReadEnabled;
	}

	public void setDocumentsLibraryHaveCompetenceReadEnabled(Boolean documentsLibraryHaveCompetenceReadEnabled) {
		this.documentsLibraryHaveCompetenceReadEnabled = documentsLibraryHaveCompetenceReadEnabled;
	}

	public Boolean getDocumentsLibraryTargetCompetenceReadEnabled() {
		return documentsLibraryTargetCompetenceReadEnabled;
	}

	public void setDocumentsLibraryTargetCompetenceReadEnabled(Boolean documentsLibraryTargetCompetenceReadEnabled) {
		this.documentsLibraryTargetCompetenceReadEnabled = documentsLibraryTargetCompetenceReadEnabled;
	}
}
