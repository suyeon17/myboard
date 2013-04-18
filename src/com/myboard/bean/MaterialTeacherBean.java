package com.myboard.bean;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.JFileChooser;

import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import com.myboard.business.Material;
import com.myboard.business.User;
import com.myboard.business.UserSession;
import com.myboard.dao.CourseMaterial;
import com.myboard.dao.CourseRoles;
import com.myboard.dao.CourseUsers;
import com.myboard.dao.Courses;
import com.myboard.dao.Users;
import com.myboard.filter.UserSessionFilter;

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
	Courses c;
	  ArrayList<String[]> dataList = new ArrayList<String[]>();

	public ArrayList<String[]> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<String[]> dataList) {
		this.dataList = dataList;
	}

	CourseUsers cu;
	
	private UploadedFile upFile;
	

	public MaterialTeacherBean() {
		c = new Courses();
		cu = new CourseUsers();
		
		c.setCourseId(1);
		cu.setCourseUid(4);
		
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

	// Go to upload page
	public String uploadPage() {
		return ("uploadFile");
	}

	// Upload a file and write to database
    public String upload() throws IOException {
        String fileName = FilenameUtils.getName(upFile.getName());
        String contentType = upFile.getContentType();
        
        // Uploaded file byte 
        byte[] bytes = upFile.getBytes();
                
        
        /*******************************************************************************************/
        // Change to specific file path
        String filePath = "C:\\Users\\J\\Desktop\\Material\\" + getCourse()+"\\"+fileName;
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

	public UploadedFile getUpFile() {
		return upFile;
	}

	public void setUpFile(UploadedFile upFile) {
		this.upFile = upFile;
	}    
}
