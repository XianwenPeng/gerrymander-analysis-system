package com.gerrymander.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class WorkId implements Serializable{
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "workName", nullable = false)
	private String workName;
	
	@Column(name = "workItemId", nullable = false)
	private int workItemId;
	
	public WorkId() {
		
	}

	public WorkId(String email, String workName) {
		this.email = email;
		this.workName = workName;
	}
	

	public int getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(int workItemId) {
		this.workItemId = workItemId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}
	
	
}
