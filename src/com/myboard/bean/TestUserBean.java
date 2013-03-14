package com.myboard.bean;

import java.io.Serializable;
import java.util.Date;

import com.myboard.business.User;

public class TestUserBean implements Serializable{
	
	private static final long serialVersionUID = -541159628249307804L;
	
	private String userId;
	private String firstName;
	private String lastName;
	private String department;
	private String password;
	private String permissionId;
	private String privateDirectory;
	private Date creationDateTime;
	private Date lastLoginDateTime;
	private boolean active;
	private String email;
	
	public TestUserBean(){
		this.userId = "";
		this.firstName = "";
		this.lastName = "";
		this.password = "";
	}
	
	public String createUser(){
		User user = new User();
		user.setUid(this.userId);
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setPassword(this.password);
		user.setDepartment(User.Department.valueOf(this.department).ordinal());
		user.setPermissionId(User.AccountPermissions.valueOf(this.permissionId).ordinal());
		user.setEmailAddress(this.email);
		
		user.createUser();
		
		return "OK";
	}
	
	public String searchForExistingUser(){
		User user = new User(this.userId);
		user.readUser();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.department = User.Department.values()[user.getDepartment()].toString();
		this.permissionId = User.AccountPermissions.values()[user.getPermissionId()].toString();
		this.email = user.getEmailAddress();
		
		return "OK";
	}
	
	public String updateUser(){
		User user = new User();
		user.setUid(this.userId);
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setPassword(this.password);
		user.setDepartment(this.department != "" ? User.Department.valueOf(this.department).ordinal() : User.INVALID_DEPARTMENT);
		user.setPermissionId(this.permissionId != "" ? User.AccountPermissions.valueOf(this.permissionId).ordinal() : User.INVALID_ACCOUNT_PERMISSIONS);
		user.setEmailAddress(this.email);
		
		user.updateUser();
		
		return "OK";
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getPrivateDirectory() {
		return privateDirectory;
	}

	public void setPrivateDirectory(String privateDirectory) {
		this.privateDirectory = privateDirectory;
	}

	public Date getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public Date getLastLoginDateTime() {
		return lastLoginDateTime;
	}

	public void setLastLoginDateTime(Date lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
