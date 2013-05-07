package com.myboard.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import com.myboard.business.Course;

@ManagedBean
@RequestScoped
public class CourseBean implements Serializable {

	private static final long serialVersionUID = 7384206127172044784L;

	private String courseId;
	private String courseName;
	private String courseDescription;
	private int department;
	private int credits;
	private int section;
	private String semester;
	private String courseRootDirectory;

    public CourseBean() {
        
        this.courseId = "";
		this.courseName = "";
		this.courseDescription = "";
		this.courseRootDirectory = "";
    }
    
    public String createCourse(){
    	Course course = new Course();
		course.setCourseId(this.courseId);
		course.setCourseName(this.courseName);
		course.setCourseDescription(this.courseDescription);
		course.setDepartment(this.department);
		course.setCredits(this.credits);
		course.setSection(this.section);
		course.setSemester(this.semester);
		course.setCourseRootDirectory(this.courseRootDirectory);
		
		try{
	        course.createCourse();
	        }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        	System.out.println(e.toString());
	        }
		
		return "OK";
	}
	
	public String searchForExistingCourse(){
		Course course = new Course(this.courseId);
		course.readCourse();
		this.courseName = course.getCourseName();
		this.courseDescription = course.getCourseDescription();
		this.department = course.getDepartment();
		this.credits = course.getCredits();
		this.section = course.getSection();
		this.semester = course.getSemester();
		this.courseRootDirectory = course.getCourseRootDirectory();
		
		return "OK";
	}
	
	public String updateCourse(){
		Course course = new Course();
		course.setCourseId(this.courseId);
		course.setCourseName(this.courseName);
		course.setCourseDescription(this.courseDescription);
		course.setCredits(this.credits);
		course.setDepartment(this.department);
		course.setSection(this.section);
		course.setSemester(this.semester);
		course.setCourseRootDirectory(this.courseRootDirectory);
		
		course.updateCourse();
		
		return "OK";
	}
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId.trim();
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName.trim();
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription.trim();
	}

	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}
	
	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getCourseRootDirectory() {
		return courseRootDirectory;
	}

	public void setCourseRootDirectory(String courseRootDirectory) {
		this.courseRootDirectory = courseRootDirectory.trim();
	}
}// class