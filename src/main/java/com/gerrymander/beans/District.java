package com.gerrymander.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(DistrictId.class)
@Table(name = "district")
public class District {
	@Id
	private int uniqueId;
	
	@Id
	private int districtId;
	
	@Id
	private int year;
	
	@Id
	private String stateName;
	
	@Column(name = "party")
	private Party party;
	
	@Column(name = "demoRatio")
	private double demoRatio;
	
	@Column(name = "repubRatio")
	private double repubRatio;
	
	public double getDemoRatio() {
		return demoRatio;
	}

	public void setDemoRatio(double demoRatio) {
		this.demoRatio = demoRatio;
	}

	public double getRepubRatio() {
		return repubRatio;
	}

	public void setRepubRatio(double repubRatio) {
		this.repubRatio = repubRatio;
	}

	public District() {
		
	}
		
	public District(String stateName, int districtId, int year) {
		this.stateName = stateName;
		this.districtId = districtId;
		this.year = year;
	}
	
	public District(String stateName, int districtId, int year, Party party) {
		this.stateName = stateName;
		this.districtId = districtId;
		this.year = year;
		this.party = party;
	}
	
	public District(String stateName, int districtId, int year, Party party, double demoRatio, double repubRatio) {
		this.stateName = stateName;
		this.districtId = districtId;
		this.year = year;
		this.party = party;
		this.demoRatio = demoRatio;
		this.repubRatio = repubRatio;
	}
	
	public Party getParty() {
		return party;
	}
	
	public void setParty(Party party) {
		this.party = party;
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
