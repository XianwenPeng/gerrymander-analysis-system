package com.gerrymander.service;

import org.springframework.stereotype.Service;

import com.gerrymander.beans.*;
import com.gerrymander.constant.ControllerAttributes;
import com.gerrymander.measure.ConsistentAdvantage;
import com.gerrymander.measure.EfficiencyGap;
import com.gerrymander.measure.LopsidedWins;
import com.gerrymander.measure.ExcessSeats;

@Service("reportService")
public class ReportService {
	private String lopsidedExplain;
	private String consistentExplain;
	private String efficiencyExplain;
	private String excessExplain;
	
	public ReportService(){}
	
	public String getLopsidedExplain() {
		return lopsidedExplain;
	}
	
	public String getConsistentExplain() {
		return consistentExplain;
	}
	
	public void setLopsidedExplain(String lopsidedExplain) {
		this.lopsidedExplain = lopsidedExplain;
	}
	
	public void setConsistentExplain(String consistentExplain){
		this.consistentExplain = consistentExplain;
	}
	
	public String getEfficiencyExplain() {
		return efficiencyExplain;
	}

	public void setEfficiencyExplain(String efficiencyExplain) {
		this.efficiencyExplain = efficiencyExplain;
	}

	public String getExcessExplain() {
		return excessExplain;
	}

	public void setExcessExplain(String excessExplain) {
		this.excessExplain = excessExplain;
	}

	public String generateLopsidedExplain(LopsidedWins lopsided) {
		State state = lopsided.getState();
		double demoAve = (int)(100*lopsided.getDemoWinAve())/100.0;
		double repubAve = (int)(100*lopsided.getRepubWinAve())/100.0;
		String pValue = String.format("%.4e", lopsided.getpValue());
		if(lopsided.getResult() == Result.FAIL) {
			this.lopsidedExplain = String.format(ControllerAttributes.LOPSIDED_FAIL, state.getStateName(),
					state.getYear(), repubAve+"%", demoAve+"%", state.getStateName(), (demoAve > repubAve ? Party.REPUBLICAN : Party.DEMOCRATIC),
					pValue);
		}
		else if(lopsided.getResult() == Result.PASS) {
			this.lopsidedExplain = String.format(ControllerAttributes.LOPSIDED_PASS, state.getStateName(),
					state.getYear(), repubAve+"%", demoAve+"%");
		}
		else if(lopsided.getResult() == Result.SKIP) {
			int demoSeat = state.getDemoSeats();
			int repubSeat = state.getRepubSeats();
			this.lopsidedExplain = String.format(ControllerAttributes.LOPSIDED_SKIP, (demoSeat > repubSeat ? Party.DEMOCRATIC : Party.REPUBLICAN),
					Math.abs(demoSeat-repubSeat), state.getStateName(), state.getYear());
		}
		return lopsidedExplain;
	}
	
	public String generateConsistentExplain(ConsistentAdvantage consistent) {
		State state = consistent.getState();
		double mean = consistent.getMean();
		double median = consistent.getMedian();
		double difference = consistent.getDifference();
		double pValue = consistent.getpValue();
		Party party = consistent.getParty();
		if(consistent.getResult() == Result.FAIL) {
			this.consistentExplain = String.format(ControllerAttributes.CONSISTENT_FAIL, state.getStateName(), state.getYear(), state.getStateName(), 
					state.getYear(), median, mean, difference, state.getStateName(), party, pValue);
		}
		else if(consistent.getResult() == Result.PASS) {
			this.consistentExplain = String.format(ControllerAttributes.CONSISTENT_PASS, state.getStateName(), state.getYear(), state.getStateName(), 
					state.getYear(), median, mean, difference, state.getStateName());
		}
		else if(consistent.getResult() == Result.SKIP) {
			this.consistentExplain = String.format(ControllerAttributes.CONSISTENT_SKIP, state.getStateName(), state.getYear(), state.getStateName(), 
					state.getYear(), median, mean, difference, state.getStateName());
		}
		return consistentExplain;
	}
	
	public String generateEfficiencyExplain(EfficiencyGap efficiency) {
		State state = efficiency.getState();
		double demoSeatMargin = efficiency.getDemoSeatMargin();
		double demoVoteMargin = efficiency.getDemoVoteMargin();
		double gap = efficiency.getGap();
		Party party = efficiency.getParty();
		if(efficiency.getResult() == Result.FAIL) {
			this.efficiencyExplain = String.format(ControllerAttributes.EFFICIENCY_FAIL, state.getStateName(), state.getYear(), state.getStateName(), 
					state.getYear(), demoSeatMargin, demoVoteMargin, gap, state.getStateName(), party);
		}
		else if(efficiency.getResult() == Result.PASS) {
			this.efficiencyExplain = String.format(ControllerAttributes.EFFICIENCY_PASS, state.getStateName(), state.getYear(), state.getStateName(), 
					state.getYear(), demoSeatMargin, demoVoteMargin, gap, state.getStateName());
		}
		else if(efficiency.getResult() == Result.SKIP) {
			this.efficiencyExplain = String.format(ControllerAttributes.EFFICIENCY_SKIP, state.getStateName(), state.getYear(), state.getStateName(), 
					state.getYear(), demoSeatMargin, demoVoteMargin, gap, state.getStateName());
		}
		return efficiencyExplain;
	}
	
	public String generateExcessExplain(ExcessSeats excessSeats) {
		State state = excessSeats.getState();
		int demoWinSeats = excessSeats.getDemoWinSeats();
		int totalSeats = excessSeats.getTotalSeats();
		double demoStateVotes = excessSeats.getDemoStateVotes();
		double difference = excessSeats.getDifference();
		double simuDemoSeats = excessSeats.getSimuDemoSeats();
		Party party = excessSeats.getParty();
		if(excessSeats.getResult() == Result.FAIL) {
			this.excessExplain = String.format(ControllerAttributes.EXCESS_FAIL, state.getStateName(), state.getYear(), state.getYear(), state.getStateName(),
					state.getYear(), demoWinSeats, totalSeats, demoStateVotes, state.getYear(), simuDemoSeats, difference, state.getStateName(), party);
		}
		else if(excessSeats.getResult() == Result.PASS) {
			this.excessExplain = String.format(ControllerAttributes.EXCESS_PASS, state.getStateName(), state.getYear(), state.getYear(), state.getStateName(),
					state.getYear(), demoWinSeats, totalSeats, demoStateVotes, state.getYear(), simuDemoSeats, difference, state.getStateName());
		}
		else if(excessSeats.getResult() == Result.SKIP) {
			this.excessExplain = String.format(ControllerAttributes.EXCESS_PASS, state.getStateName(), state.getYear(), state.getYear(), state.getStateName(),
					state.getYear(), demoWinSeats, totalSeats, demoStateVotes, state.getYear(), simuDemoSeats, difference, state.getStateName(), state.getYear());
		}
		return excessExplain;
	}
}
