package com.gerrymander.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gerrymander.Measure.*;
import com.gerrymander.beans.*;
import com.gerrymander.constant.ControllerAttributes;
import com.gerrymander.repository.DistrictRepository;
import com.gerrymander.service.*;
import com.gerrymander.service.VotesService;
//import com.gerrymander.util.JsonUtil;


@Controller
public class GerrymanderController {
	
	private GeoService geoService;
	private VotesService votesService;
	private District District;
	private ReportService reportService;
	
	@Autowired
	public GerrymanderController(GeoService districtService, VotesService votesService, ReportService reportService) {
		this.geoService = districtService;
		this.votesService = votesService;
		this.reportService = reportService;
	}
	
	@RequestMapping(value="/greeting", method = RequestMethod.GET)
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

	@RequestMapping(value="/greeting", method = RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
        return "resulHello";
    }
	
	@ModelAttribute("yearList")
    public ArrayList<Integer> generateYearList()
    {
		ArrayList<Integer> list = new ArrayList<>();
		for(int year = ControllerAttributes.YEAR_TO; year >= ControllerAttributes.YEAR_FROM; year-=2) {
			list.add(year);
		}
        return list;
    }
	
	@ModelAttribute("measureList")
    public ArrayList<Measure> generateMeasureList()
    {
		ArrayList<Measure> list = new ArrayList<>();
		list.add(Measure.LOPSIDED_WINS);
		list.add(Measure.CONSISTENT_ADVANTAGE);
        return list;
    }
	
	@RequestMapping(value="/gerrymander", method = RequestMethod.GET)
	public String getGerrymanderPage(Model model) {
    		SelectBar selectBar = new SelectBar();
    		model.addAttribute("selectBar", selectBar);
//    		return new ModelAndView("redirect:/redirectedUrl", model);
    		return "gerrymander";
	}
	
	@RequestMapping(value="/gerrymander", method = RequestMethod.POST)
	public ModelAndView postGerrymanderPage(ModelAndView modelAndView, @ModelAttribute("selectBar") SelectBar selectBar){
		District district = geoService.findDistrict(selectBar.getStateName(), selectBar.getDistrictId(), selectBar.getYear());
		ResultPane resultPane = new ResultPane(district);
		State state = geoService.findState(selectBar.getStateName(), selectBar.getYear());
		LopsidedWins lop = new LopsidedWins(votesService, state, reportService);
		resultPane.setLopsidedWins(lop);
		List<Votes> votes = votesService.findVotes(selectBar.getStateName(), selectBar.getYear());
		ConsistentAdvantage con = new ConsistentAdvantage();
		con.measure(votes, state);
		resultPane.setConsistentAdvantage(con);
		EfficiencyGap gap = new EfficiencyGap();
		gap.measure(votes, state);
		resultPane.setEfficiencyGap(gap);
		modelAndView.addObject(resultPane);
		modelAndView.setViewName("gerrymanderResult");
		/*
		String jsonState  = JsonUtil.convertJavaToJson(state);
		String jsonDistrict  = JsonUtil.convertJavaToJson(district);
		System.out.println(jsonState);
		State javaState = JsonUtil.convertJsonToJava(jsonState, State.class);
		District javaDistrict = JsonUtil.convertJsonToJava(jsonDistrict, District.class);
		System.out.println("====================================");
		System.out.println(javaDistrict);
		
		try (FileWriter file = new FileWriter("src/main/resources/static/json/temp.json")) {
			file.write(jsonState);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return modelAndView;
	}
}
