package com.gerrymander.measure;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.inference.*;

import com.gerrymander.beans.*;
import com.gerrymander.service.*;

public class LopsidedWins {
	private double pValue;
	private VotesService votesService;
	private Result result;
	private Party gerryParty;
	private double demoWinAve;
	private double repubWinAve;
	private State state;
	private String lopsidedExplain;
	
	public LopsidedWins() {
		
	}
//	public LopsidedWins(VotesService votesService, State state, ReportService reportService) {
//		this.votesService = votesService;
//		this.state = state;
//		this.reportService = reportService;
//		LopsidedWins lop = this;
//		this.result = lop.measure(state);
//	}
//	public LopsidedWins(VotesService votesService) {
//		this.votesService = votesService;
//	}
	
	public Result measure(List<District> districts, State state) {
		ReportService reportService = new ReportService();
		this.state = state;
		List<Double> demo = new ArrayList<>();
		List<Double> repub = new ArrayList<>();
//		List<Votes> votes = votesService.findVotes(state.getStateName(), state.getYear());
		double demoSum = 0, repubSum = 0;
		for(District district: districts) {
			if(district.getDemoRatio() > district.getRepubRatio()) {
				demo.add(district.getDemoRatio());
				demoSum += district.getDemoRatio();
			}
			else {
				repub.add(district.getRepubRatio());
				repubSum += district.getRepubRatio();
			}

		}
		if(demo.size() < 2 || repub.size() < 2) {
			this.result = Result.SKIP;
			this.lopsidedExplain = reportService.generateLopsidedExplain(this);
			return this.result;
		}
		TTest ttest = new TTest();
        this.pValue = ttest.homoscedasticTTest(listToArray(demo), listToArray(repub));
        this.demoWinAve = demoSum/state.getDemoSeats();
		this.repubWinAve = repubSum/state.getRepubSeats();
        if(this.pValue > 0.05){
        		this.result = Result.PASS;
        		this.lopsidedExplain = reportService.generateLopsidedExplain(this);
        }
        else {
        		this.result = Result.FAIL;
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

	public double getpValue() {
		return pValue;
	}

	public void setpValue(double pValue) {
		this.pValue = pValue;
	}

	public VotesService getVotesService() {
		return votesService;
	}

	public void setVotesService(VotesService votesService) {
		this.votesService = votesService;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Party getGerryParty() {
		return gerryParty;
	}

	public void setGerryParty(Party gerryParty) {
		this.gerryParty = gerryParty;
	}

	public double getDemoWinAve() {
		return demoWinAve;
	}

	public void setDemoWinAve(double demoWinAve) {
		this.demoWinAve = demoWinAve;
	}

	public double getRepubWinAve() {
		return repubWinAve;
	}

	public void setRepubWinAve(double repubWinAve) {
		this.repubWinAve = repubWinAve;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getLopsidedExplain() {
		return lopsidedExplain;
	}

	public void setLopsidedExplain(String lopsidedExplain) {
		this.lopsidedExplain = lopsidedExplain;
	}
	

}
