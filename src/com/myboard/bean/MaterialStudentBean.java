package com.myboard.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;

import com.myboard.business.Material;
import com.myboard.business.UserSession;
import com.myboard.dao.CourseMaterial;
import com.myboard.dao.CourseUsers;
import com.myboard.dao.Courses;


@ManagedBean
public class MaterialStudentBean  implements Serializable {

	private static final long serialVersionUID = 2419837056792129422L;
	
	private Integer courseMaterialId;
	private String title;
	private String description;
	private Date uploadDate;
	private int creator;
	private String materialFilename;
	private int course;
	UserSession userSession;
	Courses c;
	ArrayList<String[]> dataList = new ArrayList<String[]>();
	  
	CourseUsers cu;
	

	public MaterialStudentBean() {
		c = new Courses();
		cu = new CourseUsers();
		
		c.setCourseId(1);
		cu.setCourseUid(1);

		setCourse(c);
		setCreator(cu);		
	}
	
	// Read database for user's course materials
	public void getList() {
		  
		   Material mat = new Material(cu);
		   List<?> l = mat.readAllMaterial();
		 

		   for(int i=0; i<l.size(); i++){
			   String row[] = new String[7];
			   CourseMaterial c = (CourseMaterial) l.get(i);
		   
			   // Get only data relevant to current User 
			   if(c.getCreator().getCourseUid().equals(getCreator())){
				   row[0] = c.getCourseMaterialId().toString();
				   row[1] = c.getTitle();
				   row[2] = c.getDescription();
				   row[3] = c.getUploadDate().toString();
				   row[4] = c.getCreator().getCourseUid().toString();
				   row[5] = c.getMaterialFilename();
				   row[6] = c.getCourse().getCourseId().toString();
		
				   dataList.add(row);
			   }
		   }
	}
    
    // Write uploaded file into database
    public void createMaterial() {
	    Material material = new Material();
	  
	    CourseUsers cu = new CourseUsers();
	    cu.setCourseUid(creator);
	    
	    Courses c = new Courses();
	    c.setCourseId(course);
	    
	    material.setCourse(c);
		material.setCourseMaterialId(this.courseMaterialId);
		material.setTitle(this.title);
		material.setDescription(this.description);
		material.setUploadDate(this.uploadDate);
		material.setCreator(cu);
		material.setMaterialFilename(this.materialFilename);
		
    	// Write to database
		material.createMaterial();  
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

	public int getCreator() {
		return creator;
	}

	public void setCreator(CourseUsers creator) {
		this.creator = creator.getCourseUid();
	}

	public String getMaterialFilename() {
		return materialFilename;
	}

	public void setMaterialFilename(String materialFilename) {
		this.materialFilename = materialFilename;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course.getCourseId();
	}  
	
	public ArrayList<String[]> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<String[]> dataList) {
		this.dataList = dataList;
	}
}
