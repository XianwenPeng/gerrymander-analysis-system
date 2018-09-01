package com.gerrymander.Measure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gerrymander.beans.*;
import com.gerrymander.service.ReportService;

import org.apache.commons.math3.stat.inference.*;

public class ConsistentAdvantage {
	private double median;
	private double mean;
	private double difference;
	private State state;
	private Result result;
	private Party party;
	private List<Double> demoVotes;
	private double pValue;
	private ReportService reportService;
	private String consistentExplain;
	
	public ConsistentAdvantage() {}
	
	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public String getConsistentExplain() {
		return consistentExplain;
	}

	public void setConsistentExplain(String consistentExplain) {
		this.consistentExplain = consistentExplain;
	}
	
	public double getMedian() {
		return median;
	}

	public void setMedian(double median) {
		this.median = median;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getDifference() {
		return difference;
	}

	public void setDifference(double difference) {
		this.difference = difference;
	}	
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public List<Double> getDemoVotes() {
		return demoVotes;
	}

	public void setDemoVotes(List<Double> demoVotes) {
		this.demoVotes = demoVotes;
	}
	
	public double getpValue() {
		return pValue;
	}

	public void setpValue(double pValue) {
		this.pValue = pValue;
	}
	
	public double[] listToArray(List<Double> list) {
		double[] arr = new double[list.size()];
		for(int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}

	public void measure(List<Votes> votes, State state){
		TTest ttest = new TTest();
		this.state = state;
		this.demoVotes = new ArrayList<>();
		this.reportService = new ReportService();
		double totalDemoVotes = 0;
		for(Votes vote: votes){
			this.demoVotes.add(vote.getDemoVotes());
			totalDemoVotes += vote.getDemoVotes();
		}
		Collections.sort(demoVotes);
		this.mean = totalDemoVotes / this.demoVotes.size();
		this.median = (this.demoVotes.size() % 2 == 1 ? this.demoVotes.get(this.demoVotes.size()/2) 
				: (this.demoVotes.get(this.demoVotes.size()/2 - 1) + this.demoVotes.get(this.demoVotes.size()/2))/2.0);
		this.difference = this.mean - this.median;
		this.party = (this.difference < 0 ? Party.DEMOCRATIC : Party.REPUBLICAN);
		if(state.getRepubSeats() == 0 || state.getDemoSeats() == 0 || this.demoVotes.size() < 2){
			this.result = Result.SKIP;
			this.consistentExplain = this.reportService.generateConsistentExplain(this);
			return;
		}
		this.pValue = ttest.tTest(this.mean, listToArray(this.demoVotes));
		this.result = (this.pValue < 0.05 ? Result.FAIL : Result.PASS);
		this.consistentExplain = this.reportService.generateConsistentExplain(this);
	}
}
