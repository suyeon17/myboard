/*
 * MyBoard- Student Materials
 * Database access to course material such as Lecture notes. 
 * 
 * Database access: good
 * Download: under construction
 */
package com.myboard.bean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;


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
	RandomAccessFile downFile;
	private String fileName;
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
		   
		   // Set all database information in a list
		   List<?> l = mat.readAllMaterial();
		 

		   // Populate arraylist with row information, one entry in an arraylist is equal to one database row
		   for(int i=0; i<l.size(); i++){
			   String row[] = new String[7];
			   CourseMaterial c = (CourseMaterial) l.get(i);
		   
			   // Get only data relevant to current User by filtering by course uid
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
	// Go to download  page
	public String downloadPage() {
		return ("downloadFile");
	}

	// Download specified file 
	public void download() throws IOException{
		int DEFAULT_BUFFER_SIZE = 10240;  
		
		/***********************************************************************/

		// Path to file download, 
		//String filePath = System.getProperty("user.home") +"\\Desktop\\Test.txt";  
		String filePath = System.getProperty("user.home") +"\\Desktop\\"+getFileName();  
		/***********************************************************************/

		
		 FacesContext context = FacesContext.getCurrentInstance();  
		             HttpServletResponse response = (HttpServletResponse) context  
		                       .getExternalContext().getResponse();  
		             File file = new File(filePath);  
		             
         if (!file.exists()) {  
              response.sendError(HttpServletResponse.SC_NOT_FOUND);  
              return;  
         }  
         
        response.reset();  
         response.setBufferSize(DEFAULT_BUFFER_SIZE);  
        response.setContentType("application/xml");  
         response.setHeader("Content-Length", String.valueOf(file.length()));  
         
         // Save as prompt
         response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");  
         
         BufferedInputStream input = null;  
         BufferedOutputStream output = null;  
         try {  
              input = new BufferedInputStream(new FileInputStream(file),  
                        DEFAULT_BUFFER_SIZE);  
              output = new BufferedOutputStream(response.getOutputStream(),  
                        DEFAULT_BUFFER_SIZE);  
              byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];  
              int length;  
              
              // Write to buffered output stream
              while ((length = input.read(buffer)) > 0) {  
                   output.write(buffer, 0, length);  
              }  
         } finally {  
              input.close();  
              output.close();  
         }  
         context.responseComplete();  
	}
    
    // Write uploaded file into database
    public void createMaterial() {
	    Material material = new Material();
	  
	    CourseUsers cu = new CourseUsers();
	    cu.setCourseUid(creator);
	    
	    Courses c = new Courses();
	    c.setCourseId(course);
	    
	    // Set Material attributes
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

	public RandomAccessFile getDownFile() {
		return downFile;
	}

	public void setDownFile(RandomAccessFile downFile) {
		this.downFile = downFile;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
