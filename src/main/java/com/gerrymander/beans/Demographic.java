package com.gerrymander.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "demographic")
@IdClass(DemographicId.class)
public class Demographic {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "stateName", nullable = false)
	private int year;
	@Column(name = "stateName", nullable = false)
	private String stateName;
	
	@Column(name = "male")
	private int male;
	
	@Column(name = "female")
	private int female;
	
	@Column(name = "white")
	private int white;

	@Column(name = "black")
	private int black;
	
	@Column(name = "asian")
	private int asian;
	
	@Column(name = "hispanic")
	private int hispanic;
	
	public Demographic() {}
	
	public Demographic(int id, String stateName, int year, int male, int female, int white, int black, int asian, int hispanic) {
		super();
		this.id = id;
		this.year = year;
		this.stateName = stateName;
		this.male = male;
		this.female = female;
		this.white = white;
		this.black = black;
		this.asian = asian;
		this.hispanic = hispanic;
	}

	public Demographic(String stateName, int year, int male, int female, int white, int black, int asian, int hispanic) {
		super();
		this.year = year;
		this.stateName = stateName;
		this.male = male;
		this.female = female;
		this.white = white;
		this.black = black;
		this.asian = asian;
		this.hispanic = hispanic;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getMale() {
		return male;
	}

	public void setMale(int male) {
		this.male = male;
	}

	public int getFemale() {
		return female;
	}

	public void setFemale(int female) {
		this.female = female;
	}

	public int getWhite() {
		return white;
	}

	public void setWhite(int white) {
		this.white = white;
	}

	public int getBlack() {
		return black;
	}

	public void setBlack(int black) {
		this.black = black;
	}

	public int getAsian() {
		return asian;
	}

	public void setAsian(int asian) {
		this.asian = asian;
	}

	public int getHispanic() {
		return hispanic;
	}

	public void setHispanic(int hispanic) {
		this.hispanic = hispanic;
	}
	
	
	
}
