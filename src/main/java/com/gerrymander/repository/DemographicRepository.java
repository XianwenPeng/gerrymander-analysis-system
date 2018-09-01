package com.gerrymander.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gerrymander.beans.Demographic;
import com.gerrymander.beans.User;

@Repository("DemographicRepository")
public interface DemographicRepository  extends JpaRepository<Demographic, Long> {
	Demographic findByStateNameAndYear(String stateName, int year);
	List<Demographic> findAllByYear(int year);
}
