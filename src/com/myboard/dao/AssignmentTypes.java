package com.myboard.dao;

// Generated Mar 13, 2013 12:50:36 AM by Hibernate Tools 4.0.0

/**
 * AssignmentTypes generated by hbm2java
 */
public class AssignmentTypes implements java.io.Serializable {

	private int assignmentTypeId;
	private String assignmentTypeName;

	public AssignmentTypes() {
	}

	public AssignmentTypes(int assignmentTypeId) {
		this.assignmentTypeId = assignmentTypeId;
	}

	public AssignmentTypes(int assignmentTypeId, String assignmentTypeName) {
		this.assignmentTypeId = assignmentTypeId;
		this.assignmentTypeName = assignmentTypeName;
	}

	public int getAssignmentTypeId() {
		return this.assignmentTypeId;
	}

	public void setAssignmentTypeId(int assignmentTypeId) {
		this.assignmentTypeId = assignmentTypeId;
	}

	public String getAssignmentTypeName() {
		return this.assignmentTypeName;
	}

	public void setAssignmentTypeName(String assignmentTypeName) {
		this.assignmentTypeName = assignmentTypeName;
	}

}
