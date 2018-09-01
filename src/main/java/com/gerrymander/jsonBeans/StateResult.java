package com.gerrymander.jsonBeans;

import java.util.Map;

public class StateResult {
	private String stateName;
	private Map<String, String> result;
	
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public Map<String, String> getResult() {
		return result;
	}
	public void setResult(Map<String, String> result) {
		this.result = result;
	}
	
	public StateResult() {}
}
