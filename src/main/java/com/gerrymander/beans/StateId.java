package com.gerrymander.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class StateId implements Serializable{

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "uniqueId")
	private int uniqueId;
	
	@Column(name = "year", nullable = false)
	private int year;
	
	@Column(name = "stateName", nullable = false)
	private String stateName;
	
	public StateId() {}

	public StateId(int uniqueId, int year, String stateName) {
		super();
		this.uniqueId = uniqueId;
		this.year = year;
		this.stateName = stateName;
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	
}
