package com.gerrymander.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "state")
public class State {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "uniqueId")
	private int uniqueId;
	
	@Column(name = "year", nullable = false)
	private int year;
	
	@Column(name = "stateName", nullable = false)
	private String stateName;
	
	@Column(name = "demoSeats")
	private int demoSeats;
	
	@Column(name = "repubSeats")
	private int repubSeats;
	
	@Column(name = "area")
	private double area;
	
	@Column(name = "population")
	private double population;
	
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
