package com.gerrymander.beans;

import java.time.Year;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "district")
public class District {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "uniqueId")
	private int uniqueId;
	
	@Column(name = "districtId", nullable = false)
	private int districtId;
	
	@Column(name = "year", nullable = false)
	private int year;
	
	@Column(name = "stateName", nullable = false)
	private String stateName;
	
	@Column(name = "representative")
	private String representative;
	
	@Column(name = "area")
	private double area;
	
	@Column(name = "population")
	private double population;
	
	@Column(name = "party")
	private Party party;
	
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

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getPopulation() {
		return population;
	}

	public void setPopulation(double population) {
		this.population = population;
	}

}
