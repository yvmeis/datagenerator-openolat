package com.frentix.datagenerator.VOs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 
 * Initial date: 9 août 2021<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "repositoryEntryEducationalTypeVO")
public class RepositoryEntryEducationalTypeVO {
	
	private Long key;
	
	private String identifier;
	private Boolean predefined;
	private String cssClass;
	
	public RepositoryEntryEducationalTypeVO() {
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
	
	public Boolean getPredefined() {
		return predefined;
	}
	
	public void setPredefined(Boolean predefined) {
		this.predefined = predefined;
	}
	
	public String getCssClass() {
		return cssClass;
	}
	
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
	@Override
	public String toString() {
		return "RepositoryEntryEducationalTypeVO[key=" + key + ":identifier=" + identifier + "]";
	}
	
	@Override
	public int hashCode() {
		return getKey() == null ? -256471238 : getKey().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(obj instanceof RepositoryEntryEducationalTypeVO) {
			RepositoryEntryEducationalTypeVO vo = (RepositoryEntryEducationalTypeVO)obj;
			return key != null && key.equals(vo.key);
		}
		return false;
	}
}