package com.frentix.datagenerator.VOs;

import java.util.ArrayList;
import java.util.List;


public class UserVO {

	private Long key;
	private String login;
	private String externalId;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String portrait;


	private List<UserPropertyVO> properties = new ArrayList<UserPropertyVO>();

	public UserVO() {
		
	}
	
	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public List<UserPropertyVO> getProperties() {
		return properties;
	}

	public void setProperties(List<UserPropertyVO> properties) {
		this.properties = properties;
	}
	
	public void putProperty(String name, String value) {
		properties.add(new UserPropertyVO(name,value));
	}
	
	public String getProperty(String name) {
		for(UserPropertyVO entry:properties) {
			if(entry.getName().equals(name)) {
				return entry.getValue();
			}
		}
		return null;
	}
	public void editProperty(String name, String value) {
		for(UserPropertyVO entry:properties) {
			if(entry.getName().equals(name)) {
				entry.setValue(value);
			}
		}
		
	}

}