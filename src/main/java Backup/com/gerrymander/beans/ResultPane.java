package com.gerrymander.beans;

import com.gerrymander.Measure.*;

public class ResultPane {
	private District district;
	private LopsidedWins lopsidedWins;
	private ConsistentAdvantage consistentAdvantage;
	private EfficiencyGap efficiencyGap;
	
	public ResultPane() {
		
	}
	public ResultPane(District district) {
		this.district = district;
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
}
