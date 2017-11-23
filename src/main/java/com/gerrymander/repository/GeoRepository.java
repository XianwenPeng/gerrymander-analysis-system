package com.gerrymander.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gerrymander.beans.District;

@Repository("geoRepository")
public interface GeoRepository extends JpaRepository<District, Long> {
	
	 District findByStateNameAndDistrictIdAndYear(String stateName, int districtId, int year);
	 
	 District findByUniqueId(int uniqueId);
}

