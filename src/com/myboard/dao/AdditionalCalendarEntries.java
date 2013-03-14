package com.myboard.dao;

// Generated Mar 13, 2013 12:50:36 AM by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * AdditionalCalendarEntries generated by hbm2java
 */
public class AdditionalCalendarEntries implements java.io.Serializable {

	private Integer entryId;
	private String uid;
	private String title;
	private String description;
	private Date date;

	public AdditionalCalendarEntries() {
	}

	public AdditionalCalendarEntries(String uid, String title,
			String description, Date date) {
		this.uid = uid;
		this.title = title;
		this.description = description;
		this.date = date;
	}

	public Integer getEntryId() {
		return this.entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
