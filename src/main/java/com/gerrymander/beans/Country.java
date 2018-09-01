package com.gerrymander.beans;

import java.util.*;

public class Country {
	private String name;
	private Map<State, List<District>> allStates;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<State, List<District>> getAllStates() {
		return allStates;
	}
	public void setAllStates(Map<State, List<District>> allStates) {
		this.allStates = allStates;
	}
}
