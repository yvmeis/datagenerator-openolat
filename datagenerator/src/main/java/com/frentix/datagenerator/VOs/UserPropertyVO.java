package com.frentix.datagenerator.VOs;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * Description:<br>
 * 
 * <P>
 * Initial Date:  7 apr. 2010 <br>
 * @author srosse, stephane.rosse@frentix.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class UserPropertyVO {
  @XmlElement(name="name")
  public String name; 
  
  @XmlElement(name="value")
  public String value;
  
  public UserPropertyVO() {
  	//make jaxb happy
  }
  
  public UserPropertyVO(Map.Entry<String,String> e) {
     name = e.getKey();
     value = e.getValue();
  }
  
  public UserPropertyVO(String name, String value) {
    this.name = name;
    this.value = value;
  }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("userPropertyVo[").append(name).append(":").append(value).append("]");
		return sb.toString();
	}
}