package com.myboard.business;

import java.io.Serializable;
import java.util.Date;

public class UserSession implements Serializable {

	private static final long serialVersionUID = -7050402384121642617L;

	private String cookieId;
	private User user;
	private Date creationDate;
	private Date lastVisit;

	public UserSession() {}

	public UserSession(String cookieId) {
		this.cookieId = cookieId;
		this.creationDate = new Date();
		this.lastVisit = new Date();
	}

	public String getCookieId() {
		return this.cookieId;
	}

	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastVisit() {
		return this.lastVisit;
	}

	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}

	public boolean isLoggedIn() {
		return this.user != null;
	}
}
