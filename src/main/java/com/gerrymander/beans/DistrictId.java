package com.gerrymander.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class DistrictId implements Serializable{
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "uniqueId")
	private int uniqueId;
	
	@Column(name = "districtId", nullable = false)
	private int districtId;
	
	@Column(name = "year", nullable = false)
	private int year;
	
	@Column(name = "stateName", nullable = false)
	private String stateName;

	public DistrictId() {
		
	}
	
	public DistrictId(int uniqueId, int districtId, int year, String stateName) {
		super();
		this.uniqueId = uniqueId;
		this.districtId = districtId;
		this.year = year;
		this.stateName = stateName;
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
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
