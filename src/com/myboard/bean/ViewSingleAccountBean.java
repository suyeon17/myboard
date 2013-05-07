package com.myboard.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.myboard.dao.Users;
import com.myboard.dao.UsersDao;


@ManagedBean
@SessionScoped
public class ViewSingleAccountBean implements Serializable {

	private static final long serialVersionUID = -7865245629062641844L;
	private Users user;
	private String userid;

	public ViewSingleAccountBean()
	{
		
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	/*
	 * This will take the variable from the session and then set the variable in the page.
	 */
	public void setSession()
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		
		this.userid = (String)session.getAttribute("ViewAccountsUserid");
		UsersDao dao = new UsersDao();
		this.user = dao.read(this.userid);
	}
}
