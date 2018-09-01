package com.gerrymander.configuration;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.gerrymander.beans.District;
import com.gerrymander.beans.Party;
import com.gerrymander.beans.State;
import com.gerrymander.beans.Votes;
import com.gerrymander.service.GeoService;
import com.gerrymander.service.VotesService;

@Component
public class JobCompletionListener extends JobExecutionListenerSupport {
	
	@Autowired
	private VotesService votesService;
	@Autowired
	private GeoService geoService;

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			updateDistrict();
			updateState();
		}
	}
	
	public void updateDistrict() {
		List<Votes> votes = votesService.findAllVotes();
		for(Votes vote: votes) {
			double demo = vote.getDemoVotes();
			double repub = vote.getRepubVotes();
			demo = 100*demo/(demo + repub);
			repub = 100-demo;
			if(vote.getDemoVotes() > vote.getRepubVotes()) {
				geoService.saveDistrict(new District(vote.getStateName(), vote.getDistrictId(),
						vote.getYear(), Party.DEMOCRATIC, demo, repub));
			}
			else {
				geoService.saveDistrict(new District(vote.getStateName(), vote.getDistrictId(), 
						vote.getYear(), Party.REPUBLICAN, demo, repub));
			}
		}
	}
	
	public void updateState() {
		List<District> districts = geoService.findAllDistrict();
		int demoSeats = 0, repubSeats = 0;
		String stateName = "";
		State state = new State();
		if(districts.size() != 0) {
			stateName = districts.get(0).getStateName();
			state = new State(districts.get(0).getStateName(), districts.get(0).getYear());
		}
		for(District district: districts) {
			if(!district.getStateName().equals(stateName)) {
				state.setParty((demoSeats > repubSeats) ? Party.DEMOCRATIC : Party.REPUBLICAN);
				geoService.saveState(state);
				state = new State(district.getStateName(), district.getYear());
				demoSeats = 0;
				repubSeats = 0;
			}
			if(district.getParty() == Party.DEMOCRATIC) {
				demoSeats++;
				state.setDemoSeats(demoSeats);
			}
			else {
				repubSeats++;
				state.setRepubSeats(repubSeats);
			}
			stateName = district.getStateName();
		}
		state.setParty((demoSeats > repubSeats) ? Party.DEMOCRATIC : Party.REPUBLICAN);
		geoService.saveState(state);
	}
	
}