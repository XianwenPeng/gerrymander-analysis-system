package com.gerrymander.service;

import org.springframework.stereotype.Service;

import com.gerrymander.Measure.ConsistentAdvantage;
import com.gerrymander.Measure.LopsidedWins;
import com.gerrymander.beans.*;
import com.gerrymander.constant.ControllerAttributes;

@Service("reportService")
public class ReportService {
	private String lopsidedExplain;
	private String consistentExplain;
	
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
	
	public String generateLopsidedExplain(LopsidedWins lopsided) {
		State state = lopsided.getState();
		double demoAve = (int)(100*lopsided.getDemoWinAve())/100.0;
		double repubAve = (int)(100*lopsided.getRepubWinAve())/100.0;
		String pValue = String.format("%.4e", lopsided.getPValue());
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
}
