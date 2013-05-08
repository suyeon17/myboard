//Coded by Shane Poloha
//Backing bean to temporarily store data.  Used by grades.xhtml.

package com.myboard.bean;

import java.io.Serializable;
import java.util.Set;

import com.myboard.dao.*;

public class Storage implements Serializable {
	private String temp;
	
	//////////////////////////////////////////////////////////////////
	
	/*Constructor*/
	
	public Storage(){
		temp = "";
	}
	
	//////////////////////////////////////////////////////////////////
	
	/*Public Methods*/
	
	//---------------------------------------------------------------
	
	/*Gets the temp value*/
	
	public String getTemp(){
		return temp;
	}
	
	//---------------------------------------------------------------
	
	/*Sets the temp value*/
	
	public void setTemp(String temp){
		this.temp = temp;
	}
}
