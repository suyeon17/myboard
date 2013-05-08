//Coded by Shane Poloha
//Stores and manipulates list of grades.  Used by grades.xhtml

package com.myboard.bean;

import java.io.Serializable;
import java.util.*;

import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.component.html.ext.HtmlDataTable;

import com.myboard.business.Grade;
import com.myboard.business.UserSession;
import com.myboard.dao.*;

import javax.swing.*;

public class GradeList implements Serializable{

	private List grades = new ArrayList();
    private String id;
       	
	//////////////////////////////////////////////////////////////////
    
    /*Constructors*/
	
	public GradeList(){}
	
	//////////////////////////////////////////////////////////////////
	
	/*Public Methods*/
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	/*Adds grade to the list*/
				
	public void addGrade(Grade g){
		grades.add(g);
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	/*Removes grade from the list*/
	
	public void delGrade(Grade g){
		grades.remove(g);
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	/*If the list contains the passed grade entry, then that entry will be returned from the list*/
	
	public Grade getGrade(Grade g){
		for(int i = 0; i < grades.size(); i++){
			if( ((Grade)grades.get(i)).equals(g)){
				return  (Grade)grades.get(i);
			}
		}
		return null;
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	/*Return the list of grades*/
	
	public List getGrades(){
		return grades;
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	/*This method is needed after the datatable in grades.xhtml is initially rendered.  Every time a function such as sort or
	 * getComment is called, the JSF page automatically re-renders the datatable with the "get" property initGrades.  Thus, this
	 * method will be called at those times.*/
	
	public List getInitGrades(){
		return grades;
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	/*This method is used to initialize the datatable in grades.xhtml with the current user id.  The id is used to get all the 
	 * grade entries from the database for the current user.*/
	
	public List initGrades(String id){		
		if(grades.isEmpty()){
			this.id = id;
			UsersDao udao = new UsersDao();
			Users users = udao.read(id);
			ArrayList<CourseUsers> cu = new ArrayList<CourseUsers>(users.getCourseUsers());
	
			ArrayList<AdditionalGradeEntries> entries;
			Grade tempGrade;
		
		/*Retrieve grades from additional grade entries*/
		
			for(int i = 0; i < cu.size(); i++){
				entries=new ArrayList<AdditionalGradeEntries>(cu.get(i).getAdditionalGradeEntries());
				for(int j = 0; j < entries.size(); j++){
					tempGrade = new Grade();
					tempGrade.setTitle(entries.get(j).getTitle());
					tempGrade.setPointsEarned(entries.get(j).getPointsEarned().floatValue());
					tempGrade.setPointsPossible(entries.get(j).getTotalPoints());
					tempGrade.setComments(entries.get(j).getComments());
					tempGrade.setAssignmentId(entries.get(j).getEntryId());
					tempGrade.setCourseId(cu.get(i).getCourseUid());
					grades.add(tempGrade);
				}
			}
				
		/*Retrieve grades from assignment submissions*/
		
		/*ArrayList<Assignments> assigns;
		ArrayList<AssignmentSubmission> assignSub;
	
		for(int i = 0; i < cu.size(); i++){
			assigns = new ArrayList<Assignments>(cu.get(i).getAssignments());
			assignSub = new ArrayList<AssignmentSubmission>(cu.get(i).getAssignmentSubmissions());
			for(int j = 0; j < assigns.size(); j++){
				tempGrade = new Grade();
				tempGrade.setTitle(assigns.get(j).getTitle());
				tempGrade.setPointsEarned(assignSub.get(j).getPointsEarned().floatValue());
				tempGrade.setComments(assignSub.get(j).getComments());
				tempGrade.setAssignmentId(assignSub.get(j).getAssignmentId());
				tempGrade.setCourseId(cu.get(i).getCourseUid());
				
				for(int k = 0; k < assigns.size(); k++){
					if(assigns.get(k).getAssignmentId().equals(assignSub.get(j).getAssignmentId())){
						tempGrade.setPointsPossible(assigns.get(k).getTotalPoints());
						break;
					}
				}
				grades.add(tempGrade);
			}
		}*/
		}
		return grades;
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	/*Set the list of grades*/
	
	public void setGrades(ArrayList grades){
		this.grades = grades;
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	/*Function included in online example.  Not sure how it is used.*/
		
	public void deleteGrade(ActionEvent ev)
    {
        UIData datatable = findParentHtmlDataTable(ev.getComponent());
        getGrades().remove(datatable.getRowIndex() + datatable.getFirst());
    }
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	/*Sums the total points earned of all the grade entries.*/
	
	public float getTotalPointsEarned(){
		float total = 0;
		for(int i = 0; i < grades.size(); i++){
			total = total + ((Grade)grades.get(i)).getPointsEarned();
		}
		return total;
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	/*Sums the total points possible of all the grade entries*/
		
	public float getTotalPointsPossible(){
		float total = 0;
		for(int i = 0; i < grades.size(); i++){
			total = total + ((Grade)grades.get(i)).getPointsPossible();
		}
		return total;
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	
	/*Sorts list by points possible*/
	    
	public String sortByPointsPossible()
    {   
    	if(needSortAscendingPP()){
    		Comparator comparator = new Comparator(){    		
    			public int compare(Object o1, Object o2){
    				return new Float(((Grade)o2).getPointsPossible()).compareTo(new Float(((Grade)o1).getPointsPossible()));              
    			}
    		};
    		Collections.sort(grades, comparator);
    	}
    	else{
    		Comparator comparator = new Comparator(){    		
    			public int compare(Object o1, Object o2){
    				return new Float(((Grade)o1).getPointsPossible()).compareTo(new Float(((Grade)o2).getPointsPossible()));              
    			}
    		};
    		Collections.sort(grades, comparator);
      	}
    	return null;
    }
    
  //-------------------------------------------------------------------------------------------------------------------------
    
    /*Sorts list alphabetically by title*/
    
   public String sortByTitle()
    {   
    	if(needSortAscendingT()){
    		Comparator comparator = new Comparator(){    		
    			public int compare(Object o1, Object o2){
    				return ((Grade)o1).getTitle().compareTo(((Grade)o2).getTitle());              
    			}
    		};
    		Collections.sort(grades, comparator);
    	}
    	else{
    		Comparator comparator = new Comparator(){    		
    			public int compare(Object o1, Object o2){
    				return ((Grade)o2).getTitle().compareTo(((Grade)o1).getTitle());               
    			}
    		};
    		Collections.sort(grades, comparator); 		
    	}
    	return null;
    }
    
  //-------------------------------------------------------------------------------------------------------------------------
    
    /*Sorts list by points earned*/
    
    public String sortByPointsEarned()
    {   
    	if(needSortAscendingPE()){
    		Comparator comparator = new Comparator(){    		
    			public int compare(Object o1, Object o2)            {
    				return new Float(((Grade)o2).getPointsEarned()).compareTo(new Float(((Grade)o1).getPointsEarned()));              
    			}
    		};
    		Collections.sort(grades, comparator);
    	}
    	else{
    		Comparator comparator = new Comparator(){    		
    			public int compare(Object o1, Object o2)            {
    				return new Float(((Grade)o1).getPointsEarned()).compareTo(new Float(((Grade)o2).getPointsEarned()));              
    			}
    		};
    		Collections.sort(grades, comparator);		
    	}
    	return null;
    }
    
  //-------------------------------------------------------------------------------------------------------------------------
    
    /*Sort list by Assignment Id*/
    
    public String sortByAssignmentId()
    {   
    	if(needSortAscendingAI()){
    		Comparator comparator = new Comparator(){    		
    			public int compare(Object o1, Object o2){
    				return ((Grade)o2).getAssignmentId().compareTo(((Grade)o1).getAssignmentId());              
    			}
    		};
    		Collections.sort(grades, comparator);
    	}
    	else{
    		Comparator comparator = new Comparator(){    		
    			public int compare(Object o1, Object o2){
    				return ((Grade)o1).getAssignmentId().compareTo(((Grade)o2).getAssignmentId());               
    			}
    		};
    		Collections.sort(grades, comparator);
    	}
    	return null;
    }
    
  //-------------------------------------------------------------------------------------------------------------------------
    
    /*Sort list by course id*/
    
    public String sortByCourseId()
    {   
    	if(needSortAscendingCI()){
    		Comparator comparator = new Comparator(){    		
    			public int compare(Object o1, Object o2)            {
    				return new Integer(((Grade)o2).getCourseId()).compareTo(new Integer(((Grade)o1).getCourseId()));              
    			}
    		};
    		Collections.sort(grades, comparator);
    	}
    	else{
    		Comparator comparator = new Comparator(){    		
    			public int compare(Object o1, Object o2)            {
    				return new Integer(((Grade)o1).getCourseId()).compareTo(new Integer(((Grade)o2).getCourseId()));              
    			}
    		};
    		Collections.sort(grades, comparator); 		
    	}
    	return null;
    }   
    
    
	
	//////////////////////////////////////////////////////////////////
	
	/*Private Methods*/
    
    private boolean needSortAscendingT(){
    	for(int i = 1; i < grades.size(); i++){
    		if(((Grade)grades.get(i-1)).getTitle().compareTo(((Grade)grades.get(i)).getTitle())>0){
    			return true;
    		}    		
    	}
    	return false;
    }
    
    private boolean needSortAscendingPP(){
    	for(int i = 1; i < grades.size(); i++){
    		if( ((Grade)grades.get(i-1)).getPointsPossible()<((Grade)grades.get(i)).getPointsPossible() ){
    			return true;
    		}    		
    	}
    	return false;
    }
    
    private boolean needSortAscendingPE(){
    	for(int i = 1; i < grades.size(); i++){
    		if( ((Grade)grades.get(i-1)).getPointsEarned()<((Grade)grades.get(i)).getPointsEarned() ){
    			return true;
    		}    		
    	}
    	return false;
    }
    
    private boolean needSortAscendingAI(){
    	for(int i = 1; i < grades.size(); i++){
    		if( ((Grade)grades.get(i-1)).getAssignmentId().intValue()<((Grade)grades.get(i)).getAssignmentId().intValue() ){
    			return true;
    		}    		
    	}
    	return false;
    }
    
    private boolean needSortAscendingCI(){
    	for(int i = 1; i < grades.size(); i++){
    		if( ((Grade)grades.get(i-1)).getCourseId()<((Grade)grades.get(i)).getCourseId() ){
    			return true;
    		}    		
    	}
    	return false;
    }  
    
   /*Not sure of use.  Included in online example*/
	
	private HtmlDataTable findParentHtmlDataTable(UIComponent component)
    {
        if (component == null)
        {
            return null;
        }
        if (component instanceof HtmlDataTable)
        {
            return (HtmlDataTable) component;
        }
        return findParentHtmlDataTable(component.getParent());
    }
	
}
