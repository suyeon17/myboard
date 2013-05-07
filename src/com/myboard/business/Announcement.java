package com.myboard.business;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;

import com.myboard.dao.Announcements;
import com.myboard.dao.AnnouncementsDao;
import com.myboard.dao.CourseUsers;
import com.myboard.dao.Users;
import com.myboard.dao.Courses;
import com.myboard.dao.CourseUsers;

/*
 * AnnouncementBean has an arraylist of announcement objects
 * CreateAnnouncementBean creates an announcement, then calls createAnnouncement
 */
public class Announcement implements Serializable {

	private String announcementTitle;
	private String description;
	private Date datePosted;
	private int announcementID, creatorID, courseID;
	private int userID;
	private List<Courses> userCourses;
	private Set<CourseUsers> courseUsers; /*yeah good luck not getting this mixed up with userCourses */
	private User user;
	
	/* use this to denote values that we can't set to null */
	private static final int INVALID_VALUE = -1;
	
	public Announcement() {
		this.announcementTitle = "no title";
		this.description = "nothing here";
		datePosted = new Date();
		courseID = INVALID_VALUE;
		userID = INVALID_VALUE;
		announcementID = INVALID_VALUE;
	}
	
	public Announcement(int announcementID) {
		this.announcementID = announcementID;
		announcementTitle = "";
		description = "";
		datePosted = new Date();
		courseID = INVALID_VALUE;
		userID = INVALID_VALUE;
	}
	
	public Announcement(String title, String description, int creatorID, int courseID) {
		announcementTitle = title;
		this.description = description;
		this.creatorID = creatorID;
		this.courseID = courseID;
		datePosted = new Date();
		announcementID = INVALID_VALUE;
	}
	
	public void createAnnouncement() {
		/* get the current user's ID*/
		UserSession userSession = (UserSession)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userSession");
		user = userSession.getUser();
		userID = Integer.parseInt(user.getUid());
		/* note that we don't actually use userID anywhere
		 * I'm not sure how or why this is, but I'm pretty sure it's a problem
		 * 		Since we need to attach the creatorID to the announcement
		 * also note that we do yet another string->to->int conversion
		 * 
		 * wat
		 */
		
		/* set the date created */
		datePosted = new Date();
		
		/* make sure we have a Course */
		if(courseID == INVALID_VALUE) {
			System.out.println("INVALID COURSE FOR ANNOUNCEMENT");
			return;
		}

		/* get the CourseUsers for this user */
		CourseUsers CUsers = new CourseUsers(); 
		CUsers.setCourseUid(courseID);
		
		/* get the Courses object for the announcement */
		Courses courses = new Courses();
		courses.setCourseId(courseID);
		
		/* call the DAO announcements class and create an announcement */
		Announcements announcement = new Announcements();
		announcement.setDescription(description);
		announcement.setTitle(announcementTitle);
		announcement.setDatePosted(datePosted);
		announcement.setCreator(CUsers);
		announcement.setCourse(courses);
		
		/* call the DAO and write the actual announcement */
		AnnouncementsDao AnnDao = new AnnouncementsDao();
		AnnDao.create(announcement);
		
		/* try to get the ID from the object - this value is only found in the database */
//		announcement = AnnDao.read(announcement.getAnnouncementId().toString()); /* INT->TO->STRING CONVERSION*/
//		announcementID = announcement.getAnnouncementId(); /* dunno if this part works */
	}
	
	public void readAnnouncement() {
		/*
		 * this should read an announcement from the Announcements Dao
		 * it stores these values in the CALLING ANNOUNCEMENT OBJECT
		 * DOES NOT RETURN A NEW OBJECT
		 */
		
		if(announcementID == INVALID_VALUE) {
			System.out.println("NO VALID ANNOUNCEMENT ID");
			return;
		}
		
		Announcements ann = new Announcements();
		AnnouncementsDao annDao = new AnnouncementsDao();
		/*
		 * TODO:
		 * This will always cause a crash
		 * because this creatorID is a string
		 * and the code is set up to handle integers
		 * No known fix without significant changes?
		 */
		ann = annDao.read(Integer.toString(announcementID));
		this.announcementTitle = ann.getTitle();
		this.courseID = ann.getCourse().getCourseId();
		this.creatorID = Integer.parseInt(ann.getCreator().getUser().getUid()); /* ANOTHER STRING->TO->INT */
		this.datePosted = ann.getDatePosted();
		this.description = ann.getDescription();		
	}
	
	/* getters and setters */
	public void setTitle(String arg) {
		announcementTitle = arg;
	}
	public void setDescription(String arg) {
		description = arg;
	}
	public void setDatePosted(Date arg) {
		datePosted = arg;
	}
	
	public void setCreatorID(int arg) {
		creatorID = arg;
	} 
	
	public void setCourseID(int arg) {
		courseID = arg;
	}
	public void setAnnouncementID(int arg) {
		announcementID = arg;
	}
	public int getAnnouncementID() {
		return announcementID;
	}
	
	public int getCreatorID() {
		return creatorID;
	}
	
	public int getCourseID() {
		return courseID;
	}
	public Date getDatePosted() {
		return datePosted;
	}
	public String getTitle() {
		return announcementTitle;
	}
	public String getDescription() {
		return description;
	}
	public int getUserID() {
		return userID;
	}
	public String toString() {
		String string = "";
		string += "Title: " + announcementTitle;
		string += "\nDate posted: " + datePosted.toString();
		string += "\nCreator: " + creatorID;
		string += "\nClass: " + courseID;
		string += "\nContent: " + description;
		return string;
	}
	
}
