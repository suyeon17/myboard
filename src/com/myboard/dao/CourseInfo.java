package com.myboard.dao;

// Generated Mar 13, 2013 12:50:36 AM by Hibernate Tools 4.0.0

/**
 * CourseInfo generated by hbm2java
 */
public class CourseInfo implements java.io.Serializable {

	private Integer courseInfoId;
	private String courseId;
	private String courseName;
	private String courseDescription;
	private int department;
	private int credits;

	public CourseInfo() {
	}

	public CourseInfo(String courseId, String courseName,
			String courseDescription, int department, int credits) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.department = department;
		this.credits = credits;
	}

	public Integer getCourseInfoId() {
		return this.courseInfoId;
	}

	public void setCourseInfoId(Integer courseInfoId) {
		this.courseInfoId = courseInfoId;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDescription() {
		return this.courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public int getDepartment() {
		return this.department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	public int getCredits() {
		return this.credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

}
