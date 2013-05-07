/*
 * MyBoard- Teacher Materials
 * Database access to course material such as Lecture Notes and File Uploading 
 * 
 * Database access: good
 * File uploading: good
 */

package com.myboard.bean;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import com.myboard.business.Material;
import com.myboard.business.UserSession;
import com.myboard.dao.CourseMaterial;
import com.myboard.dao.CourseUsers;
import com.myboard.dao.Courses;

// MaterialTeacherBean interacts with courseMaterialTeacher.xhtml allows teachers to view/upload materials
@ManagedBean
public class MaterialTeacherBean implements Serializable {

	private static final long serialVersionUID = -4403631412398681874L;
	
	private Integer courseMaterialId;
	private String title;
	private String description;
	private Date uploadDate;
	private int creator;
	private String materialFilename;
	private int course;
	UserSession userSession;
	Courses courses;
	 ArrayList<String[]> dataList = new ArrayList<String[]>();
	 
	CourseUsers courseUsers;
	
	private UploadedFile upFile;
		

	public MaterialTeacherBean() {
		
		// Testing...user currently logged in user later
		courses = new Courses();
		courseUsers = new CourseUsers();
		
		courses.setCourseId(1);
		courseUsers.setCourseUid(1);

		setCourse(courses);
		setCreator(courseUsers);		
	}
	
	// Read database for user's course materials
	public void getList() {
		  
		   Material mat = new Material(courseUsers);
		   List<?> l = mat.readAllMaterial();
		 

		   for(int i=0; i<l.size(); i++){
			   String rowData[] = new String[7];
			   CourseMaterial courseMaterial = (CourseMaterial) l.get(i);
		   
			   // Get only data relevant to current User 
			   if(courseMaterial.getCreator().getCourseUid().equals(getCreator())){
				   
				   // Fetch row information
				   rowData[0] = courseMaterial.getCourseMaterialId().toString();
				   rowData[1] = courseMaterial.getTitle();
				   rowData[2] = courseMaterial.getDescription();
				   rowData[3] = courseMaterial.getUploadDate().toString();
				   rowData[4] = courseMaterial.getCreator().getCourseUid().toString();
				   rowData[5] = courseMaterial.getMaterialFilename();
				   rowData[6] = courseMaterial.getCourse().getCourseId().toString();
		
				   // Populate list with database row information
				   dataList.add(rowData);
			   }
		   }
		}

	// Go to upload page
	public String uploadPage() {
		return ("uploadFile");
	}

	// Upload a file to the server and write to database
    public String upload() throws IOException {
        String fileName = FilenameUtils.getName(upFile.getName());
        String contentType = upFile.getContentType();
        
        // Uploaded file byte 
        byte[] bytes = upFile.getBytes();
                
        
        /*******************************************************************************************/
        // Change to specific file path
        String filePath = System.getProperty("user.home")+"\\Desktop\\Material\\" + getCourse()+"\\"+fileName;
        /*******************************************************************************************/

        setMaterialFilename(fileName);
        
        Date date = new Date();
        setUploadDate(date);
        
        // Open a new file for writing
		RandomAccessFile file = new RandomAccessFile(filePath, "rw");
	    
		// Write bytes to new file for server
	    file.write(bytes);
	    
	    file.close();

	    // Write uploaded file info to database
	    createMaterial();
       
	    // Send confirmation message to user
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(String.format("File '%s' of type '%s' successfully uploaded!", fileName, contentType)));
        
        return "fileUploaded";
    }
    
    // Write uploaded file into database, uses business class Material as intermediary between bean and database 
    public void createMaterial() {
    	// Get instance of business class
	    Material material = new Material();
	  
	    CourseUsers cu = new CourseUsers();
	    cu.setCourseUid(creator);
	    
	    Courses c = new Courses();
	    c.setCourseId(course);
	    
	    // Get data from business class
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

	public UploadedFile getUpFile() {
		return upFile;
	}

	public void setUpFile(UploadedFile upFile) {
		this.upFile = upFile;
	}    
	
	public ArrayList<String[]> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<String[]> dataList) {
		this.dataList = dataList;
	}
}
