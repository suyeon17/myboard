package com.myboard.business;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.myboard.dao.Announcements;
//import com.myboard.dao.CourseInfo;
//import com.myboard.dao.CourseInfoDao;
import com.myboard.dao.CourseSectionDao;
import com.myboard.dao.CourseUsers;
import com.myboard.dao.CourseUsersDao;
import com.myboard.dao.Courses;
import com.myboard.dao.CoursesDao;
import com.myboard.dao.DepartmentDao;
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
	private String courseUsersId;
	
	public static final int INVALID_DEPARTMENT = -1;
	
	public enum Department{
		NO_ASSOCIATION,
		COMPUTER_SCIENCE,
		MATHEMATICS;
	}
	
	public enum section{
		NO_ASSOCIATION,
		SECTION_ONE,
		SECTION_TWO,
		SECTION_FIFTY;
	}
	
	public Course(){
		//this. courseInfoId = 0;
		this.courseId = "";
		this.courseName = "";
		this.courseDescription = "";
		this.courseRootDirectory = "";
	}
	
	public Course(String id){
		this();
		this.courseId = id;
	}
	
	public void createCourse(){
		this.active = true;
		this.courseRootDirectory = "/" + this.courseId + "/";
		this.creationDate = new Date();
		
		//Create Courses Object
		CoursesDao dao = new CoursesDao();
		
		com.myboard.dao.Department d = getDeptObjById();
		if(d == null) return;
		
		com.myboard.dao.CourseSection cs = getSectionObjById();
		if(cs == null) return;
		
		com.myboard.dao.Semester s = getSemesterObjById();
		if(s == null) return;

		Courses courses = new Courses(cs, s, 
				this.courseRootDirectory);
				
		dao.create(courses);
		
		//Create CourseInfo Object
		//CourseInfoDao dao2 = new CourseInfoDao();
		
		com.myboard.dao.Department d2 = getDeptObjById();
		if(d2 == null) return;

//		CourseInfo courseInfo = new CourseInfo(this.courseId, this.courseName,
//				this.courseDescription, d2, this.credits);
		
		//dao2.create(courseInfo);
	}// create
	
	private com.myboard.dao.CourseSection getSectionObjById(){
		CourseSectionDao dao = new CourseSectionDao();
		return dao.read(Integer.toString(this.section));
	}
	
	private com.myboard.dao.Department getDeptObjById(){
		DepartmentDao dao = new DepartmentDao();
		return dao.read("0");
		//return dao.read(this.department+"");
	}
	
	private com.myboard.dao.Semester getSemesterObjById(){
		SemesterDao dao = new SemesterDao();
		return dao.read(this.semester);
	}
	
	
	public void updateCourse(){		
		CoursesDao dao = new CoursesDao();
	//	CourseInfoDao dao2 = new CourseInfoDao();
		
		Courses courses = dao.read(Integer.parseInt(courseId));
	//	CourseInfo courseInfo = dao2.read(courseId);
		
		if(courses != null){
		//	courseInfo.setCourseName(!this.courseName.isEmpty() && this.courseName != courseInfo.getCourseName() ? this.courseName : courseInfo.getCourseName());
		//	courseInfo.setCourseDescription(!this.courseDescription.isEmpty() && this.courseDescription != courseInfo.getCourseDescription() ? this.courseDescription : courseInfo.getCourseDescription());
		//	courseInfo.setDepartment(this.department != Course.INVALID_DEPARTMENT && this.department != courseInfo.getDepartment().getDeptId() ? getDeptObjById() : courseInfo.getDepartment());
		//	courseInfo.setCredits(this.credits >= 0 && this.credits != courseInfo.getCredits() ? this.credits : courseInfo.getCredits());
			courses.setSection(this.section != Course.INVALID_DEPARTMENT && this.section != courses.getSection().getCourseSectionId() ? getSectionObjById() : courses.getSection());
			courses.setSemester(!this.semester.isEmpty() && this.semester != courses.getSemester().getSemesterName() ? getSemesterObjById() : courses.getSemester());
			dao.update(courses);
		}
	}
	
	public void readCourse(){
		//CourseInfoDao dao = new CourseInfoDao();
		//CourseInfo courseInfo = dao.read(this.getCourseId());
		
//		if(courseInfo != null){
//			this.setCourseName(courseInfo.getCourseName());
//			this.setCourseDescription(courseInfo.getCourseDescription());
//			this.setDepartment(courseInfo.getDepartment().getDeptId());
//			this.setCredits(courseInfo.getCredits());
//		}
		
		CoursesDao dao2 = new CoursesDao();
		Courses courses = dao2.read(Integer.parseInt(this.getCourseId()));
		
		if(courses != null){
			this.setSection(courses.getSection().getCourseSectionId());
			this.setSemester(courses.getSemester().getSemesterName());
			this.setUsers(courses.getUsers());
			this.setAnnouncements(courses.getAnnouncements());
		}
		
		if(this.users != null)
		{
			CourseUsers[] useArr = new CourseUsers[users.size()]; 
			users.toArray(useArr);
			for(int i=0; i<users.size(); i++)
			{
				System.out.println("HIT HERE: "+useArr[i].getUser().getUid().toString());
			}
		}//if
		
		if(this.announcements != null)
		{
			Announcements[] annArr = new Announcements[announcements.size()];
			announcements.toArray(annArr);
			for(int i=0; i<announcements.size(); i++)
			{
				//code to handle here
			}
		}
	}//read

	public Course copy() {
		Course copiedCourse = new Course();
		
		copiedCourse.active = this.active;
		copiedCourse.courseDescription = this.courseDescription;
		copiedCourse.courseId = this.courseId;
		copiedCourse.courseName = this.courseName;
		copiedCourse.courseRootDirectory = this.courseRootDirectory;
		copiedCourse.courseUsersId = this.courseUsersId;
		copiedCourse.creationDate = this.creationDate;
		copiedCourse.credits = this.credits;
		copiedCourse.department = this.department;
		copiedCourse.semester = this.semester;
		copiedCourse.section = this.section;
		copiedCourse.users = this.users;
		
		return copiedCourse;
	}
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId.toString();
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
	
	public Set<CourseUsers> getUsers()
	{
		return users;
	}
	
	public void setUsers(Set<CourseUsers> users)
	{
		this.users = users;
	}
	
	public Set<Announcements> getAnnouncements()
	{
		return announcements;
	}
	
	public void setAnnouncements(Set<Announcements> announcements)
	{
		this.announcements = announcements;
	}
}