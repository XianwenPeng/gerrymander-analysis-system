package com.gerrymander.htmlObject;

import com.gerrymander.beans.*;
import com.gerrymander.measure.*;

public class ResultPane {
	private District district;
	private State state;
	private LopsidedWins lopsidedWins;
	private ConsistentAdvantage consistentAdvantage;
	private EfficiencyGap efficiencyGap;
	private ExcessSeats excessSeats;
	private AllMeasure allMeasure;
	
	
	public ResultPane() {
		
	}
	public ResultPane(District district) {
		this.district = district;
	}
	public ResultPane(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public void setDistrict(District district) {
		this.district = district;
	}
	public District getDistrict() {
		return district;
	}
	public void setLopsidedWins(LopsidedWins lopsidedWins) {
		this.lopsidedWins = lopsidedWins;
	}
	public LopsidedWins getLopsidedWins() {
		return lopsidedWins;
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
	public AllMeasure getAllMeasure() {
		return allMeasure;
	}
	public void setAllMeasure(AllMeasure allMeasure) {
		this.allMeasure = allMeasure;
	}
}
