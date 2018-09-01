package com.gerrymander.measure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.stat.inference.TTest;

import com.gerrymander.beans.Party;
import com.gerrymander.beans.Result;
import com.gerrymander.beans.State;
import com.gerrymander.beans.Votes;
import com.gerrymander.service.ReportService;

public class ExcessSeats {
	private double demoSeats;
	private double simuDemoSeats;
	private double difference;
	private State state;
	private Result result;
	private Party party;
	private List<Double> demoVotes;
	private double demoTotalVotes;
	private double pValue;
	private ReportService reportService;
	private String excessExplain;
	private int demoWinSeats;
	private int totalSeats;
	private double demoStateVotes;
	
	public ExcessSeats(){}

	public double getDemoSeats() {
		return demoSeats;
	}

	public void setDemoSeats(double demoSeats) {
		this.demoSeats = demoSeats;
	}

	public double getSimuDemoSeats() {
		return simuDemoSeats;
	}

	public void setSimuDemoSeats(double simuDemoSeats) {
		this.simuDemoSeats = simuDemoSeats;
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

	public double getDemoTotalVotes() {
		return demoTotalVotes;
	}

	public void setDemoTotalVotes(double demoTotalVotes) {
		this.demoTotalVotes = demoTotalVotes;
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

	public String getExcessExplain() {
		return excessExplain;
	}

	public void setExcessExplain(String excessExplain) {
		this.excessExplain = excessExplain;
	}
	
	
	public int getDemoWinSeats() {
		return demoWinSeats;
	}

	public void setDemoWinSeats(int demoWinSeats) {
		this.demoWinSeats = demoWinSeats;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	
	public double getDemoStateVotes() {
		return demoStateVotes;
	}

	public void setDemoStateVotes(double demoStateVotes) {
		this.demoStateVotes = demoStateVotes;
	}

	public double[] listToArray(List<Double> list) {
		double[] arr = new double[list.size()];
		for(int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}

	public void measure(List<Votes> votes, List<Votes> totalVotes, State state){
		TTest ttest = new TTest();
		this.state = state;
		this.demoVotes = new ArrayList<>();
		this.reportService = new ReportService();
		this.demoTotalVotes = 0;
		this.demoWinSeats = 0;
		this.totalSeats = votes.size();
		this.demoStateVotes = 0;
		for(Votes vote: votes){
			this.demoVotes.add(vote.getDemoVotes()/(vote.getDemoVotes()+vote.getRepubVotes())*100);
			this.demoStateVotes += vote.getDemoVotes()/(vote.getDemoVotes()+vote.getRepubVotes())*100;
			if(vote.getDemoVotes()/(vote.getDemoVotes()+vote.getRepubVotes())*100 > 50) this.demoWinSeats += 1;
		}
		this.demoStateVotes = this.demoStateVotes / this.totalSeats;
		for(Votes vote: totalVotes) this.demoTotalVotes += vote.getDemoVotes()/(vote.getDemoVotes()+vote.getRepubVotes())*100;
		this.demoTotalVotes = this.demoTotalVotes / totalVotes.size();
		Collections.sort(demoVotes);
		this.simuDemoSeats = this.totalSeats * this.demoTotalVotes/100;
		this.difference = this.simuDemoSeats - this.demoWinSeats;
		this.party = (this.difference < 0 ? Party.DEMOCRATIC : Party.REPUBLICAN);
		if(state.getRepubSeats() == 0 || state.getDemoSeats() == 0 || this.demoVotes.size() < 2){
			this.result = Result.SKIP;
			this.excessExplain = this.reportService.generateExcessExplain(this);
			return;
		}
//		this.pValue = Math.abs(this.difference)/calculateStandardE(this.mean,this.demoVotes)*0.5708;
		double mu = this.demoStateVotes + 2* this.difference;
		this.pValue = ttest.tTest(mu, listToArray(this.demoVotes));
		this.result = (this.pValue < 0.05 ? Result.FAIL : Result.PASS);
		this.excessExplain = this.reportService.generateExcessExplain(this);
	}
}
