package com.gerrymander.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gerrymander.beans.Activity;
import com.gerrymander.beans.State;
import com.gerrymander.beans.User;
import com.gerrymander.constant.ControllerAttributes;
import com.gerrymander.htmlObject.ExportPane;
import com.gerrymander.htmlObject.ManageUserObj;
import com.gerrymander.htmlObject.SelectBar;
import com.gerrymander.repository.UserRepository;
import com.gerrymander.service.DemographicService;
import com.gerrymander.service.GeoService;
import com.gerrymander.service.JsonService;
import com.gerrymander.service.UserService;
import com.gerrymander.service.VotesService;

@Controller
public class MenuController {
	private UserService userService;
	private GeoService geoService;
	private VotesService votesService;
	private DemographicService demographicService;
	
	public MenuController(UserService userService, GeoService geoService, VotesService votesService, DemographicService demographicService) {
		this.userService = userService;
		this.geoService = geoService;
		this.votesService = votesService;
		this.demographicService = demographicService;
	}
	@RequestMapping(value="/gerrymander/about", method = RequestMethod.GET)
	public ModelAndView showAccountPage(ModelAndView modelAndView, Model model, User user){		
		modelAndView.setViewName("about");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		return modelAndView;
	}
	@RequestMapping(value="/gerrymander/help", method = RequestMethod.GET)
	public ModelAndView showHelpPage(ModelAndView modelAndView, Model model, User user){		
		modelAndView.setViewName("help");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		return modelAndView;
	}
	@RequestMapping(value="/gerrymander/credit", method = RequestMethod.GET)
	public ModelAndView showCreditPage(ModelAndView modelAndView, Model model, User user){		
		modelAndView.setViewName("credit");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		return modelAndView;
	}
	@RequestMapping(value="/gerrymander/manage/exportResult", method = RequestMethod.GET)
	public ModelAndView showExportResultPage(ModelAndView modelAndView, Model model, User user){		
		modelAndView.setViewName("exportResult");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		ExportPane exportPane = new ExportPane();
		modelAndView.getModel().put("exportPane", exportPane);
		List<Integer> list = generateYearList();
		modelAndView.getModel().put("yearToList", list);
		modelAndView.getModel().put("yearFromList", list);
		modelAndView.getModel().put("yearList", list);
		return modelAndView;
	}
	@RequestMapping(value = "/gerrymander/manage/exportRst", method = RequestMethod.POST)
	public String postExportView(ModelAndView modelAndView, @ModelAttribute("exportPane") ExportPane exportPane) {
		JsonService jsonService = new JsonService(geoService, votesService, demographicService);
		if(exportPane.getYear() > 0)
			jsonService.writeAllDataJson(exportPane.getYear(), exportPane.getYearFileName());
		if(exportPane.getYearFrom() > 0 && exportPane.getYearTo() > 0)
			jsonService.writeAllDataJson(exportPane.getYearTo(), exportPane.getYearFrom(), exportPane.getYearsFileName());
		return "exportSucceed";
	}
	@RequestMapping(value="/gerrymander/manage/exportDemo", method = RequestMethod.GET)
	public ModelAndView showExportDemoPage(ModelAndView modelAndView, Model model, User user){		
		modelAndView.setViewName("exportDemo");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		ExportPane exportPane = new ExportPane();
		modelAndView.getModel().put("exportPane", exportPane);
		List<Integer> list = generateYearList();
		modelAndView.getModel().put("yearToList", list);
		modelAndView.getModel().put("yearFromList", list);
		modelAndView.getModel().put("yearList", list);
		return modelAndView;
	}
	@RequestMapping(value = "/gerrymander/manage/exportDemo", method = RequestMethod.POST)
	public String postExportDemoView(ModelAndView modelAndView, @ModelAttribute("exportPane") ExportPane exportPane) {
		JsonService jsonService = new JsonService(geoService, votesService, demographicService);
		if(exportPane.getYear() > 0)
			jsonService.writeAllDemosJson(exportPane.getYear(), exportPane.getYearFileName());
		if(exportPane.getYearFrom() > 0 && exportPane.getYearTo() > 0)
			jsonService.writeAllDemosJson(exportPane.getYearTo(), exportPane.getYearFrom(), exportPane.getYearsFileName());
		return "exportSucceedDemo";
	}
	@RequestMapping(value="/gerrymander/manage", method = RequestMethod.GET)
	public ModelAndView showManagePage(ModelAndView modelAndView, Model model, User user){		
		modelAndView.setViewName("manage");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		modelAndView.getModel().put("usersList", userService.findAll());
		return modelAndView;
	}
	
	@RequestMapping(value="/gerrymander/logout")
	public ModelAndView postManageUserPage(ModelAndView modelAndView, User user){		
		modelAndView.setViewName("redirect:/gerrymander/login");
		User loggedIn = userService.findByStatus("online");
		loggedIn.setStatus("offline");
		userService.saveUser(loggedIn);
		return modelAndView;
	}
	
	
	public String getCurrentTimeUsingDate() {
        Date date = new Date();
        String strDateFormat = ("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        	return formattedDate;
    }
	
	@RequestMapping(value="/gerrymander/lopsidedResult", method = RequestMethod.GET)
	public ModelAndView showUploadDemoPage(ModelAndView modelAndView, Model model, User user){		
		modelAndView.setViewName("lopsidedResult");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
//		ExportPane exportPane = new ExportPane();
//		modelAndView.getModel().put("exportPane", exportPane);
//		modelAndView.getModel().put("usersList", userService.findAll());
		return modelAndView;
	}
	
	public List<Integer> generateYearList()
    {
		Map<Integer, Integer> map = new HashMap<>();
		for(State state: geoService.findAllState()) {
			if(map.containsKey(state.getYear())) {
				map.put(state.getYear(), map.get(state.getYear()) + 1);
			}
			else
				map.put(state.getYear(), 1);
		}
		ArrayList<Integer> list = new ArrayList<>();
		for(int year: map.keySet()) {
			list.add(year);
		}
		Collections.sort(list);
		Collections.reverse(list);
        return list;
    }
	
	
}
