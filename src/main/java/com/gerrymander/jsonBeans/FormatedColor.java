package com.gerrymander.jsonBeans;

import java.util.*;

public class FormatedColor {
	private int year;
	private List<StateColor> states;
	private List<DistrictColor> districts;
	
	public List<StateColor> getStates() {
		return states;
	}
	public void setStates(List<StateColor> statesList) {
		this.states = statesList;
	}
	public List<DistrictColor> getDistricts() {
		return districts;
	}
	public void setDistricts(List<DistrictColor> districts) {
		this.districts = districts;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
}
