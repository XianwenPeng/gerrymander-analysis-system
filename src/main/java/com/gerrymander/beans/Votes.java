package com.gerrymander.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "votes")
@IdClass(VotesId.class)
public class Votes {
	
	@Id
	private int id;
	
	@Id
	private int districtId;
	@Id
	private int year;
	@Id
	private String stateName;
	
	@Column(name = "demoVotes")
	private double demoVotes;
	
	@Column(name = "repubVotes")
	private double repubVotes;
	
	private Party party;
	
	public Votes() {
		
	}
	
	public Votes(String stateName, int districtId, int year, double demoVotes, double repubVotes) {
		this.stateName = stateName;
		this.districtId = districtId;
		this.year = year;
		this.demoVotes = demoVotes;
		this.repubVotes = repubVotes;
		
	}
	
	
	public Votes(int id, String stateName, int districtId, int year, double demoVotes, double repubVotes) {
		this.id = id;
		this.stateName = stateName;
		this.districtId = districtId;
		this.year = year;
		this.demoVotes = demoVotes;
		this.repubVotes = repubVotes;
		if(demoVotes > repubVotes)
			this.setParty(Party.DEMOCRATIC);
		else
			this.setParty(Party.REPUBLICAN);
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

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	
}
