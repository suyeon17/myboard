package com.myboard.dao;

// Generated Mar 13, 2013 12:50:36 AM by Hibernate Tools 4.0.0

/**
 * CourseUsers generated by hbm2java
 */
public class CourseUsers implements java.io.Serializable {

	private Integer courseUid;
	private int courseId;
	private String uid;
	private int roleId;

	public CourseUsers() {
	}

	public CourseUsers(int courseId, String uid, int roleId) {
		this.courseId = courseId;
		this.uid = uid;
		this.roleId = roleId;
	}

	public Integer getCourseUid() {
		return this.courseUid;
	}

	public void setCourseUid(Integer courseUid) {
		this.courseUid = courseUid;
	}

	public int getCourseId() {
		return this.courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}
