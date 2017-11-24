package com.gerrymander.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gerrymander.beans.District;
import com.gerrymander.beans.State;

@Repository("districtRepository")
public interface DistrictRepository extends JpaRepository<District, Long> {
	
	 District findDistrictByStateNameAndDistrictIdAndYear(String stateName, int districtId, int year);
	 
	 District findDistrictByUniqueIdAndYear(int uniqueId, int year);
	 
	 List<District> findAllDistrictByStateNameAndYear(String stateName, int year);
	 
}

