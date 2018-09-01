package com.gerrymander.measure;

import java.util.List;

import com.gerrymander.beans.Party;
import com.gerrymander.beans.Result;
import com.gerrymander.beans.State;
import com.gerrymander.beans.Votes;
import com.gerrymander.service.ReportService;

public class EfficiencyGap {
	private double demoSeatMargin;
	private double demoVoteMargin;
	private double gap;
	private State state;
	private Result result;
	private Party party;
	private String efficiencyExplain;
	
	public EfficiencyGap(){}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getEfficiencyExplain() {
		return efficiencyExplain;
	}

	public void setEfficiencyExplain(String efficiencyExplain) {
		this.efficiencyExplain = efficiencyExplain;
	}

	public double getDemoSeatMargin() {
		return demoSeatMargin;
	}

	public void setDemoSeatMargin(double demoSeatMargin) {
		this.demoSeatMargin = demoSeatMargin;
	}

	public double getDemoVoteMargin() {
		return demoVoteMargin;
	}

	public void setDemoVoteMargin(double demoVoteMargin) {
		this.demoVoteMargin = demoVoteMargin;
	}

	public double getGap() {
		return gap;
	}

	public void setGap(double gap) {
		this.gap = gap;
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
	
	public void measure(List<Votes> votes, State state){
		this.state = state;
		ReportService reportService = new ReportService();
		this.demoSeatMargin = (this.state.getDemoSeats() / (double)votes.size())*100;
		double repubVoteMargin = 0, demoVotes = 0, repubVotes = 0;
		for(Votes vote: votes){
			demoVotes += vote.getDemoVotes();
			repubVotes += vote.getRepubVotes();
		}
		repubVoteMargin = repubVotes / (repubVotes + demoVotes)*100;
		this.demoVoteMargin = demoVotes / (repubVotes + demoVotes)*100;
		this.gap = (this.demoSeatMargin - 50) - 2 * (this.demoVoteMargin - 50);
		this.result = (Math.abs(this.gap) < 8 ? Result.PASS : Result.FAIL);
		this.party = (this.gap > 0 ? Party.DEMOCRATIC : Party.REPUBLICAN);
		if(this.demoVoteMargin > 75 || repubVoteMargin > 75 || state.getDemoSeats() == 0 || state.getRepubSeats() == 0)
			this.result = Result.SKIP;
		this.efficiencyExplain = reportService.generateEfficiencyExplain(this);
	}
}
