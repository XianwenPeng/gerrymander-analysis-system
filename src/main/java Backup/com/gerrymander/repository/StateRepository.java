package com.gerrymander.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gerrymander.beans.District;
import com.gerrymander.beans.State;

@Repository("stateRepository")
public interface StateRepository extends JpaRepository<State, Long> {

	 State findByStateNameAndYear(String stateName, int year);
	 
	 State findStateByUniqueIdAndYear(int uniqueId, int year);
	 
	 List<State> findStateByYear(int year);
	 
}
