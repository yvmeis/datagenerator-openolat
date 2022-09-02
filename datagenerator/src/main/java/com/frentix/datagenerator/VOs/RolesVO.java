package com.frentix.datagenerator.VOs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;




@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rolesVO")
public class RolesVO {
	
	private boolean systemAdmin = false;
	private boolean olatAdmin = false;
	private boolean userManager = false;
	private boolean groupManager = false;
	private boolean author = false;
	private boolean guestOnly = false;
	private boolean institutionalResourceManager = false;
	private boolean poolAdmin = false;
	private boolean curriculumManager = false;
	private boolean invitee = false;

	public RolesVO() {
		//for JAXB
	}


	public boolean isSystemAdmin() {
		return systemAdmin;
	}

	public void setSystemAdmin(boolean systemAdmin) {
		this.systemAdmin = systemAdmin;
	}

	public boolean isOlatAdmin() {
		return olatAdmin;
	}

	public void setOlatAdmin(boolean olatAdmin) {
		this.olatAdmin = olatAdmin;
	}

	public boolean isUserManager() {
		return userManager;
	}

	public void setUserManager(boolean userManager) {
		this.userManager = userManager;
	}

	public boolean isGroupManager() {
		return groupManager;
	}

	public void setGroupManager(boolean groupManager) {
		this.groupManager = groupManager;
	}

	public boolean isAuthor() {
		return author;
	}

	public void setAuthor(boolean author) {
		this.author = author;
	}

	public boolean isGuestOnly() {
		return guestOnly;
	}

	public void setGuestOnly(boolean guestOnly) {
		this.guestOnly = guestOnly;
	}

	public boolean isInstitutionalResourceManager() {
		return institutionalResourceManager;
	}

	public void setInstitutionalResourceManager(boolean institutionalResourceManager) {
		this.institutionalResourceManager = institutionalResourceManager;
	}

	public boolean isPoolAdmin() {
		return poolAdmin;
	}

	public void setPoolAdmin(boolean poolAdmin) {
		this.poolAdmin = poolAdmin;
	}

	public boolean isCurriculumManager() {
		return curriculumManager;
	}

	public void setCurriculumManager(boolean curriculumManager) {
		this.curriculumManager = curriculumManager;
	}

	public boolean isInvitee() {
		return invitee;
	}

	public void setInvitee(boolean invitee) {
		this.invitee = invitee;
	}

}
