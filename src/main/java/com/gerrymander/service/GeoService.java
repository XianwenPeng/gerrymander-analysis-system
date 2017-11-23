package com.gerrymander.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerrymander.beans.District;
import com.gerrymander.repository.GeoRepository;

@Service("geoService")
public class GeoService {

	private GeoRepository geoRepository;
	
		@Autowired
		public GeoService(GeoRepository geoRepository) {
			this.geoRepository = geoRepository;
		}
		
		public District findDistrict(String stateName, int districtId, int year) {
			return geoRepository.findByStateNameAndDistrictIdAndYear(stateName, districtId, year);
		}
//		 
		public District findDistrict(int uniqueId) {
			return geoRepository.findByUniqueId(uniqueId);
		}
		
		public void saveDistrict(District district) {
			geoRepository.save(district);
		}
		
		public void removeDistrict(District district) {
			geoRepository.delete(district);
		}
		

}
