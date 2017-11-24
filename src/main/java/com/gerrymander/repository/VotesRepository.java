package com.gerrymander.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gerrymander.beans.Votes;

@Repository("votesRepository")
public interface VotesRepository extends JpaRepository<Votes, Long> {
	
	 Votes findByStateNameAndDistrictIdAndYear(String stateName, int districtId, int year);
	 
	 Votes findById(int id);
}

