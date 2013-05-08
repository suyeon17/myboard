package com.myboard.bean;

import java.io.Serializable;
import java.util.Date;

import com.myboard.business.Grade;
import com.myboard.business.User;
import com.myboard.dao.CourseUsers;
import com.myboard.dao.AdditionalGradeEntries;

public class UpdateGrade implements Serializable{

private static final long serialVersionUID = -541159628249307804L;
	
	private float ptsEarn;
	private float ptsPoss;
	private String title;
	private String comments;
	private Integer assignmentId;
	private int courseId;
	private String uid;
	
	public UpdateGrade(){
		this.ptsEarn = 0;
		this.ptsPoss = 0;
		this.title = "";
		this.comments = "";
		this.assignmentId = null;
		this.courseId = 0;
		this.uid = "";
	}	
	
	public String createGrade(){
		Grade grade = new Grade();
		grade.setAssignmentId(this.assignmentId);
		grade.setComments(this.comments);
		grade.setCourseId(this.courseId);
		grade.setPointsEarned(this.ptsEarn);
		grade.setPointsPossible(this.ptsPoss);
		grade.setTitle(this.title);
		
		grade.createGrade();
		
		return "OK";
	}
	
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
	
	public void setUID(String uid){
		this.uid = uid;
	}
	
	//----------------------------------------------------------------
	
	public String getUID(){
		return this.uid;
	}
		
}
