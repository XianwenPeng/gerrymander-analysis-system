package com.gerrymander.jsonBeans;

import java.util.*;

import com.gerrymander.beans.District;
import com.gerrymander.beans.State;

public class ElectionDataJson {
	private int year;
	private List<State> states;
	private Map<String, List<District>> districts;

	public ElectionDataJson() {
		
	}
	public ElectionDataJson(int year, List<State> states, Map<String, List<District>> districts) {
		this.year = year;
		this.states = states;
		this.districts = districts;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public List<State> getStates() {
		return states;
	}
	public void setStates(List<State> states) {
		this.states = states;
	}
	public Map<String, List<District>> getDistricts() {
		return districts;
	}
	public void setDistricts(Map<String, List<District>> districts) {
		this.districts = districts;
	}
	
	
}
