package com.gerrymander.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(WorkItemId.class)
@Table(name = "workItem")
public class WorkItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Id
	private String email;
	
	@Id
	private String state;
	
	@Id
	private String district;
	
	@Id
	private int year;
	
	public WorkItem() {}

	public WorkItem(int id, String email) {
		super();
		this.id = id;
		this.email = email;
	}
	
	

	public WorkItem( String email, String state, String district, int year) {
		super();
		this.email = email;
		this.state = state;
		this.district = district;
		this.year = year;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	

	
	
}
