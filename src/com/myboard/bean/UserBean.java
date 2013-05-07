/*
 * The UserBean class is the 1st abstraction layer for handling
 * user data and querying the database.
 * It contains the functionality for user creation, updating,
 * and searching.
 * 
 * ADDITIONAL WORK NEEDED:
 * The searchForExistingUser method reads all the information
 * needed to get the necessary information for the end user,
 * but the functionality needs to be added to display the
 * list of courses associated with the user (information that
 * can be obtained from the Set<CourseUsers> object. 
 */

package com.myboard.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.myboard.business.User;
import com.myboard.dao.CourseUsers;

public class UserBean implements Serializable{
	
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
	private Set<CourseUsers> courseUsers;
	
	public UserBean(){
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
		
		try{
	        user.createUser();
	        }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        	System.out.println(e.toString());
	        }
		
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
		this.courseUsers = user.getCourseUsers();
		
		//Use courseUsers to get course id to get Courses objects
		//that are associated with the user
		
		return "OK";
	}
	
	public String updateUser(){
		User user = new User();
		user.setUid(this.userId);
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setPassword(this.password);
		user.setDepartment(User.Department.valueOf(this.department).ordinal());
		user.setPermissionId(this.permissionId != "" ? User.AccountPermissions.valueOf(this.permissionId).ordinal() : User.INVALID_ACCOUNT_PERMISSIONS);
		user.setEmailAddress(this.email);
		user.setCourseUsers(this.courseUsers);
		
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
	
	public Set<CourseUsers> getCourseUsers() {
		return courseUsers;
	}

	public void setCourseUsers(Set<CourseUsers> courseUsers) {
		this.courseUsers = courseUsers;
	}

}
