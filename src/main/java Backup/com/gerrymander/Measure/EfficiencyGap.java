package com.gerrymander.Measure;

import java.util.List;

import com.gerrymander.beans.Party;
import com.gerrymander.beans.Result;
import com.gerrymander.beans.State;
import com.gerrymander.beans.Votes;

public class EfficiencyGap {
	private double demoSeatMargin;
	private double demoVoteMargin;
	private double gap;
	private Result result;
	private Party party;
	
	public EfficiencyGap(){}
		
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
		this.demoSeatMargin = state.getDemoSeats() / votes.size();
		double repubVoteMargin = 0;
		for(Votes vote: votes){
			this.demoVoteMargin += vote.getDemoVotes();
			repubVoteMargin += vote.getRepubVotes();
		}
		this.gap = (this.demoSeatMargin - 0.5) - 2 * (this.demoVoteMargin - 0.5);
		this.result = (Math.abs(this.gap) < 0.08 ? Result.PASS : Result.FAIL);
		this.party = (this.gap > 0 ? Party.DEMOCRATIC : Party.REPUBLICAN);
		if(this.demoVoteMargin > 0.75 || repubVoteMargin > 0.75 || state.getDemoSeats() == 0 || state.getRepubSeats() == 0)
			this.result = Result.SKIP;
	}
}
