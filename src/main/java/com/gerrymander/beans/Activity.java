package com.gerrymander.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "activity")
@IdClass(ActivityId.class)
public class Activity {
	
	@Id
	@Column(name = "id")
	private int id;
	@Id
	@Column(name = "email")
	private String email;
	@Id
	@Column(name = "action")
	private String action;
	@Id
	@Column(name = "time")
	private String time;
	
	public Activity(String email, String action) {
		this.email = email;
		this.action = action;
	}
	
	public Activity() {}
	

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	} 

	
}
