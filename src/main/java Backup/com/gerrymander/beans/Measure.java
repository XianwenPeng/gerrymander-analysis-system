package com.gerrymander.beans;

public enum Measure {
	LOPSIDED_WINS("Lopsided Wins (T-test)"),
	CONSISTENT_ADVANTAGE("Consistent Advantage (mean-median difference)"), 
	EFFIENCY_GAP("Efficiency Gap"), 
	EXCESS_SEATES("Excess Seats (Simulated Election)");
	
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
