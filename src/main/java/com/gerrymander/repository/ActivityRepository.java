package com.gerrymander.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gerrymander.beans.Activity;
import com.gerrymander.beans.District;

@Repository("activityRepository")
public interface ActivityRepository extends JpaRepository<Activity, Long>{
	
	public List<Activity> findAllByEmail(String email);
	
}
