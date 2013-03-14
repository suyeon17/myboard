package com.myboard.dao;

// Generated Mar 13, 2013 12:50:36 AM by Hibernate Tools 4.0.0

/**
 * AdditionalGradeEntries generated by hbm2java
 */
public class AdditionalGradeEntries implements java.io.Serializable {

	private Integer entryId;
	private int courseUid;
	private Integer pointsEarned;
	private int totalPoints;
	private String title;
	private String comments;
	private int createdBy;

	public AdditionalGradeEntries() {
	}

	public AdditionalGradeEntries(int courseUid, int totalPoints, String title,
			int createdBy) {
		this.courseUid = courseUid;
		this.totalPoints = totalPoints;
		this.title = title;
		this.createdBy = createdBy;
	}

	public AdditionalGradeEntries(int courseUid, Integer pointsEarned,
			int totalPoints, String title, String comments, int createdBy) {
		this.courseUid = courseUid;
		this.pointsEarned = pointsEarned;
		this.totalPoints = totalPoints;
		this.title = title;
		this.comments = comments;
		this.createdBy = createdBy;
	}

	public Integer getEntryId() {
		return this.entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public int getCourseUid() {
		return this.courseUid;
	}

	public void setCourseUid(int courseUid) {
		this.courseUid = courseUid;
	}

	public Integer getPointsEarned() {
		return this.pointsEarned;
	}

	public void setPointsEarned(Integer pointsEarned) {
		this.pointsEarned = pointsEarned;
	}

	public int getTotalPoints() {
		return this.totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

}
