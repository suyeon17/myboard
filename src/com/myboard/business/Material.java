package com.myboard.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.myboard.dao.CourseMaterial;
import com.myboard.dao.CourseMaterialDao;
import com.myboard.dao.CourseUsers;
import com.myboard.dao.Courses;
import com.myboard.exception.EntityNotFoundException;

public class Material {
	
	private Integer courseMaterialId;
	private String title;
	private String description;
	private Date uploadDate;
	private CourseUsers creator;
	private String materialFilename;
	private Courses course;
	
	public static final int INVALID_COURSE_ID = -1;
	
	public Material() {
		this.title = "";
		this.description = "";
		this.materialFilename = "";
	}
	
	public Material(CourseUsers id){
		this();
		this.creator = id;
	}
	
	public int createMaterial(){
		
		/* Needed?
		this.active = true;
		this.privateDirectory = "/" + this.uid + "/";
		this.creationDate = new Date();
		this.lastLogin = this.creationDate;
		*/
		
		CourseMaterialDao dao = new CourseMaterialDao();
		
		CourseMaterial cm = new CourseMaterial(this.title, this.description, this.uploadDate, this.creator, 
				this.materialFilename,this.course);
		
		dao.create(cm);
		
		int id = cm.getCourseMaterialId();
		
		return id;
	}
	
	public void updateMaterial(){		
		CourseMaterialDao dao = new CourseMaterialDao();
		
		//Note: creatorUid is an int, CourseMaterialDao read() takes a string. Which should I change? for now use Integer.toString() to properly read
		CourseMaterial cm = dao.read(String.valueOf(this.creator));
		
		
		if(cm != null){
			cm.setCourseMaterialId(!Integer.toString(this.courseMaterialId).isEmpty() && this.courseMaterialId != cm.getCourseMaterialId() ? this.courseMaterialId : cm.getCourseMaterialId());
			cm.setTitle(!this.title.isEmpty() && this.title != cm.getTitle() ? this.title : cm.getTitle());
			cm.setDescription(!this.description.isEmpty() && this.description != cm.getDescription() ? this.description : cm.getDescription());
			cm.setCreator(this.creator.getUser().getUid().isEmpty() && this.creator != cm.getCreator() ? this.creator : cm.getCreator());			
			cm.setMaterialFilename(!this.materialFilename.isEmpty() && this.materialFilename != cm.getMaterialFilename() ? this.materialFilename : cm.getMaterialFilename());
			cm.setCourse(this.course.getCourseId() != Material.INVALID_COURSE_ID && this.course != cm.getCourse() ? this.course : cm.getCourse());
			dao.update(cm);
		}
	}
	
	public void readMaterial(){
		CourseMaterialDao dao = new CourseMaterialDao();

		CourseMaterial cm = dao.read(this.creator.getCourseUid());
		
		if(cm != null){
			this.setCourseMaterialId(cm.getCourseMaterialId());
			this.setTitle(cm.getTitle());
			this.setDescription(cm.getDescription());
			this.setMaterialFilename(cm.getMaterialFilename());
			this.setCourse(cm.getCourse());
			this.setUploadDate(cm.getUploadDate());
		}
	}
	
	public List<?> readAllMaterial(){
		CourseMaterialDao dao = new CourseMaterialDao();
		//CourseMaterial cm = dao.read(this.creator.getCourseUid());
		List<?> cmL = dao.readAll();
		
		return cmL;
	}

	public Integer getCourseMaterialId() {
		return courseMaterialId;
	}

	public void setCourseMaterialId(Integer courseMaterialId) {
		this.courseMaterialId = courseMaterialId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public CourseUsers getCreator() {
		return creator;
	}

	public void setCreator(CourseUsers creator) {
		this.creator = creator;
	}

	public String getMaterialFilename() {
		return materialFilename;
	}

	public void setMaterialFilename(String materialFilename) {
		this.materialFilename = materialFilename;
	}

	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}
}
