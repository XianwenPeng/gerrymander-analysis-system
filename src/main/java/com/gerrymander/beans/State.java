package com.gerrymander.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(StateId.class)
@Table(name = "state")
public class State {
	@Id
	private int uniqueId;
	
	@Id
	private int year;
	
	@Id
	private String stateName;
	
	@Column(name = "demoSeats")
	private int demoSeats;
	
	@Column(name = "repubSeats")
	private int repubSeats;
	
	@Column(name = "Party")
	private Party party;
	
	public State() {
		
	}
	
	public State(String stateName, int year) {
		this.stateName = stateName;
		this.year = year;
	}
	
	public State(String stateName, int year, int demoSeats, int repubSeats, Party party) {
		this.stateName = stateName;
		this.year = year;
		this.repubSeats = repubSeats;
		this.demoSeats = demoSeats;
		this.party = party;
	}
	
	public Party getParty() {
		return party;
	}
	
	public void setParty(Party party) {
		this.party = party;
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

	public int getDemoSeats() {
		return demoSeats;
	}

	public void setDemoSeats(int demoSeats) {
		this.demoSeats = demoSeats;
	}
	
	public int getRepubSeats() {
		return repubSeats;
	}

	public void setRepubSeats(int repubSeats) {
		this.repubSeats = repubSeats;
	}


}
