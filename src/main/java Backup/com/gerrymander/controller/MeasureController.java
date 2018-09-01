package com.gerrymander.controller;

import com.gerrymander.Measure.LopsidedWins;
import com.gerrymander.beans.Result;
import com.gerrymander.beans.State;

public class MeasureController {
	private LopsidedWins lopsidedWins;
	
	public void measure(State state) {
		this.lopsidedWins.measure(state);
	}
	
	public LopsidedWins getLopsidedWins() {
		return lopsidedWins;
	}
}
