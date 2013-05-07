/*
 * The CourseBean class is part of the first layer
 * of abstraction between the end user and the database.
 * It contains all of the methods for creating, reading,
 * and updating course objects.
 * 
 * NEEDS ADDITIONAL WORK
 * Not all of the information obtained has been handled
 * yet. Functionality needs to be added to handle the
 * different Sets on information (CourseUsers, Assignments,
 * etc.)
 */

package com.myboard.bean;

import java.io.Serializable;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import com.myboard.business.Course;
import com.myboard.dao.Announcements;
import com.myboard.dao.Assignments;
import com.myboard.dao.CourseMaterial;
import com.myboard.dao.CourseUsers;
import com.myboard.dao.Message;

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
	private Set<CourseUsers> users;
	private Set<Announcements> announcements;
	private Set<CourseMaterial> materials;
	private Set<Message> messages;
	private Set<Assignments> assignments;

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
		course.setUsers(this.users);
		course.setAnnouncements(this.announcements);
		course.setMaterials(this.materials);
		course.setMessages(this.messages);
		course.setAssignments(this.assignments);
		
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
		this.users = course.getUsers();
		this.announcements = course.getAnnouncements();
		this.materials = course.getMaterials();
		this.messages = course.getMessages();
		this.assignments = course.getAssignments();
		
		return "OK";
		
		/* The following code will allow you to access each individual
		 * element contained in each of the sets.
		 * Functionality needs to be added to handle the elements
		 * in the desired fashion
		 */
		/*
		if (this.users != null) {
			CourseUsers[] useArr = new CourseUsers[users.size()];
			users.toArray(useArr);
			for (int i = 0; i < users.size(); i++) {
				System.out.println("HIT HERE: "
						+ useArr[i].getUser().getUid().toString());
			}
		}// if

		if (this.announcements != null) {
			Announcements[] annArr = new Announcements[announcements.size()];
			announcements.toArray(annArr);
			for (int i = 0; i < announcements.size(); i++) {
				// code to handle here
			}
		}
		
		if (this.materials != null) {
			CourseMaterial[] matArr = new CourseMaterial[materials.size()];
			materials.toArray(matArr);
			for (int i = 0; i < materials.size(); i++) {
				// code to handle here
			}
		}
		if (this.messages != null) {
			Message[] messArr = new Message[messages.size()];
			messages.toArray(messArr);
			for (int i = 0; i < messages.size(); i++) {
				// code to handle here
			}
		}
		if (this.assignments != null) {
			Assignments[] assgnArr = new Assignments[assignments.size()];
			assignments.toArray(assgnArr);
			for (int i = 0; i < assignments.size(); i++) {
				// code to handle here
			}
		}
		*/
	}//searchForExistingUser
	
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
		course.setUsers(this.users);
		course.setAnnouncements(this.announcements);
		course.setMaterials(this.materials);
		course.setMessages(this.messages);
		course.setAssignments(this.assignments);
		
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
	public Set<CourseUsers> getUsers() {
		return users;
	}

	public void setUsers(Set<CourseUsers> users) {
		this.users = users;
	}

	public Set<Announcements> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(Set<Announcements> announcements) {
		this.announcements = announcements;
	}

	public Set<CourseMaterial> getMaterials() {
		return materials;
	}

	public void setMaterials(Set<CourseMaterial> materials) {
		this.materials = materials;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Set<Assignments> getAssignments() {
		return assignments;
	}

	public void setAssignments(Set<Assignments> assignments) {
		this.assignments = assignments;
	}
	
}// class