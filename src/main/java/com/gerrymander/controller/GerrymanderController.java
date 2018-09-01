package com.gerrymander.controller;

import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.gerrymander.beans.*;
import com.gerrymander.constant.ControllerAttributes;
import com.gerrymander.htmlObject.*;
import com.gerrymander.htmlObject.PieChartPage;
import com.gerrymander.htmlObject.ResultPane;
import com.gerrymander.htmlObject.SelectBar;
import com.gerrymander.jsonBeans.AllDataJson;
import com.gerrymander.jsonBeans.StateColor;
import com.gerrymander.measure.*;
import com.gerrymander.service.*;

import javafx.scene.chart.PieChart;

@Controller
public class GerrymanderController {
	
	private GeoService geoService;
	private VotesService votesService;
	private ReportService reportService;
	private AccountController accountController;
	private DemographicService demoService;
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private WorkService workService;
	
	@Autowired
	private UserService userService;
	
//	private Country country;
	
	@Autowired
	public GerrymanderController(GeoService geoService, VotesService votesService, 
			ReportService reportService, AccountController accountController, UserService userService,
			DemographicService demoService, WorkService workService, ActivityService activityService) {
		this.geoService = geoService;
		this.votesService = votesService;
		this.reportService = reportService;
		this.accountController = accountController;
		this.userService = userService;
		this.demoService = demoService;
		this.workService = workService;
		this.activityService = activityService;
	}
	
	public GerrymanderController() {
		// TODO Auto-generated constructor stub
	}
	
	
	@RequestMapping(value="/greeting")
    public ModelAndView greetingForm(ModelAndView modelAndView) throws IOException {

//		modelAndView.getModel().put("maleNum", 345);
//		modelAndView.getModel().put("femaleNum", 456);
//		modelAndView.setViewName("demoPieFrag");

		int maleNum = demoService.findByStateNameAndYear("AL", 2016).getMale();
		int femaleNum = demoService.findByStateNameAndYear("AL", 2016).getFemale();
		modelAndView.getModel().put("maleNum", maleNum);
		modelAndView.getModel().put("femaleNum", femaleNum);
		modelAndView.setViewName("demoPieFrag");
        return modelAndView;
    }

	@RequestMapping(value="/greeting", method = RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
        return "resulHello";
    }
	
	@ModelAttribute("yearList")
    public ArrayList<Integer> generateYearList()
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
	
	@ModelAttribute("userAdmin")
    public User getOnlineUser()
    {
		User loggedIn = userService.findByStatus("online");
	    return loggedIn;
    }
	
	@ModelAttribute("measureList")
    public ArrayList<Measure> generateMeasureList()
    {
		ArrayList<Measure> list = new ArrayList<>();
		list.add(Measure.LOPSIDED_WINS);
		list.add(Measure.CONSISTENT_ADVANTAGE);
		list.add(Measure.EFFICIENCY_GAP);
//		list.add(Measure.RELIABLE_WINS);
		list.add(Measure.EXCESS_SEATS);
		list.add(Measure.ALLMEASURE);
        return list;
    }
	
	@RequestMapping(value = "/save_json")
	public String saveJson(Model model) {
		JsonService jsonService = new JsonService(geoService, votesService, demoService);
		jsonService.writeTestResultJson();
//		jsonService.writeStatePaneDataJson(2016, 2014, "demoData");
		
//		jsonService.writeAllVotesJson(2012, "votes2012");
//		jsonService.writeAllVotesJson(2014, "votes2014");
//		jsonService.writeAllVotesJson(2016, "votes2016");
//		jsonService.writeAllVotesJson(2016, 2012, "votes161412");
//		jsonService.writeElectionDataToJson();
//		jsonService.writeFormatedJsonForColor();
		SelectBar selectBar = new SelectBar();
	    model.addAttribute("selectBar", selectBar);
		return "redirect:/gerrymander";
	}
	@RequestMapping(value = "/read_votes")
	public String readJson(Model model) {
		JsonService jsonService = new JsonService(geoService, votesService, demoService);
//		jsonService.readVotesJson("exportJson");
		
//		for(Votes vote: votesService.findAllVotes()) {
//			if(vote.getYear() == 2008) {
//				vote.setYear(2014);
//				votesService.saveVotes(vote);
//			}
//		}
	
//		jsonService.writeElectionDataToJson();
//		jsonService.writeFormatedJsonForColor();
		SelectBar selectBar = new SelectBar();
	    model.addAttribute("selectBar", selectBar);
		return "redirect:/gerrymander";
	}
	
