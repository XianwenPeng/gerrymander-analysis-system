package com.gerrymander.htmlObject;

import java.util.Map;

public class DemoDataObj {
	
	private String stateName;
	private Map<String, Integer> data;
	
	public DemoDataObj() {}
	
	

	public DemoDataObj(String stateName, Map<String, Integer> data) {
		super();
		this.stateName = stateName;
		this.data = data;
	}



	public Map<String, Integer> getData() {
		return data;
	}



	public void setData(Map<String, Integer> data) {
		this.data = data;
	}



	


	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}


	
	
	
}
