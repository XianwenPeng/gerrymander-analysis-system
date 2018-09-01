package com.gerrymander.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class VotesId implements Serializable{

	/**
	 * 
	 */
	public VotesId() {}
	public VotesId(String stateName, int districtId, int year) {
		this.stateName = stateName;
		this.districtId = districtId;
		this.year = year;
	}
	private static final long serialVersionUID = 4578104072536654078L;

	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "stateName", nullable = false)
	private String stateName;
	
	@Column(name = "districtId", nullable = false)
	private int districtId;
	
	@Column(name = "year", nullable = false)
	private int year;
	
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
