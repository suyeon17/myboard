/*
 * The Course class is a 2nd abstraction level class
 * (business logic). It contains all the functionality
 * to read and write (create, update) courses to the
 * database.
 * 
 * The structure of information is a little complicated.
 * Each Courses object has an associated section id to a
 * CourseSection object.
 * 
 * Each CourseSection object has an associated course info id
 * associated with a CourseInfo object.
 * 
 * The process of creating a new course also creates a 
 * CourseSection object and a CourseInfo object as needed.
 * 
 * ADDITIONAL WORK NEEDED:
 * Testing needs to be done to ensure that duplicates of
 * CoureInfo objects aren't being created. If duplicates
 * are being created, testing probably needs to trickle back
 * to the addClass.xhtml page and the requirements it asks
 * for to create the new class.
 */

package com.myboard.business;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.myboard.dao.Announcements;
import com.myboard.dao.Assignments;
import com.myboard.dao.CourseInfo;
import com.myboard.dao.CourseInfoDao;
import com.myboard.dao.CourseMaterial;
import com.myboard.dao.CourseSection;
import com.myboard.dao.CourseSectionDao;
import com.myboard.dao.CourseUsers;
import com.myboard.dao.Courses;
import com.myboard.dao.CoursesDao;
import com.myboard.dao.DepartmentDao;
import com.myboard.dao.Message;
import com.myboard.dao.SemesterDao;

public class Course implements Serializable {

	private static final long serialVersionUID = 9033128014477448074L;

	//private Integer courseInfoId;
	/*
	 * Max: 
	 * 		I had to comment out every single reference to CourseInfo, because
	 * 		there were constant issues with CourseInfoId that I can't figure out
	 */
	private String courseId;
	private String courseName;
	private String courseDescription;
	private int department;
	private int credits;
	private int section;
	private String semester;
	private String courseRootDirectory;
	private boolean active;
	private Date creationDate;

	private Set<CourseUsers> users;
	private Set<Announcements> announcements;
	private Set<CourseMaterial> materials;
	private Set<Message> messages;
	private Set<Assignments> assignments;

	public static final int INVALID_DEPARTMENT = -1;

	public enum Department {
		NO_ASSOCIATION, COMPUTER_SCIENCE, MATHEMATICS;
	}

	public enum section {
		NO_ASSOCIATION, SECTION_ONE, SECTION_TWO, SECTION_FIFTY;
	}

	public Course() {
		//this. courseInfoId = 0;
		this.courseId = "";
		this.courseName = "";
		this.courseDescription = "";
		this.courseRootDirectory = "";
	}

	public Course(String id) {
		this();
		this.courseId = id;
	}

	public void createCourse() {
		System.out.println("Hit here from Course.java");
		this.active = true;
		//this.courseRootDirectory = "/" + this.courseId + "/";
		this.creationDate = new Date();
		CoursesDao dao = new CoursesDao();
		Courses courses = dao.read(Integer.parseInt(this.courseId));
		
		if(courses != null)
		{
			//check section
			CourseSectionDao dao2 = new CourseSectionDao();
			CourseSection courseSection = dao2.read(Integer.toString(courses.getSection().getCourseSectionId()));
			if(courseSection != null)
			{
				//read courseInfo
				CourseInfoDao dao3 = new CourseInfoDao();
				CourseInfo courseInfo = dao3.read(courseSection.getCourseInfo().getCourseId());
				if(courseInfo != null)
				{}
				else
				{
					//new courseInfo
					courseInfo = new CourseInfo(this.courseId, this.courseName,
							this.courseDescription, getDeptObjById(), this.credits);
					dao3.create(courseInfo);
				}
			}
			else
			{
				//new courseSection + new courseInfo
				CourseInfoDao dao3 = new CourseInfoDao();
				CourseInfo courseInfo = new CourseInfo(this.courseId, this.courseName,
						this.courseDescription, getDeptObjById(), this.credits);
				dao3.create(courseInfo);
				courseSection = new CourseSection(courseInfo, this.section);
				dao2.create(courseSection);
			}
			
		}
		else
		{
			//new courses + courseSection + new courseInfo
			CourseSectionDao dao2 = new CourseSectionDao();
			CourseInfoDao dao3 = new CourseInfoDao();
			
			CourseInfo courseInfo = new CourseInfo(this.courseId, this.courseName,
					this.courseDescription, getDeptObjById(), this.credits);
			dao3.create(courseInfo);
			CourseSection courseSection = new CourseSection(courseInfo, this.section);
			dao2.create(courseSection);
			courses = new Courses(courseSection, getSemesterObjById());
			dao.create(courses);
		}
	}// create

