package com.myboard.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.myboard.business.UserSession;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import com.myboard.business.Announcement;
import com.myboard.business.Course;
import com.myboard.business.UserSession;
import com.myboard.business.User;

@ManagedBean(name="AnnouncementCreateBean")
@RequestScoped
public class AnnouncementCreateBean implements Serializable {
	/**
	 * pretty sure this is some sort of wizardry
	 */
	private static final long serialVersionUID = -8929723117288232465L;

	private String announcementTitle;
	private String description;
	private Date datePosted;
	private String classSelected;
	private int announcementID, creatorID, courseID;
	private UserSession userSession;
	private User user;
	/* the list of courses in string form */
	private List<String> classList;
	
	/* the list of course objects that we're going to actually use */
	private List<Course> courseList;
	
	/* using this for testing purposes */
	private static final int NO_CLASS_SELECTED = -1;
	
	public AnnouncementCreateBean() {
		classList = new ArrayList<String>();
		classSelected = "-1";
		
		userSession = (UserSession)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userSession");
		
		user = userSession.getUser();
		System.out.println("\n\nUser ID is: " + user.getUid());
		
		if(userSession.isLoggedIn()) {
			initializeCourseList();
		} else {
			System.out.println("THE USER IS NOT LOGGED IN SOMEHOW");
			classList.add("not getting classes");
		}
		
		this.announcementTitle = "New Announcement";
		this.description = "No Description";
		datePosted = new Date();
	}
	
	public void createAnnouncement(ActionEvent e) {
		Announcement announcement = new Announcement();
		announcement.setDescription(description);
		announcement.setTitle(announcementTitle);
		
		/* this should be updated to get the course Id once that stuff is working: */
		announcement.setCourseID(Integer.parseInt(classSelected));

		announcement.setDatePosted(new Date());
		creatorID = Integer.parseInt(user.getUid()); /* again, we have user ids as strings and creator ids as ints */
		announcement.setCreatorID(creatorID);
		announcement.setCourseID(Integer.parseInt(classSelected));
		
		announcement.createAnnouncement();
		
		System.out.println("NEW ANNOUNCEMENT CREATED! SUCKA DICK!\n" +
				announcement.toString());
	
	}
	
	public void initializeCourseList() {
		System.out.println("Adding classes...");
		courseList = user.getCourses();
		classList.clear();

		for(int i = 0; i < courseList.size(); i++) {
			classList.add(courseList.get(i).getCourseId());
			System.out.println("New course added to course list: " + classList.get(i) + ", " + courseList.get(i).getCourseId());
		}
		if (courseList.size() == 0) {
			System.out.println("\nUSER HAS NO COURSES \n");
		}
		if(classList.size() == 0) {
			System.out.println("\nUSER HAS NO CLASS NAMES\n");
		}
		System.out.println("Done adding classes!\nNumber of Course objects: " + courseList.size() +
				"\nNumber of class names: " + classList.size());
	}
	
	/* this is required by the jsf page */
	public String submitButton() {
		return "button";
	}
	
	public void classSelectChanged(String arg) {
		classSelected = arg;
		System.out.println("Class selected is now: " + classSelected);
	}
	public void classSelectChanged(ValueChangeEvent e) {
		description = "\nThe current class selected is: " + e.getNewValue().toString();
	}
	public void announcementSelectChanged(ValueChangeEvent e) {
		announcementTitle = e.getNewValue().toString();
	}
	public String getClassSelected() {
		return classSelected;
	}
	public void setClassSelected(String in) {
		classSelected = in;
	}
	public void setSelectChanged(String in) {
		classSelected = in;
	}
	public List<String> getClassList() {
		return classList;
	}
	public void setClassList(List<String> list) {
		classList = list;
	}
	public void setDescription(String in) {
		description = in;
	}
	public String getDescription() {
		return description;
	}
	public void setAnnouncementTitle(String in) {
		announcementTitle = in;
	}
	public String getAnnouncementTitle() {
		return announcementTitle;
	}
	public void setDatePosted(Date in) {
		datePosted = in;
	}
	public Date getDatePosted() {
		return datePosted;
	}
}
