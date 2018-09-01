package com.gerrymander.htmlObject;

public class ExportPane {
	private int year;
	private int yearTo;
	private int yearFrom;
	private String yearsFileName;
	private String yearFileName;

	public int getYearTo() {
		return yearTo;
	}
	public void setYearTo(int yearTo) {
		this.yearTo = yearTo;
	}
	public int getYearFrom() {
		return yearFrom;
	}
	public void setYearFrom(int yearFrom) {
		this.yearFrom = yearFrom;
	}
	
	public String getYearsFileName() {
		return yearsFileName;
	}
	public void setYearsFileName(String yearsFileName) {
		this.yearsFileName = yearsFileName;
	}
	public String getYearFileName() {
		return yearFileName;
	}
	public void setYearFileName(String yearFileName) {
		this.yearFileName = yearFileName;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
}
