package com.gerrymander.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gerrymander.beans.District;
import com.gerrymander.beans.Greeting;
import com.gerrymander.beans.SelectBar;
import com.gerrymander.constant.ControllerAttributes;
import com.gerrymander.repository.DistrictRepository;
import com.gerrymander.service.GeoService;

@Controller
public class GerrymanderController {
	
	private GeoService geoService;
	private District District;
	
	@Autowired
	public GerrymanderController(GeoService districtService) {
		this.geoService = districtService;
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
	
	@RequestMapping(value="/gerrymander", method = RequestMethod.GET)
	public String getGerrymanderPage(Model model) {
    		SelectBar selectBar = new SelectBar();
    		model.addAttribute("selectBar", selectBar);
    		return "gerrymander";
	}
	
	@RequestMapping(value="/gerrymander", method = RequestMethod.POST)
	public ModelAndView postGerrymanderPage(ModelAndView modelAndView, @ModelAttribute("selectBar") SelectBar selectBar){
		District district = geoService.findDistrict(selectBar.getStateName(), selectBar.getDistrictId(), selectBar.getYear());
		modelAndView.addObject(district);
		modelAndView.setViewName("gerrymanderResult");
		return modelAndView;
	}
	
}
