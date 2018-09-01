package com.gerrymander.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerrymander.beans.*;
import com.gerrymander.repository.DistrictRepository;
import com.gerrymander.repository.StateRepository;

@Service("geoService")
public class GeoService {

	private DistrictRepository districtRepository;
	private StateRepository stateRepository;
	
	@Autowired
	public GeoService(DistrictRepository districtRepository, StateRepository stateRepository) {
		this.districtRepository = districtRepository;
		this.stateRepository = stateRepository;
	}
	
	public District findDistrict(String stateName, int districtId, int year) {
		return districtRepository.findDistrictByStateNameAndDistrictIdAndYear(stateName, districtId, year);
	}	 
	public District findDistrict(int uniqueId, int year) {
		return districtRepository.findDistrictByUniqueIdAndYear(uniqueId, year);
	}
	public List<District> findAllDistrict(){
		return districtRepository.findAll();
	}
	public List<District> findAllDistrict(String stateName, int year){
		return districtRepository.findAllDistrictByStateNameAndYear(stateName, year);
	}
	
	public void saveDistrict(District district) {
		districtRepository.save(district);
	}
	
	public void removeDistrict(District district) {
		districtRepository.delete(district);
	}
	
	public State findState(String stateName, int year) {
		return stateRepository.findByStateNameAndYear(stateName, year);
	}	 
	public State findState(int uniqueId, int year) {
		return stateRepository.findStateByUniqueIdAndYear(uniqueId, year);
	}
	public List<State> findState(int year) {
		return stateRepository.findStateByYear(year);
	}
	
	public void saveState(State state) {
		stateRepository.save(state);
	}
	
	public void removeState(State state) {
		stateRepository.delete(state);
	}
	
	public List<State> findAllState(){
		return stateRepository.findAll();
	}
//	
//	public Country generateCountry() {
//		Country country = new Country();
//		this.findAllState()
//		return country;
//	}
}
