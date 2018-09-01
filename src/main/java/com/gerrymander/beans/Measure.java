package com.gerrymander.beans;

public enum Measure {
	LOPSIDED_WINS("Lopsided Wins (T-test)"),
	CONSISTENT_ADVANTAGE("Consistent Advantage (mean-median difference)"), 
	EFFICIENCY_GAP("Efficiency Gap"), 
	EXCESS_SEATS("Excess Seats (Simulated Election)"),
	RELIABLE_WINS("Reliable Wins Test"),
	ALLMEASURE("All Measures");
	
	private String name;
	private Measure(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
