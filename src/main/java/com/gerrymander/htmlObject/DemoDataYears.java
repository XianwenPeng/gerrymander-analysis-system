package com.gerrymander.htmlObject;

import java.util.List;

public class DemoDataYears {
	private int year;
	private List<DemoDataObj> demoData;
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public List<DemoDataObj> getDemoData() {
		return demoData;
	}
	public void setDemoData(List<DemoDataObj> demoData) {
		this.demoData = demoData;
	}
	public DemoDataYears(int year, List<DemoDataObj> demoData) {
		super();
		this.year = year;
		this.demoData = demoData;
	}
	
	public DemoDataYears() {}
}
