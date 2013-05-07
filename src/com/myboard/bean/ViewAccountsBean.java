package com.myboard.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.*;
import java.math.BigDecimal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.myboard.dao.Users;
import com.myboard.business.ViewAccountsDirector;
import com.myboard.dao.AccountPermissions;

@ManagedBean
@SessionScoped
public class ViewAccountsBean implements Serializable {

	private static final long serialVersionUID = -4592960737131782816L;
	
	ArrayList<Users> users;
	private String userid;


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public ViewAccountsBean() {
		
	}

	public ArrayList<Users> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<Users> users) {
		this.users = users;
	}
	
	//Grabs only the students from the users
	public void viewStudentAccounts()
	{
		ViewAccountsDirector vad = new ViewAccountsDirector();
		this.users = vad.getStudents();
	}
	
	//Grabs only the teachers from the users
	public void viewTeacherAccounts()
	{
		ViewAccountsDirector vad = new ViewAccountsDirector();
		this.users = vad.getTeachers();
		
	}
	
	//Grabs only the admins from the users
	public void viewAdminAccounts()
	{
		ViewAccountsDirector vad = new ViewAccountsDirector();
		this.users = vad.getAdmins();
	}
	
	/*
	 * This sets the userid in teh session and then redirects to view a single account
	 * 
	 */
	public void viewAccount(String uid)
	{
		this.userid = uid;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		
		session.setAttribute("ViewAccountsUserid", this.userid);
		
		try {
			facesContext.getExternalContext().redirect("userAccount.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
