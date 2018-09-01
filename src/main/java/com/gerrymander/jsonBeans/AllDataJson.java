package com.gerrymander.jsonBeans;

import java.util.List;
import java.util.Map;

import com.gerrymander.beans.District;
import com.gerrymander.beans.State;
import com.gerrymander.measure.AllMeasure;

public class AllDataJson {
	private int year;
	private List<State> state;
	private Map<String, AllMeasure> allMeasure;
	private Map<String, List<District>> districts;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public List<State> getState() {
		return state;
	}
	public void setState(List<State> state) {
		this.state = state;
	}
	public Map<String, AllMeasure> getAllMeasure() {
		return allMeasure;
	}
	public void setAllMeasure(Map<String, AllMeasure> allMeasure) {
		this.allMeasure = allMeasure;
	}
	public Map<String, List<District>> getDistricts() {
		return districts;
	}
	public void setDistricts(Map<String, List<District>> districts) {
		this.districts = districts;
	}
	
	
	
}