	@RequestMapping(value="/gerrymander/manage/uploadVotes", method = RequestMethod.GET)
	public ModelAndView showUploadVotesPage(ModelAndView modelAndView, Model model, User user){		
		modelAndView.setViewName("uploadVotesPage");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		ExportPane exportPane = new ExportPane();
		modelAndView.getModel().put("exportPane", exportPane);
//		modelAndView.getModel().put("usersList", userService.findAll());
		return modelAndView;
	}
	
	@RequestMapping(value="/gerrymander/manage/uploadVotes", method = RequestMethod.POST)
	public ModelAndView postUploadVotesPage(ModelAndView modelAndView, Model model, @ModelAttribute("exportPane") ExportPane exportPane){		
		modelAndView.setViewName("redirect:/gerrymander/manage");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		
		JsonService jsonService = new JsonService(geoService, votesService, demoService);
		jsonService.readVotesJson(exportPane.getYear(), exportPane.getYearsFileName());
		
//		jsonService.writeElectionDataToJson();
//		jsonService.writeFormatedJsonForColor();
		SelectBar selectBar = new SelectBar();
	    model.addAttribute("selectBar", selectBar);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/gerrymander/manage/uploadDemo", method = RequestMethod.GET)
	public ModelAndView showUploadDemoPage(ModelAndView modelAndView, Model model, User user){		
		modelAndView.setViewName("uploadDemoPage");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		ExportPane exportPane = new ExportPane();
		modelAndView.getModel().put("exportPane", exportPane);
//		modelAndView.getModel().put("usersList", userService.findAll());
		return modelAndView;
	}
	
	@RequestMapping(value="/gerrymander/manage/uploadDemo", method = RequestMethod.POST)
	public ModelAndView postUploadDemoPage(ModelAndView modelAndView, Model model, @ModelAttribute("exportPane") ExportPane exportPane){		
		modelAndView.setViewName("redirect:/gerrymander");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		
		JsonService jsonService = new JsonService(geoService, votesService, demoService);
		jsonService.readDemographicJson(exportPane.getYear(), exportPane.getYearsFileName());
//		jsonService.writeElectionDataToJson();
//		jsonService.writeFormatedJsonForColor();
		SelectBar selectBar = new SelectBar();
	    model.addAttribute("selectBar", selectBar);
		
		return modelAndView;
	}
	
	
	@RequestMapping(value="/gerrymander", method = RequestMethod.GET)
	public String getGerrymanderPage(Model model) {
		JsonService jsonService = new JsonService(geoService, votesService, demoService);
//		jsonService.writeElectionDataToJson();
//		jsonService.writeFormatedJsonForColor();
		jsonService.writeTestResultJson();
		
		User loggedIn = userService.findByStatus("online");
	    if(loggedIn == null)
	    		return "redirect:/gerrymander/login";
		SelectBar selectBar = new SelectBar();
		SuperDistrictHtml sp = new SuperDistrictHtml();
	    model.addAttribute("selectBar", selectBar);
	    model.addAttribute("superDistrictHtml", sp);
	    model.addAttribute("saveWork", new Work());
	    model.addAttribute("viewWork", new Work());
	    model.addAttribute("workNameList", produceWorkNameList(loggedIn));
	    
//	    int maleNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getMale();
//		int femaleNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getFemale();
//		int whiteNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getWhite();
//		int blackNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getBlack();
//		int asianNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getAsian();
//		int hispanicNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getHispanic();
//
//		model.addAttribute("maleNum", maleNum);
//		model.addAttribute("femaleNum", femaleNum);
//		model.addAttribute("whiteNum", whiteNum);
//		model.addAttribute("blackNum", blackNum);
//		model.addAttribute("asianNum", asianNum);
//		model.addAttribute("hispanicNum", hispanicNum);
	    //      return "redirect:/gerrymandering";
	    return "gerrymander";
	}
	
	@RequestMapping(value="/gerrymander", method = RequestMethod.POST)
	public ModelAndView postGerrymanderPage(ModelAndView modelAndView, Model model, @ModelAttribute("saveWork") Work saveWork, 
			@ModelAttribute("viewWork") Work viewWork,
			@ModelAttribute("selectBar") SelectBar selectBar,
			@ModelAttribute("superDistrictHtml") SuperDistrictHtml superDistrictHtml) {
		JsonService jsonService = new JsonService(geoService, votesService, demoService);
//		jsonService.writeElectionDataToJson();
//		jsonService.writeFormatedJsonForColor();
//		jsonService.writeStatePaneDataJson("testResult");
		
		User loggedIn = userService.findByStatus("online");
	    if(loggedIn == null) {
	    		modelAndView.setViewName("redirect:/gerrymander/login");
	    		return modelAndView;
	    }
	    SuperDistrictHtml sp = new SuperDistrictHtml();
	    modelAndView.setViewName("gerrymander");

		if(!saveWork.getWorkName().equals("")) {
			List<WorkItem> workItem = workService.findWorkItemByEmail(loggedIn.getEmail());
			WorkItem item = workItem.get(workItem.size()-1);
			Work work = new Work();
			work.setId(workService.findAllWork().size());
			work.setEmail(item.getEmail());
			work.setWorkName(saveWork.getWorkName());
			work.setWorkItemId(item.getId());
			workService.save(work);
			Activity activity = new Activity(loggedIn.getEmail(), "Saved new work");
		    activity.setTime(getCurrentTimeUsingDate());
		    activityService.saveActivity(activity);
		}
	    
	    SelectBar selectBar1 = new SelectBar();
		model.addAttribute("selectBar", selectBar1);
	    model.addAttribute("superDistrictHtml", sp);
		

	    
		if(!viewWork.getWorkName().equals("Select Record")) {
			List<Work> work = workService.findByEmailAndWorkName(loggedIn.getEmail(), viewWork.getWorkName());
			WorkItem workItem = workService.findWorkItemById(work.get(work.size()-1).getWorkItemId());
			int year = workItem.getYear();
						
			superDistrictHtml.setSelected(workItem.getDistrict());
			superDistrictHtml.setSelectYear(workItem.getYear()+"");
			superDistrictHtml.setSelectStateName(workItem.getState());
			
			State state = geoService.findState(workItem.getState(), year);
			List<Votes> votes = votesService.findVotes(workItem.getState(), year);
			List<District> districts = geoService.findAllDistrict(workItem.getState(), year);
			String selected = workItem.getDistrict();
			
			System.out.println("xxxxxxx"+ year+viewWork.getWorkName());

			districts = modifySelected(state, districts, votes, selected);
			modelAndView.setViewName("redirect:/gerrymander");
			
			Activity activity = new Activity(loggedIn.getEmail(), "Viewd previous work");
		    activity.setTime(getCurrentTimeUsingDate());
		    activityService.saveActivity(activity);
						
			jsonService.writeSuperDistrictJsonForColor(districts, votes, state);
//			modelAndView = getSuperDistrictPage(modelAndView, model, selectBar, superDistrictHtml);
		}
		model.addAttribute("saveWork", new Work());
	    model.addAttribute("viewWork", new Work());
	    model.addAttribute("workNameList", produceWorkNameList(loggedIn));
//		modelAndView.setViewName("redirect:/gerrymander/manage");
//	      return "redirect:/gerrymandering";
	    return modelAndView;
	}
	
	public List<String> produceWorkNameList(User user){
		List<String> list = new ArrayList<>();
	    list.add("Select Record");
	    Map<String, Integer> map = new HashMap<>();
	    for(Work work:  workService.findByEmail(user.getEmail())) {
	    		if(!map.containsKey(work.getWorkName())) {
	    			map.put(work.getWorkName(), 1);
	    			list.add(work.getWorkName());
	    		}
	    }
	    return list;
	}
	
//	@RequestMapping(value="/gerrymander/measureResult")
//	public ModelAndView postLopsidedResult(ModelAndView modelAndView, Model model, @ModelAttribute("selectBar") SelectBar selectBar) {
//		LopsidedWins lop = new LopsidedWins();
//		
//		System.out.println("***********************************");
//		System.out.println(resultPane.getState().getStateName());
//		System.out.println("***********************************");
//
//		modelAndView.setViewName("lopsidedResult");
//		return modelAndView;
//	}
	@RequestMapping(value="/gerrymander/demoData")
	public ModelAndView postDemoPage(ModelAndView modelAndView, Model model, @ModelAttribute("selectBar") SelectBar selectBar){		
		System.out.println(selectBar.getStateName());
		
		int maleNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getMale();
		int femaleNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getFemale();
		int whiteNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getWhite();
		int blackNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getBlack();
		int asianNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getAsian();
		int hispanicNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getHispanic();

		model.addAttribute("maleNum", maleNum);
		model.addAttribute("femaleNum", femaleNum);
		model.addAttribute("whiteNum", whiteNum);
		model.addAttribute("blackNum", blackNum);
		model.addAttribute("asianNum", asianNum);
		model.addAttribute("hispanicNum", hispanicNum);
		
		State state = geoService.findState(selectBar.getStateName(), selectBar.getYear());
		model.addAttribute("state", state);
		
		List<District> districts = geoService.findAllDistrict(selectBar.getStateName(), selectBar.getYear());
		model.addAttribute("districtList", districts);
		SelectBar selectBar1 = new SelectBar();
		model.addAttribute("selectBar", selectBar1);
		modelAndView.setViewName("demoDataPage");
		return modelAndView;
	}
 
	@RequestMapping(value="/gerrymander/measureResult")
	public ModelAndView postGerrymanderPage(ModelAndView modelAndView, Model model, @ModelAttribute("selectBar") SelectBar selectBar){
		State state = geoService.findState(selectBar.getStateName(), selectBar.getYear());
		ResultPane resultPane = new ResultPane(state);
		List<Votes> votes = votesService.findVotes(selectBar.getStateName(), selectBar.getYear());
		List<Votes> totalVotes = votesService.findAllVotes();
		List<District> districts = geoService.findAllDistrict(selectBar.getStateName(), selectBar.getYear());
		if(selectBar.getMeasure() == Measure.LOPSIDED_WINS){
			LopsidedWins lop = new LopsidedWins();
			lop.measure(districts, state);
			resultPane.setLopsidedWins(lop);
			modelAndView.addObject(resultPane);
			modelAndView.setViewName("lopsidedResult");
		}else if(selectBar.getMeasure() == Measure.CONSISTENT_ADVANTAGE){
			ConsistentAdvantage con = new ConsistentAdvantage();
			con.measure(votes, state);
			resultPane.setConsistentAdvantage(con);
			modelAndView.addObject(resultPane);
			modelAndView.setViewName("consistentResult");
		}else if(selectBar.getMeasure() == Measure.EFFICIENCY_GAP){
			EfficiencyGap gap = new EfficiencyGap();
			gap.measure(votes, state);
			resultPane.setEfficiencyGap(gap);
			modelAndView.addObject(resultPane);
			modelAndView.setViewName("efficiencyResult");
		}else if(selectBar.getMeasure() == Measure.ALLMEASURE){
			AllMeasure all = new AllMeasure();
			all.measure(votes, districts, state, totalVotes);
			resultPane.setAllMeasure(all);
			modelAndView.addObject(resultPane);
			modelAndView.setViewName("gerrymanderResult");
		}else if(selectBar.getMeasure() == Measure.EXCESS_SEATS){
			ExcessSeats excessSeats = new ExcessSeats();
			excessSeats.measure(votes, totalVotes, state);
			resultPane.setExcessSeats(excessSeats);
			modelAndView.addObject(resultPane);
			modelAndView.setViewName("excessResult");
		}
		
		int maleNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getMale();
		int femaleNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getFemale();
		int whiteNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getWhite();
		int blackNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getBlack();
		int asianNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getAsian();
		int hispanicNum = demoService.findByStateNameAndYear(selectBar.getStateName(), selectBar.getYear()).getHispanic();

		model.addAttribute("maleNum", maleNum);
		model.addAttribute("femaleNum", femaleNum);
		model.addAttribute("whiteNum", whiteNum);
		model.addAttribute("blackNum", blackNum);
		model.addAttribute("asianNum", asianNum);
		model.addAttribute("hispanicNum", hispanicNum);
		
		model.addAttribute("districtList", districts);
		return modelAndView;
	}
	
	@RequestMapping(value="/gerrymander/superDistrict", method = RequestMethod.POST)
	public ModelAndView getSuperDistrictPage(ModelAndView modelAndView, Model model, @ModelAttribute("selectBar") SelectBar selectBar,
			@ModelAttribute("superDistrictHtml") SuperDistrictHtml superDistrictHtml){	
		System.out.println(superDistrictHtml.getSelectStateName()+superDistrictHtml.getSelected()+((superDistrictHtml==null)?1:0));
		if(superDistrictHtml.getSelectYear().equals("")) {
			modelAndView.setViewName("redirect:/gerrymander");
			return modelAndView;
		}
		
		User loggedIn = userService.findByStatus("online");
		System.out.println((workService == null) ? 1 : 0);
		WorkItem work = new WorkItem();
		
		int year = Integer.parseInt(superDistrictHtml.getSelectYear());
		work.setId(workService.findAllWorkItem().size());
		work.setEmail(loggedIn.getEmail());
		work.setState(superDistrictHtml.getSelectStateName());
		work.setYear(year);
		work.setDistrict(superDistrictHtml.getSelected());
		workService.saveWorkItem(work);
		
//		District district = geoService.findDistrict(selectBar.getStateName(), selectBar.getDistrictId(), selectBar.getYear());
//		superDistrictHtml.setSelectStateName("NC");
//		superDistrictHtml.setSelectYear(2016);
		State state = geoService.findState(superDistrictHtml.getSelectStateName(), year);
		ResultPane resultPane = new ResultPane(state);
		List<Votes> votes = votesService.findVotes(superDistrictHtml.getSelectStateName(), year);
		List<Votes> totalVotes = votesService.findAllVotes();
		List<District> districts = geoService.findAllDistrict(superDistrictHtml.getSelectStateName(), year);
		String selected = superDistrictHtml.getSelected();
		
		districts = modifySelected(state, districts, votes, selected);
		modelAndView.setViewName("redirect:/gerrymander");
		
		JsonService jsonService = new JsonService(geoService, votesService, demoService);
		
		jsonService.writeSuperDistrictJsonForColor(districts, votes, state);
		return modelAndView;
	}
	
	public List<District> modifySelected(State state, List<District> list, List<Votes> votes, String selected){
		List<String> temp = Arrays.asList(selected.split(","));
		List<String> districts = new ArrayList<>();
		for(String str: temp) {
			districts.add(str);
		}
		System.out.println(districts);
		System.out.println(state.getStateName());
		boolean select = false;
		int total = districts.size();
		int count = 0;
		List<District> sublist = new ArrayList<>();
		double demo = 0;
		double repub = 0;
		District district = new District();
		if(districts.size() != 0) {
			select = true;
			district = geoService.findDistrict(state.getStateName(), Integer.parseInt(districts.get(0)), state.getYear());
			sublist.add(district);
			demo += district.getDemoRatio();
			repub += district.getRepubRatio();
			districts.remove(0);
		}
		
		while(districts.size() != 0) {
			for(District dis : list) {
				if(districts.contains(dis.getDistrictId()+"")) {
					District inside = new District(dis.getStateName(), dis.getDistrictId(), dis.getYear());
					demo += dis.getDemoRatio();
					repub += dis.getRepubRatio();
					inside.setDemoRatio(dis.getDemoRatio());
					inside.setRepubRatio(dis.getRepubRatio());
					sublist.add(inside);
					districts.remove(0);
					System.out.println(dis.getDistrictId());
					break;
				}
			}
		}
		
		for(District change: sublist) {
			for(District dis : list) {
				if(change.getDistrictId() == dis.getDistrictId()) {
					
					dis.setDemoRatio(demo/total);
					dis.setRepubRatio(repub/total);
					dis.setParty((demo/total > repub/total) ? Party.DEMOCRATIC: Party.REPUBLICAN);
					for(Votes vote: votes) {
						if(vote.getDistrictId() == dis.getDistrictId()) {
							vote.setDemoVotes(dis.getDemoRatio());
							vote.setRepubVotes(dis.getRepubRatio());
							
							break;
						}
					}
					break;
				}
			}
		}
		
	return list;
	}
	
	@RequestMapping(value="/gerrymander/manage/viewTestPage", method = RequestMethod.GET)
	public ModelAndView showTestResultPage(ModelAndView modelAndView, Model model, User user){		
		modelAndView.setViewName("viewTestPage");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		ExportPane exportPane = new ExportPane();
		modelAndView.getModel().put("exportPane", exportPane);
//		modelAndView.getModel().put("usersList", userService.findAll());
		return modelAndView;
	}
	
	@RequestMapping(value="/gerrymander/manage/viewTestPage", method = RequestMethod.POST)
	public ModelAndView postTestResultPage(ModelAndView modelAndView, Model model, @ModelAttribute("exportPane") ExportPane exportPane){		
		modelAndView.setViewName("showResultPage");
		User loggedIn = userService.findByStatus("online");
		modelAndView.getModel().put("userAdmin", loggedIn);
		
		
		List<AllMeasure> allData = new ArrayList<>();
		JsonService jsonService = new JsonService(geoService, votesService, demoService);
		int year = jsonService.readAllDataJson(exportPane.getYearsFileName());
		model.addAttribute("viewTestYear", year);
		
		AllDataJson dataObj = new AllDataJson();
		dataObj.setYear(year);
		List<State> states = this.geoService.findState(year);
		List<Votes> totalVotes = votesService.findAllVotes();
		dataObj.setState(states);
		for(State state: states) {
			AllMeasure allMeasure = new AllMeasure();
			List<District> districts = this.geoService.findAllDistrict(state.getStateName(), state.getYear());
			allMeasure.measure(votesService.findVotes(state.getStateName(), state.getYear()), districts, state, totalVotes);
			allData.add(allMeasure);
		}
		modelAndView.getModel().put("testResultList", allData);
		
//		jsonService.writeElectionDataToJson();
//		jsonService.writeFormatedJsonForColor();
		SelectBar selectBar = new SelectBar();
	    model.addAttribute("selectBar", selectBar);
		
		return modelAndView;
	}
	public String getCurrentTimeUsingDate() {
        Date date = new Date();
        String strDateFormat = ("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        	return formattedDate;
    }
	
}
