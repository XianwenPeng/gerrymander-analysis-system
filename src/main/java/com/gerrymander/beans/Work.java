package com.gerrymander.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(WorkId.class)
@Table(name = "work")
public class Work {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Id
	private String email;
	
	@Id
	private String workName;
	
	@Id
	private int workItemId;
	
	public Work() {}

	public Work(int id, String email, String workName) {
		super();
		this.id = id;
		this.email = email;
		this.workName = workName;
	}
	
	

	public int getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(int workItemId) {
		this.workItemId = workItemId;
	}
	
	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
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

	
}
