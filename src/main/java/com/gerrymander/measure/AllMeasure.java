package com.gerrymander.measure;

import java.util.List;

import com.gerrymander.beans.*;
import com.gerrymander.service.*;

public class AllMeasure {
	private LopsidedWins lopsidedWins;
	private ConsistentAdvantage consistentAdvantage;
	private EfficiencyGap efficiencyGap;
	private ExcessSeats excessSeats;
	private ReliableWins reliableWins;
	private int failed;

	public AllMeasure(){}
	
	public ReliableWins getReliableWins() {
		return reliableWins;
	}

	public void setReliableWins(ReliableWins reliableWins) {
		this.reliableWins = reliableWins;
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}

	public LopsidedWins getLopsidedWins() {
		return lopsidedWins;
	}

	public void setLopsidedWins(LopsidedWins lopsidedWins) {
		this.lopsidedWins = lopsidedWins;
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

	public void setEfficiencyGap(EfficiencyGap efficiencyGap) {
		this.efficiencyGap = efficiencyGap;
	}

	public ExcessSeats getExcessSeats() {
		return excessSeats;
	}

	public void setExcessSeats(ExcessSeats excessSeats) {
		this.excessSeats = excessSeats;
	}

	public void measure(List<Votes> votes, List<District> districts, State state, List<Votes> totalVotes){
		this.lopsidedWins = new LopsidedWins();
		this.lopsidedWins.measure(districts, state);
		this.consistentAdvantage = new ConsistentAdvantage();
		this.consistentAdvantage.measure(votes, state);
		this.efficiencyGap = new EfficiencyGap();
		this.efficiencyGap.measure(votes, state);
		this.excessSeats = new ExcessSeats();
		this.excessSeats.measure(votes,totalVotes,state);
		this.failed = countFailed(this);
//		this.reliableWins = new ReliableWins();
//		this.reliableWins.measure(votes, state);
	}
	public int countFailed(AllMeasure allmeasure) {
		int failed = 0;
		if(allmeasure.getLopsidedWins().getResult() == Result.FAIL)
			failed++;
		if(allmeasure.getEfficiencyGap().getResult() == Result.FAIL)
			failed++;
		if(allmeasure.getConsistentAdvantage().getResult() == Result.FAIL)
			failed++;
		if(allmeasure.getExcessSeats().getResult() == Result.FAIL)
			failed++;
		return failed;
	}
}
