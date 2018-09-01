package com.gerrymander.measure;

import java.util.ArrayList;
import java.util.List;

import com.gerrymander.beans.Party;
import com.gerrymander.beans.Result;
import com.gerrymander.beans.State;
import com.gerrymander.beans.Votes;
import com.gerrymander.service.ReportService;

public class ReliableWins {
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
	private double standardD;
	private ConsistentAdvantage consistentAdvantage;
	private EfficiencyGap efficiencyGap;
	
	public ReliableWins(){}
	
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

	public double getStandardD() {
		return standardD;
	}

	public void setStandardD(double standardD) {
		this.standardD = standardD;
	}

	public ConsistentAdvantage getConsistentAdvantage() {
		return consistentAdvantage;
	}

	public void setConsistentAdvantage(ConsistentAdvantage consistentAdvantage) {
		this.consistentAdvantage = consistentAdvantage;
	}

	public EfficiencyGap getEfficiencyGap() {
		return efficiencyGap;
	}
	
	public double calculateStandardD(double mean, List<Double> demoVotes){
		double squreSum = 0;
		for(Double demoVote: demoVotes) squreSum += Math.pow((demoVote - mean),2);
		squreSum /= demoVotes.size();
		squreSum = Math.sqrt(squreSum);
		return squreSum;
	}

	public void setEfficiencyGap(EfficiencyGap efficiencyGap) {
		this.efficiencyGap = efficiencyGap;
	}

	public void measure(List<Votes> votes, State state){
		this.efficiencyGap = new EfficiencyGap();
		if(this.efficiencyGap.getDemoVoteMargin() > 75 || this.efficiencyGap.getDemoVoteMargin() < 25 || state.getDemoSeats() == 0 || state.getRepubSeats() == 0){
			List<Double> demoWinVotes = new ArrayList<>();
			double winVoteSum = 0, mean = 0;
			for(Votes vote: votes){
				if(vote.getDemoVotes() > vote.getRepubVotes()){
					demoWinVotes.add(vote.getDemoVotes());
					winVoteSum += vote.getDemoVotes();
				}
				mean = winVoteSum / demoWinVotes.size();
				double standardDDemo = calculateStandardD(mean, demoWinVotes);
				this.pValue = 0.756*standardDDemo/Math.sqrt(votes.size());
			}
		}else{
			this.consistentAdvantage = new ConsistentAdvantage();
			this.consistentAdvantage.measure(votes, state);
		}
	}
}
