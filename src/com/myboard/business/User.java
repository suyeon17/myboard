package com.myboard.business;

import java.io.Serializable;
import java.util.Date;

import com.myboard.dao.Users;
import com.myboard.dao.UsersDao;

public class User implements Serializable {
	
	private static final long serialVersionUID = 9033128014477448074L;
	
	private String uid;
	private String firstName;
	private String lastName;
	private int department;
	private String password;
	private int permissionId;
	private Date creationDate;
	private Date lastLogin;
	private String privateDirectory;
	private boolean active;
	private String emailAddress;
	
	public static final int INVALID_DEPARTMENT = -1;
	public static final int INVALID_ACCOUNT_PERMISSIONS = -1;
	
	public enum AccountPermissions{
		ADMINISTRATOR,
		DEPARTMENT_ADMINISTRATOR,
		FACULTY,
		REGULAR_USER;
	}
	
	public enum Department{
		NO_ASSOCIATION,
		COMPUTER_SCIENCE,
		MATHEMATICS;
	}
	
	public User(){
		this.uid = "";
		this.firstName = "";
		this.lastName = "";
		this.password = "";
		this.privateDirectory = "";
		this.emailAddress = "";
		
	}
	
	public User(String id){
		this();
		this.uid = id;
	}
	
	public void createUser(){
		this.active = true;
		this.privateDirectory = "/" + this.uid + "/";
		this.creationDate = new Date();
		this.lastLogin = this.creationDate;
		
		UsersDao dao = new UsersDao();
		
		Users users = new Users(this.uid, this.firstName, this.lastName, this.department, this.password, this.permissionId,
				                this.creationDate, this.lastLogin, this.privateDirectory, this.active, this.emailAddress);
		
		dao.create(users);
	}
	
	public void updateUser(){		
		UsersDao dao = new UsersDao();
		
		Users users = dao.read(this.uid);
		
		if(users != null){
			users.setPassword(!this.password.isEmpty() && this.password != users.getPassword() ? this.password : users.getPassword());
			users.setFirstName(!this.firstName.isEmpty() && this.firstName != users.getFirstName() ? this.firstName : users.getFirstName());
			users.setLastName(!this.lastName.isEmpty() && this.lastName != users.getLastName() ? this.lastName : users.getLastName());
			users.setDepartment(this.department != User.INVALID_DEPARTMENT && this.department != users.getDepartment() ? this.department : users.getDepartment());
			users.setPermissionId(this.permissionId != User.INVALID_ACCOUNT_PERMISSIONS && this.permissionId != users.getPermissionId() ? this.permissionId : users.getPermissionId());
			users.setEmailAddress(!this.emailAddress.isEmpty() && this.emailAddress != users.getEmailAddress() ? this.emailAddress : users.getEmailAddress());
			dao.update(users);
		}
	}
	
	public void readUser(){
		UsersDao dao = new UsersDao();
		Users users = dao.read(this.getUid());
		
		if(users != null){
			this.setFirstName(users.getFirstName());
			this.setLastName(users.getLastName());
			this.setDepartment(users.getDepartment());
			this.setPermissionId(users.getPermissionId());
			this.setEmailAddress(users.getEmailAddress());
			this.setPassword(users.getPassword());
		}
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid.trim();
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName.trim();
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName.trim();
	}

	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.trim();
	}

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPrivateDirectory() {
		return privateDirectory;
	}

	public void setPrivateDirectory(String privateDirectory) {
		this.privateDirectory = privateDirectory.trim();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress.trim();
	}
	
}
