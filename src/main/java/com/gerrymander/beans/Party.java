package com.gerrymander.beans;

public enum Party {
	DEMOCRATIC(0), REPUBLICAN(1);
	private int val;
	
	private Party(int val) {
		this.val = val;
	}
	public int getVal() {
		return val;
	}
	
}
