/*
 * The User class is the 2nd level of abstraction
 * (business logic) between the end user and the database.
 * It does the heavy lifting for creating, reading, and updating
 * database information about specific users.
 * 
 * Should work as is.
 */

package com.myboard.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.myboard.dao.AccountPermissionsDao;
import com.myboard.dao.CourseUsers;
import com.myboard.dao.Courses;
import com.myboard.dao.DepartmentDao;
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
	
	private Set<CourseUsers> courseUsers;
	private Set<Courses> userCourses;
	
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
		
		com.myboard.dao.Department d = getDeptObjById();
		if(d == null) return;
		com.myboard.dao.AccountPermissions p = getPermissionObjById();
		if(p == null) return;
		
		Users users = new Users(this.uid, this.firstName, this.lastName, d, this.password, p,
        this.creationDate, this.lastLogin, this.privateDirectory, this.active, this.emailAddress);
		dao.create(users);
	}
	
	private com.myboard.dao.Department getDeptObjById(){
		DepartmentDao dao = new DepartmentDao();
		return dao.read(this.department+"");
	}
	
	private com.myboard.dao.AccountPermissions getPermissionObjById(){
		AccountPermissionsDao dao = new AccountPermissionsDao();
		return dao.read(this.permissionId+"");
	}
	
	public void updateUser(){		
		UsersDao dao = new UsersDao();
		
		Users users = dao.read(this.uid);
		
		if(users != null){
			users.setPassword(!this.password.isEmpty() && this.password != users.getPassword() ? this.password : users.getPassword());
			users.setFirstName(!this.firstName.isEmpty() && this.firstName != users.getFirstName() ? this.firstName : users.getFirstName());
			users.setLastName(!this.lastName.isEmpty() && this.lastName != users.getLastName() ? this.lastName : users.getLastName());
			users.setDepartment(this.department != User.INVALID_DEPARTMENT && this.department != users.getDepartment().getDeptId() ? getDeptObjById() : users.getDepartment());
			users.setPermission(this.permissionId != User.INVALID_ACCOUNT_PERMISSIONS && this.permissionId != users.getPermission().getPermissionId() ? getPermissionObjById() : users.getPermission());
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
			this.setDepartment(users.getDepartment().getDeptId());
			this.setPermissionId(users.getPermission().getPermissionId());
			this.setEmailAddress(users.getEmailAddress());
			this.setPassword(users.getPassword());
			this.setCourseUsers(users.getCourseUsers());
		}
		
		if(this.courseUsers != null)
		{
			CourseUsers[] usersArr = new CourseUsers[courseUsers.size()];
			courseUsers.toArray(usersArr);
			userCourses = new HashSet<Courses>();
			for(int i=0; i<usersArr.length; i++)
			{
				if (usersArr[i].getCourse() != null) {
					userCourses.add(usersArr[i].getCourse());
				}
				System.out.println("Hit here in User: " + usersArr[i].getCourse().getSection().getCourseInfo().getCourseName());
			}
		}//if
	}//read

	public ArrayList<Course> getCourses() {
		/*
		 * userCourses is a Set<Courses>
		 * We want an ArrayList<Course>
		 * first we get an array of courses
		 * then we add those courses to our array list
		 */
		Courses[] coursesArr;
		coursesArr = userCourses.toArray(new Courses[userCourses.size()]);
		ArrayList<Course> courseList = new ArrayList<Course>();
		Course tempCourse;
		for (int i = 0; i < coursesArr.length; i++) {
			tempCourse = new Course();
			tempCourse.setCourseId(coursesArr[i].getCourseId().toString());
			tempCourse.readCourse();
			courseList.add(tempCourse);
		}
		
		if(courseList.size() == 0) {
			System.out.println("ERROR: COURSE LIST IS EMPTY");
		}
		
		return courseList;
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
	
	public Set<CourseUsers> getCourseUsers()
	{
		return courseUsers;
	}
	
	public void setCourseUsers(Set<CourseUsers> courseUsers)
	{
		this.courseUsers = courseUsers;
	}
	
}
