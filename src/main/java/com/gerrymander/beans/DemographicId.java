package com.gerrymander.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class DemographicId implements Serializable {
	
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "stateName", nullable = false)
	private String stateName;
	
	@Column(name = "year", nullable = false)
	private int year;

}
