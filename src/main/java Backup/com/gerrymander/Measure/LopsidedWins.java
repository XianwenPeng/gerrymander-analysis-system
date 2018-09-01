package com.gerrymander.Measure;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.inference.*;

import com.gerrymander.beans.*;
import com.gerrymander.service.*;

public class LopsidedWins {
	private double pValue;
	private VotesService votesService;
	private ReportService reportService;
	private Result result;
	private Party gerryParty;
	private double demoWinAve;
	private double repubWinAve;
	private State state;
	private String lopsidedExplain;
	
	public LopsidedWins() {
		
	}
	public LopsidedWins(VotesService votesService, State state, ReportService reportService) {
		this.votesService = votesService;
		this.state = state;
		this.reportService = reportService;
		LopsidedWins lop = this;
		this.result = lop.measure(state);
	}
	public LopsidedWins(VotesService votesService) {
		this.votesService = votesService;
	}
	
	public Result measure(State state) {
		if(state.getDemoSeats() < 2 || state.getRepubSeats() < 2) {
			this.result = Result.SKIP;
			this.lopsidedExplain = reportService.generateLopsidedExplain(this);
			return this.result;
		}
		List<Double> demo = new ArrayList<>();
		List<Double> repub = new ArrayList<>();
		List<Votes> votes = votesService.findVotes(state.getStateName(), state.getYear());
		double demoSum = 0, repubSum = 0;
		for(Votes vote: votes) {
			if(vote.getDemoVotes() > vote.getRepubVotes()) {
				demo.add(vote.getDemoVotes());
				demoSum += vote.getDemoVotes();
			}
			else {
				repub.add(vote.getRepubVotes());
				repubSum += vote.getRepubVotes();
			}
		}
		TTest ttest = new TTest();
        this.pValue = ttest.homoscedasticTTest(listToArray(demo), listToArray(repub));
        if(this.pValue > 0.05) 
        		this.result = Result.PASS;
        else {
        		this.result = Result.FAIL;
        		this.demoWinAve = demoSum/state.getDemoSeats();
        		this.repubWinAve = repubSum/state.getRepubSeats();
        		if(demoWinAve > repubWinAve)
        			this.gerryParty = Party.DEMOCRATIC;
        		else
        			this.gerryParty = Party.REPUBLICAN;
        		this.lopsidedExplain = reportService.generateLopsidedExplain(this);
        }
        return this.result;
	}
	
	public double[] listToArray(List<Double> list) {
		double[] arr = new double[list.size()];
		for(int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}
	public double getPValue() {
		return pValue;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Result getResult() {
		return result;
	}
	public Party getGerryParty() {
		return gerryParty;
	}
	public double getDemoWinAve() {
		return demoWinAve;
	}
	public double getRepubWinAve() {
		return repubWinAve;
	}
	public State getState() {
		return state;
	}
	public String getLopsidedExplain() {
		return lopsidedExplain;

	}

}
