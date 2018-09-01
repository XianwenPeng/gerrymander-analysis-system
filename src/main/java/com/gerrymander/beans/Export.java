package com.gerrymander.beans;

public enum Export {
	EXPORT_ONE_YEAR("Export one year"),
	EXPORT_FROM_TO("Export between years"); 
	
	private String select;

	private Export(String select) {
		this.select = select;
	}
	
	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}
}
