package com.gerrymander.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "votes")
public class Votes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "districtId", nullable = false)
	private int districtId;
	
	@Column(name = "year", nullable = false)
	private int year;
	
	@Column(name = "stateName", nullable = false)
	private String stateName;
	
	@Column(name = "demoVotes")
	private double demoVotes;
	
	@Column(name = "repubVotes")
	private double repubVotes;
	
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

	public double getRepubVotes() {
		return repubVotes;
	}

	public void setRepubVotes(double repubVotes) {
		this.repubVotes = repubVotes;
	}

	public double getDemoVotes() {
		return demoVotes;
	}

	public void setDemoVotes(double demoVotes) {
		this.demoVotes = demoVotes;
	}
}
