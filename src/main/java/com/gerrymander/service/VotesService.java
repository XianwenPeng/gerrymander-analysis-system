package com.gerrymander.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerrymander.beans.District;
import com.gerrymander.beans.Votes;
import com.gerrymander.repository.DistrictRepository;
import com.gerrymander.repository.VotesRepository;

@Service("votesService")
public class VotesService {

	private VotesRepository votesRepository;
	
		@Autowired
		public VotesService(VotesRepository votesRepository) {
			this.votesRepository = votesRepository;
		}
		
		public VotesService() {
			
		}
		
		public Votes findVotes(String stateName, int districtId, int year) {
			return votesRepository.findByStateNameAndDistrictIdAndYear(stateName, districtId, year);
		}
		
		public List<Votes> findVotes(String stateName, int year) {
			return votesRepository.findByStateNameAndYear(stateName, year);
		}
		
		public List<Votes> findByYear(int year) {
			return votesRepository.findAllByYear(year);
		}
//		 
		public Votes findVotes(int id) {
			return votesRepository.findById(id);
		}
		
		public List<Votes> findAllVotes() {
			return votesRepository.findAll();
		}
		
		public void saveVotes(Votes votes) {
			votesRepository.save(votes);
		}
		
		public void deleteByYear(int year) {
			votesRepository.deleteByYear(year);
		}

}
