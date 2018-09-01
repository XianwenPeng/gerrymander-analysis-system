package com.gerrymander.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.gerrymander.constant.ControllerAttributes;
import com.gerrymander.htmlObject.ExportPane;
import com.gerrymander.htmlObject.SelectBar;
import com.gerrymander.service.DemographicService;
import com.gerrymander.service.GeoService;
import com.gerrymander.service.JsonService;
import com.gerrymander.service.ReportService;
import com.gerrymander.service.VotesService;

import org.springframework.ui.Model;


@Controller
public class ExportController {
	
	private GeoService geoService;
	private VotesService votesService;
	private DemographicService demoService;
	
	@Autowired
	public ExportController(GeoService geoService, VotesService votesService, DemographicService demoService) {
		this.geoService = geoService;
		this.votesService = votesService;
		this.demoService = demoService;
	}
	
	@RequestMapping(value = "/gerrymander/export", method = RequestMethod.GET)
	public String getExportView(Model model) {
		ExportPane exportPane = new ExportPane();
		model.addAttribute("exportPane", exportPane);
		model.addAttribute("yearToList", generateYearList());
		model.addAttribute("yearFromList", generateYearList());
		model.addAttribute("yearList", generateYearList());
		return "export";
	}
	
	@RequestMapping(value = "/gerrymander/export", method = RequestMethod.POST)
	public String postExportView(ModelAndView modelAndView, @ModelAttribute("exportPane") ExportPane exportPane) {
		JsonService jsonService = new JsonService(geoService, votesService, demoService);
		if(exportPane.getYear() > 0)
			jsonService.writeAllDataJson(exportPane.getYear(), exportPane.getYearFileName());
		if(exportPane.getYearFrom() > 0 && exportPane.getYearTo() > 0)
			jsonService.writeAllDataJson(exportPane.getYearTo(), exportPane.getYearFrom(), exportPane.getYearsFileName());
		return "exportSucceed";
	}
	
    public ArrayList<Integer> generateYearList()
    {
		ArrayList<Integer> list = new ArrayList<>();
		for(int year = ControllerAttributes.YEAR_TO; year >= ControllerAttributes.YEAR_FROM; year-=2) {
			list.add(year);
		}
        return list;
    }
}
