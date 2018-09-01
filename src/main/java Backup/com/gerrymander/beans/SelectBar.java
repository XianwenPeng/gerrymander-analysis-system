package com.gerrymander.beans;

import javax.persistence.Column;

public class SelectBar {
	private int districtId;
	private int year;
	private String stateName;
	private int measureName;
	private Measure measure;
	
	public Measure getMeasure() {
		return measure;
	}
	public void setMeasure(Measure measure) {
		this.measure = measure;
	}
	public int getMeasureName() {
		return measureName;
	}
	public void setMeasureName(int measureName) {
		this.measureName = measureName;
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
