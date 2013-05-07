package com.myboard.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import com.myboard.business.Announcement;
import com.myboard.business.User;
import com.myboard.business.Course;
import com.myboard.business.UserSession;
import com.myboard.dao.Announcements; /* I'd prefer not to have to import a Dao here, but not sure how else to do this*/

/*
 * TODO:
 * 		Right now, selecting a course causes an error
 * 		This is due to announcement.read(), which has an issue
 * 		Good luck!
 * 
 */

@ManagedBean(name="AnnouncementBean")
@RequestScoped
public class AnnouncementBean implements Serializable {
	/* wat is this */
	private static final long serialVersionUID = -7390403795787831843L;
	
	private String announcementTitle;
	private String description;
	private Date datePosted;
	private String classSelected;
	private int announcementID, creatorID, courseID; /* these aren't used right now, may be useful info to display later*/
	private String announcementSelected;
	private User user;

	/*
	 * These simply hold the names of classes and announcements
	 * A good way to improve this page would be to make these not needed
	 */
	private List<String> classList;
	private List<String> announcementList;
	
	/*
	 * These are the actual Course and Announcement objects
	 * The announcement list is updated based on the course selected
	 */
	private List<Announcement> listOfAnnouncements;
	private List<Course> courseList;
	
	public AnnouncementBean() {
		this.announcementTitle = "New Announcement";
		this.description = "No Description";
		datePosted = new Date();
		
		UserSession userSession = (UserSession)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userSession");
		user = userSession.getUser();
		courseList = user.getCourses();
		
		/*
		 * Iterate through the courses and add their IDs to the classList
		 * 
		 * We're grabbing course ID's instead of course names because the CourseInfo table/objects are messed up
		 * 
		 * TODO:
		 * Once CourseInfo works, be sure to put the course names into the list
		 */
		classList = new ArrayList<String>();
		for(int i = 0; i < courseList.size(); i++) {
			classList.add(courseList.get(i).getCourseId());
		}
		
		classSelected = "none";
		
	}
	
	public String getAnnouncementTitle() {
		return announcementTitle;
	}
	
	public String getAnnouncementDescription() {	
		return description;
	}
	
	public List<String> getClassList() {
		return classList;
	}
	
	public String getClassSelected() {
		/* returns the name of the currently selected class 
		 * This seemingly useless comment is here so that no one thinks this at all relates to an actual Course object
		 * or that this method might actually be useful (it's not)
		 * */
		return classSelected;
	}
	
	public String getAnnouncementSelected() {
		return announcementSelected;
	}
	
	public List<String> getAnnouncementList() {
		return announcementList;
	}
	
	public String getAnnouncementDate() {
		return datePosted.toString();
	}
	
	public void setClassSelected(String arg) {
		classSelected = arg;
	}
	
	public void setAnnouncementSelected(String arg) {
		announcementSelected = arg;
	}
	
	public void classSelectChanged(ValueChangeEvent e) {	
		
		Course courseSelected = new Course();
		courseSelected.setCourseId(e.getNewValue().toString());
		courseSelected.readCourse();
		/* Course has a set of AnnouncementS, not announcement, so we have to use the Dao object Announcements */
		Announcement temp;
		ArrayList<Announcements> listOfAnnouncementsTEMP = new ArrayList<Announcements>(courseSelected.getAnnouncements());
		for (int i = 0; i < listOfAnnouncementsTEMP.size(); i++) {
			temp = new Announcement();
			temp.setAnnouncementID(listOfAnnouncementsTEMP.get(i).getAnnouncementId());
			temp.readAnnouncement();
			System.out.println("announcement print:\n" + temp.toString());
			listOfAnnouncements.add(temp);
			announcementList.add(listOfAnnouncements.get(i).getTitle());
		}
	}
	
	public void loadAnnouncements() {
		/* TODO: this
		 * this updates the announcements list based on the class currently selected
		 * this can replace the method classSelectChanged
		 * see the example called "Populate a Child Menu" at http://stackoverflow.com/tags/selectonemenu/info
		 */
	}
	
	public void announcementSelectChanged(ValueChangeEvent e) {
		/*
		 * TODO: make this update the values with the desired announcement output
		 */
		announcementTitle = e.getNewValue().toString();
		/*
		 * TODO:
		 * iterate through the list of announcements and select the one with the same title
		 * This is obviously the wrong way to do things
		 * I'm pretty sure we need some sort of hash map that allows us to link the string output of Titles
		 * 			with the objects they refer to, so that we can have the same titles for multiple objects
		 * 
		 */
		for (int i = 0; i < listOfAnnouncements.size(); i++) {
			if (announcementTitle.equals(listOfAnnouncements.get(i).getTitle())) {
				
			}
		}
		
	}
	/* dunno what this does, but it's probably required */
	public boolean renderOutput() {
		return true;
	}

}