package com.gerrymander.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerrymander.beans.Demographic;
import com.gerrymander.repository.DemographicRepository;
import com.gerrymander.repository.UserRepository;

@Service("demographicService")
public class DemographicService {
	

	private DemographicRepository demographicRepository;

	@Autowired
	public DemographicService(DemographicRepository demographicRepository) {
		this.demographicRepository = demographicRepository;
	}
	
	public List<Demographic> findByYear(int year){
		return demographicRepository.findAllByYear(year);
	}
	public Demographic findByStateNameAndYear(String stateName, int year) {
		return demographicRepository.findByStateNameAndYear(stateName, year);
	}
	
	public void save(Demographic demo) {
		demographicRepository.save(demo);
	}
		

}
