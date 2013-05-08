//Coded by Shane Poloha
//This class stores information about student grades.  Used by grades.xhtml.

package com.myboard.business;

import com.myboard.dao.*;

import java.io.Serializable;
import java.util.Date;
//author:  Shane Poloha

import javax.faces.event.ActionEvent;

public class Grade implements Serializable{
	
	private static final long serialVersionUID = -9056171476361263222L;
	
	private float ptsEarn;
	private float ptsPoss;
	private String title;
	private String comments;
	private Integer assignmentId;
	private int courseId;
	private Integer uid = new Integer(11111111);
			
	//////////////////////////////////////////////////////////////////
	
	/*Constructors*/	
	
	/*Empty Constructor*/
	
	public Grade(){
		
		this.assignmentId = null;
		this.courseId = 0;
		this.ptsEarn = 0;
		this.ptsPoss = 0;
		this.title = null;
		this.comments= null;
	}
	
	//----------------------------------------------------------------
		
	//////////////////////////////////////////////////////////////////
	
	/*Public Methods*/
		
	/*Sets the assignment id for the current Grade Instance */
	
	public void setAssignmentId(Integer id){
		this.assignmentId = new Integer(id.intValue());
	}
	
	//----------------------------------------------------------------
	
	/*Sets the course id for the current Grade Instance */
	
	public void setCourseId(int id){
		this.courseId = id;
	}
	//----------------------------------------------------------------
	
	/*Sets the points possible for the current Grade Instance */
	
	public void setPointsPossible(float ptsPoss){
		this.ptsPoss = ptsPoss;
	}
	
	//----------------------------------------------------------------
	
	/*Sets the points earned for the current Grade Instance */
		
	public void setPointsEarned(float ptsEarn){
		this.ptsEarn = ptsEarn;
	}
	
	//----------------------------------------------------------------
	
	/*Sets the title for the current Grade Instance */
			
	public void setTitle(String title){
		this.title = title;
	}
	
	//----------------------------------------------------------------
	
	/*Sets the comments for the current Grade Instance */
	
	public void setComments(String comments){
		this.comments = comments;
	}
		
	//----------------------------------------------------------------
	
	/*Gets the assignment id for the current Grade Instance */
	
	public Integer getAssignmentId(){
		return this.assignmentId;
	}
	
	//----------------------------------------------------------------
	
		/*Gets the course id for the current Grade Instance */
		
		public int getCourseId(){
			return this.courseId;
		}
		
	
	//----------------------------------------------------------------
	
	/*Gets the points earned for the current Grade Instance */
		
	public float getPointsEarned(){
		return this.ptsEarn;
	}

	//----------------------------------------------------------------
	
	/*Gets the points possible for the current Grade Instance */
	
	public float getPointsPossible(){
		return this.ptsPoss;
	}
	
	//----------------------------------------------------------------
	
	/*Gets the title for the current Grade Instance */
	
	public String getTitle(){
		return this.title;
	}
	
	//----------------------------------------------------------------
	
	/*Gets the comments for the current Grade Instance */
	
	public String getComments(){
		return this.comments;
	}
	
	//----------------------------------------------------------------
	
	/*Determines if two grade objects are equal*/
	
	public boolean equals(Grade g){
		if(!this.assignmentId.equals(g.getAssignmentId())){
			return false;
		} 
		else if(!this.comments.equals(g.getComments())){
			return false;
		}
		else if(this.courseId!=g.getCourseId()){
			return false;
		}
		else if(this.ptsEarn!=g.getPointsEarned()){
			return false;
		}
		else if(this.ptsPoss!=g.getPointsPossible()){
			return false;
		}
		else if(!this.title.equals(g.getTitle())){
			return false;
		}
		else{
			return true;
		}
	}
	
	//----------------------------------------------------------------
	
	/*Set all the attributes for the current Grade Instance */
	
	public void setGrade(float ptsEarn, float ptsPoss, String title, String comments, Integer assignmentId, int courseId){
		
		this.ptsEarn = ptsEarn;
		this.ptsPoss = ptsPoss;
		this.title = title;
		this.comments= comments;
		this.assignmentId = assignmentId;
		this.courseId = courseId;
	}	

	//----------------------------------------------------------------
	
	/*Get all the attributes for the current Grade Instance */
	
	public Object[] getGrade(){
		return new Object[] {new Float(this.ptsEarn), new Float(this.ptsPoss), this.title, this.comments};
	}	
	
	//----------------------------------------------------------------
	
	/*Get String for the current Grade Instance */
	
	public String toString(){
		return title + " | " + ptsEarn + " | " + ptsPoss + " | " + comments + " | " + assignmentId + " | " + courseId;
	}
	
	//----------------------------------------------------------------
	
	public Grade clone(){
		Grade newGrade = new Grade();
		newGrade.setAssignmentId(assignmentId);
		newGrade.setComments(comments);
		newGrade.setCourseId(courseId);
		newGrade.setPointsEarned(ptsEarn);
		newGrade.setPointsPossible(ptsPoss);
		newGrade.setTitle(title);
		return newGrade;
	}
	
	//----------------------------------------------------------------
	
	public void setUID(Integer uid){
		this.uid = uid;
	}
	
	//----------------------------------------------------------------
	
	public Integer getUID(){
		return this.uid;
	}
	
	//----------------------------------------------------------------
		
	public void createGrade(){
				
		AdditionalGradeEntriesDao dao = new AdditionalGradeEntriesDao();
		
		com.myboard.dao.CourseUsers cu = getCourseUserById();
		if(cu == null) return;
		
				
		AdditionalGradeEntries ages = new AdditionalGradeEntries(cu, new Integer((int)this.ptsEarn), (int)this.ptsPoss, this.title, this.comments, cu);
	}
	
	//----------------------------------------------------------------
	
	private com.myboard.dao.CourseUsers getCourseUserById(){
		CourseUsersDao dao = new CourseUsersDao();
		return dao.read(this.uid+"");
	}
	
}
