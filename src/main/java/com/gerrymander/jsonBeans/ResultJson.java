package com.gerrymander.jsonBeans;

import java.util.*;

public class ResultJson {
	private int year;
	private List<StateResult> results;
	
	public ResultJson() {}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<StateResult> getResults() {
		return results;
	}

	public void setResults(List<StateResult> results) {
		this.results = results;
	}
	
}
