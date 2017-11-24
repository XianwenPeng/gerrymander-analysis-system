package com.gerrymander.configuration;

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

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private VotesService votesService;
	@Autowired
	private GeoService geoService;

	@Autowired
	public JobCompletionListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
//			log.info("!!! JOB FINISHED! Time to verify the results");
			updateDistrict();
			updateState();
//			List<Votes> results = jdbcTemplate.query("SELECT * FROM votes", new RowMapper<Votes>() {
//				@Override
//				public Votes mapRow(ResultSet rs, int row) throws SQLException {
//					return new Votes(rs.getString(1), Integer.parseInt(rs.getString(2)), 
//							Integer.parseInt(rs.getString(3)), Double.parseDouble(rs.getString(4)), 
//							Double.parseDouble(rs.getString(5)));
//				}
//			});
//
//			for (Votes person : results) {
//				log.info("Found <" + person + "> in the database.");
//			}

		}
	}
	
	public void updateDistrict() {
		List<Votes> votes = votesService.findAllVotes();
		for(Votes vote: votes) {
			if(vote.getDemoVotes() > vote.getRepubVotes()) {
				geoService.saveDistrict(new District(vote.getStateName(), vote.getDistrictId(),
						vote.getYear(), Party.DEMOCRATIC));
			}
			else {
				geoService.saveDistrict(new District(vote.getStateName(), vote.getDistrictId(), 
						vote.getYear(), Party.REPUBLICAN));
			}
		}
	}
	
	public void updateState() {
		List<District> districts = geoService.findAllDistrict();
		List<State> states = geoService.findAllState();
		int demoSeats = 0, repubSeats = 0;
		int count = 0;
		String stateName = "";
		State state = new State();
		int year = 0;
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