	private com.myboard.dao.Department getDeptObjById() {
		DepartmentDao dao = new DepartmentDao();
		return dao.read(this.department+"");
	}

	private com.myboard.dao.Semester getSemesterObjById() {
		SemesterDao dao = new SemesterDao();
		return dao.read(this.semester);
	}

	public void updateCourse() {
		try{
		CoursesDao dao = new CoursesDao();
		CourseInfoDao dao2 = new CourseInfoDao();
		CourseSectionDao dao3 = new CourseSectionDao();

		Courses courses = dao.read(Integer.parseInt(this.courseId));
		CourseInfo courseInfo = courses.getSection().getCourseInfo();
		CourseSection courseSection = courses.getSection();

		if (courses != null && courseInfo != null) {
			courseInfo
					.setCourseName(!this.courseName.isEmpty()
							&& this.courseName != courseInfo.getCourseName() ? this.courseName
							: courseInfo.getCourseName());
			courseInfo.setCourseDescription(!this.courseDescription.isEmpty()
					&& this.courseDescription != courseInfo
							.getCourseDescription() ? this.courseDescription
					: courseInfo.getCourseDescription());
			courseInfo
					.setDepartment(this.department != Course.INVALID_DEPARTMENT
							&& this.department != courseInfo.getDepartment()
									.getDeptId() ? getDeptObjById()
							: courseInfo.getDepartment());
			courses.setSemester(!this.semester.isEmpty()
					&& this.semester != courses.getSemester().getSemesterName() ? getSemesterObjById()
					: courses.getSemester());
			
			courseInfo.setCredits(this.credits);
			courseSection.setSection(this.section);
			courses.setUsers(this.users);
			courses.setAnnouncements(this.announcements);
			courses.setMaterials(this.materials);
			courses.setMessages(this.messages);
			courses.setAssignments(this.assignments);
			dao.update(courses);
			dao2.update(courseInfo);
			dao3.update(courseSection);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void readCourse() {

		CoursesDao dao = new CoursesDao();
		Courses courses = dao.read(Integer.parseInt(this.courseId));

		if (courses != null) {
			this.setSection(courses.getSection().getSection());
			this.setSemester(courses.getSemester().getSemesterName());
			this.setUsers(courses.getUsers());
			this.setAnnouncements(courses.getAnnouncements());
			this.setMaterials(courses.getMaterials());
			this.setMessages(courses.getMessages());
			this.setAssignments(courses.getAssignments());
			this.setCourseRootDirectory(courses.getCourseRootDirectory());
		}
		
		CourseInfo courseInfo = courses.getSection().getCourseInfo();

		if (courseInfo != null) {
			this.setCourseName(courseInfo.getCourseName());
			this.setCourseDescription(courseInfo.getCourseDescription());
			this.setDepartment(courseInfo.getDepartment().getDeptId());
			this.setCredits(courseInfo.getCredits());
		}

	}// read

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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCourseRootDirectory() {
		return courseRootDirectory;
	}

	public void setCourseRootDirectory(String courseRootDirectory) {
		this.courseRootDirectory = courseRootDirectory.trim();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
	
}