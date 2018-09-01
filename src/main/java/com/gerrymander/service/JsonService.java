package com.gerrymander.service;

import java.util.*;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerrymander.beans.*;
import com.gerrymander.constant.ControllerAttributes;
import com.gerrymander.htmlObject.DemoDataObj;
import com.gerrymander.htmlObject.DemoDataYears;
import com.gerrymander.jsonBeans.AllDataJson;
import com.gerrymander.jsonBeans.DistrictColor;
import com.gerrymander.jsonBeans.ElectionDataJson;
import com.gerrymander.jsonBeans.FormatedColor;
import com.gerrymander.jsonBeans.*;
import com.gerrymander.measure.AllMeasure;
import com.gerrymander.beans.*;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.io.File;
import java.io.IOException;

@Service("jsonService")
public class JsonService {
	@Autowired
	private GeoService geoService;
	@Autowired
	private VotesService votesService;

	private DemographicService demoService;
	
    ObjectMapper objectMapper = new ObjectMapper();
    
    private EntityManager entityManager;

	@Autowired
	public JsonService(GeoService geoService, VotesService votesService, EntityManager entityManager,
			DemographicService demoService) {
		this.geoService = geoService;
		this.votesService = votesService;
		this.entityManager= entityManager;
		this.demoService = demoService;
	}
	
	public JsonService(GeoService geoService, VotesService votesService, DemographicService demoService) {
		this.geoService = geoService;
		this.votesService = votesService;
		this.demoService = demoService;
	}
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public void readVotesJson(String fileName) {
		try {
			String dire = String.format("./src/main/resources/static/json/%s.json", fileName);
			JsonNode rootNode = objectMapper.readTree(new File(dire));
			for(int i = 0; i < rootNode.size(); i++) {
				String stateName = rootNode.get(i).get("stateName").asText();
				int districtId = Integer.parseInt(rootNode.get(i).get("districtId").asText());
				int year = Integer.parseInt(rootNode.get(i).get("year").asText());
				double demo = Double.parseDouble(rootNode.get(i).get("demoVotes").asText());
				double repub = Double.parseDouble(rootNode.get(i).get("repubVotes").asText());
				votesService.saveVotes(new Votes(stateName, districtId, year, demo, repub));
			}
//			String prettyPrintEmployee = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
//			logger.info(prettyPrintEmployee+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readVotesJson(int year, String yearFileName) {
		try {
			String dire = String.format("./src/main/resources/static/json/%s.json", yearFileName);
			JsonNode rootNode = objectMapper.readTree(new File(dire));
			for(int i = 0; i < rootNode.size(); i++) {
				String stateName = rootNode.get(i).get("stateName").asText();
				int districtId = Integer.parseInt(rootNode.get(i).get("districtId").asText());
				int readYear = year;
//				System.out.println("************************");
//				System.out.println(yearFileName);
//				System.out.println("************************");

				double demo = Double.parseDouble(rootNode.get(i).get("demoVotes").asText());
				double repub = Double.parseDouble(rootNode.get(i).get("repubVotes").asText());
				votesService.saveVotes(new Votes(stateName, districtId, readYear, demo, repub));
			}
			updateDistrict();
			updateState();
//			String prettyPrintEmployee = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
//			logger.info(prettyPrintEmployee+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void readDemographicJson(int year, String yearFileName) {
		try {
			String dire = String.format("./src/main/resources/static/json/%s.json", yearFileName);
			JsonNode rootNode = objectMapper.readTree(new File(dire));
			for(int i = 0; i < rootNode.size(); i++) {
				String stateName = rootNode.get(i).get("stateName").asText();
				int readYear = Integer.parseInt(rootNode.get(i).get("year").asText());
				int male = Integer.parseInt(rootNode.get(i).get("male").asText());
				int female = Integer.parseInt(rootNode.get(i).get("female").asText());
				int white = Integer.parseInt(rootNode.get(i).get("white").asText());
				int black = Integer.parseInt(rootNode.get(i).get("black").asText());
				int asian = Integer.parseInt(rootNode.get(i).get("asian").asText());
				int hispanic = Integer.parseInt(rootNode.get(i).get("hispanic").asText());
				demoService.save(new Demographic(stateName, readYear, male, female, white, black, asian, hispanic));
			}
//			String prettyPrintEmployee = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
//			logger.info(prettyPrintEmployee+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void writeAllVotesJson(int yearTo, int yearFrom, String fileName) {
		try {
			String dire = String.format("./src/main/resources/static/json/%s.json", fileName);
			File file = new File(dire);
			List<Votes> votes = new ArrayList<>();
			for(int i = yearTo; i >= yearFrom; i-=2) {
				votes.addAll(votesService.findByYear(i));
			}
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		    objectMapper.writeValue(file, votes);
		} catch (JsonGenerationException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();																
	    }	
	}
	
	public void writeAllVotesJson(int year, String fileName) {
		try {
			String dire = String.format("./src/main/resources/static/json/%s.json", fileName);
			File file = new File(dire);
			List<Votes> votes = votesService.findByYear(year);
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		    objectMapper.writeValue(file, votes);
		} catch (JsonGenerationException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }	
	}
	
	public void writeAllDemosJson(int yearTo, int yearFrom, String fileName) {
		try {
			String dire = String.format("./src/main/resources/static/json/%s.json", fileName);
			File file = new File(dire);
			Set<String> set = new HashSet<>();
			List<Demographic> demos = new ArrayList<>();
			for(int i = yearTo; i >= yearFrom; i-=2) {
				for(Demographic demo: demoService.findByYear(i)){
					if(set.add(demo.getStateName())) demos.add(demo);
				}
			}
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		    objectMapper.writeValue(file, demos);
		} catch (JsonGenerationException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }	
	}
	
	public void writeAllDemosJson(int year, String fileName) {
		try {
			String dire = String.format("./src/main/resources/static/json/%s.json", fileName);
			File file = new File(dire);
			Set<String> set = new HashSet<>();
			List<Demographic> demos = new ArrayList<>();
			for(Demographic demo: demoService.findByYear(year)){
				if(set.add(demo.getStateName())) demos.add(demo);
			}
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		    objectMapper.writeValue(file, demos);
		} catch (JsonGenerationException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }	
	}
	
	public void writeAllDataJson(int yearTo, int yearFrom, String fileName) {
		try {
			String dire = String.format("./src/main/resources/static/json/%s.json", fileName);
			File file = new File(dire);
			List<AllDataJson> list = new ArrayList<>();
			for(int year = yearTo; year >= yearFrom; year-=2) {
				list.add(writeAllDataHelper(year));
			}
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		    objectMapper.writeValue(file, list);
		} catch (JsonGenerationException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }	
	}
	public int readAllDataJson(String yearFileName) {
		int year = 0;
		try {
			String dire = String.format("./src/main/resources/static/json/%s.json", yearFileName);
			JsonNode rootNode = objectMapper.readTree(new File(dire));
			year = Integer.parseInt(rootNode.get("year").asText());
			return year;
//			String prettyPrintEmployee = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
//			logger.info(prettyPrintEmployee+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return year;
	}
	
	public void writeAllDataJson(int year, String fileName) {
		try {
			String dire = String.format("./src/main/resources/static/json/%s.json", fileName);
			File file = new File(dire);
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		    objectMapper.writeValue(file, writeAllDataHelper(year));
		} catch (JsonGenerationException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }	
	}
	
	public void writeStatePaneDataJson(String fileName) {
		try {
			String dire = String.format("./src/main/resources/static/json/%s.json", fileName);
			File file = new File(dire);
			
			List<DemoDataYears> list = new ArrayList<>();
			for(int year : generateYearList()) {
				DemoDataYears demo = new DemoDataYears();
				demo.setYear(year);
				
				List<State> states = this.geoService.findState(year);
				List<DemoDataObj> subList = new ArrayList<>();
				for(State state: states) {
					DemoDataObj obj = new DemoDataObj();
					Map<String, Integer> subMap = new HashMap<>();
					subMap.put("DemocraticSeats", state.getDemoSeats());
					subMap.put("RepublicanSeats", state.getRepubSeats());
					Demographic temp = demoService.findByStateNameAndYear(state.getStateName(), year);
					subMap.put("Male", temp.getMale());
					subMap.put("Female", temp.getFemale());
					subMap.put("White", temp.getWhite());
					subMap.put("Black", temp.getBlack());
					subMap.put("Asian", temp.getAsian());
					subMap.put("Hispanic", temp.getHispanic());
					obj.setStateName(state.getStateName());
					obj.setData(subMap);
					subList.add(obj);
				}
				demo.setDemoData(subList);
				list.add(demo);
			}
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		    objectMapper.writeValue(file, list);
		} catch (JsonGenerationException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }	
	}
	
	public AllDataJson writeAllDataHelper(int year) {
		AllDataJson dataObj = new AllDataJson();
		dataObj.setYear(year);
		List<State> states = this.geoService.findState(year);
		List<Votes> totalVotes = votesService.findAllVotes();
		Map<String, AllMeasure> measureMap = new HashMap<>();
		Map<String, List<District>> districtMap = new HashMap<>();
		dataObj.setState(states);
		for(State state: states) {
			AllMeasure allMeasure = new AllMeasure();
			List<District> districts = this.geoService.findAllDistrict(state.getStateName(), state.getYear());
			allMeasure.measure(votesService.findVotes(state.getStateName(), state.getYear()), districts, state, totalVotes);
			measureMap.put(state.getStateName(), allMeasure);
			districtMap.put(state.getStateName(), districts);
		}
		dataObj.setAllMeasure(measureMap);
		dataObj.setDistricts(districtMap);
		return dataObj;
    	}
	
	public void writeFormatedJsonForColor() {
		try {
			File file = new File("./src/main/resources/static/json/mapColor.json");
			List<FormatedColor> list = new ArrayList<>();
			for(int year: generateYearList()) {
	    			list.add(writeFormatedJsonForColorHelper(year));
	    		}
        		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		    objectMapper.writeValue(file, list);
	    } catch (JsonGenerationException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public FormatedColor writeFormatedJsonForColorHelper(int year) {
		FormatedColor colors = new FormatedColor();
		List<DistrictColor> districtsList = new ArrayList<>();
		List<StateColor> statesList = new ArrayList<>();
		List<State> states = this.geoService.findState(year);
		List<Votes> totalVotes = votesService.findAllVotes();
//		System.out.println("*****************************");
//		System.out.println(year);
//		System.out.println("*****************************");
		for(State state: states) {
			StateColor stateColor = new StateColor();
			AllMeasure all = new AllMeasure();
			String lowerStateName = state.getStateName().toLowerCase();
			List<District> districts = this.geoService.findAllDistrict(state.getStateName(), state.getYear());
			stateColor.setStateName(String.format(ControllerAttributes.STATE_COLOR_JSON_FORMAT, lowerStateName));
			all.measure(votesService.findVotes(state.getStateName(), state.getYear()), districts, state, totalVotes);
			stateColor.setTestFailed(all.getFailed());
			statesList.add(stateColor);
			districtsList = generateDistrictsColorJson(districts, totalVotes, districtsList, state);
		}
		colors.setYear(year);
		colors.setStates(statesList);
		colors.setDistricts(districtsList);
		return colors;
	}
	
	public void writeSuperDistrictJsonForColor(List<District> districts, List<Votes> superVotes, State state) {
		try {
			File file = new File("./src/main/resources/static/json/superdistrict.json");
			FormatedColor list = new FormatedColor();
			list = writeSuperDistrictJsonForColorHelper(districts, superVotes, state, state.getYear());
	    		
        		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		    objectMapper.writeValue(file, list);
	    } catch (JsonGenerationException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public FormatedColor writeSuperDistrictJsonForColorHelper(List<District> dists, List<Votes> superVotes, State superState, int year) {
		FormatedColor colors = new FormatedColor();
		List<DistrictColor> districtsList = new ArrayList<>();
		List<StateColor> statesList = new ArrayList<>();
		List<State> states = this.geoService.findState(year);
		List<Votes> totalVotes = votesService.findAllVotes();
//		System.out.println("*****************************");
//		System.out.println(year);
//		System.out.println("*****************************");
		
			StateColor stateColor = new StateColor();
			AllMeasure all = new AllMeasure();
			String lowerStateName = superState.getStateName().toLowerCase();
			List<District> districts = this.geoService.findAllDistrict(superState.getStateName(), superState.getYear());
				districts = dists;
				all.measure(superVotes, districts, superState, totalVotes);
				districtsList = generateDistrictsColorJson(dists, superVotes, districtsList, superState);
			
			stateColor.setStateName(String.format(ControllerAttributes.STATE_COLOR_JSON_FORMAT, lowerStateName));
			stateColor.setTestFailed(all.getFailed());
			if(districts.size() < 6)
				stateColor.setTestFailed(-1);
			statesList.add(stateColor);
			
		
		colors.setYear(year);
		colors.setStates(statesList);
		colors.setDistricts(districtsList);
		return colors;
	}
	
	
	public void writeDistrictFormatedJson() {
		try {
	    		List<ElectionDataJson> objList = new ArrayList<>();
	    		File file = new File("./src/main/resources/static/json/mapTemp.json");
	        	for(int year : generateYearList()) {
	        		List<State> states = this.geoService.findState(year);
	        		ElectionDataJson obj = new ElectionDataJson();
	        		Map<String, List<District>> map = new HashMap<>();
	        		obj.setYear(year);
	        		obj.setStates(states);
	        		for(State state: states) {
	        			List<District> districts = this.geoService.findAllDistrict(state.getStateName(), year);
	        			map.put(state.getStateName(), districts);
	        		}
	        		obj.setDistricts(map);
	        		objList.add(obj);
	    		}
	        	objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	        objectMapper.writeValue(file, objList);
	        
	    } catch (JsonGenerationException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void writeSuperDistrictFormatedJson(List<District> superDistricts, State superState) {
		try {
	    		List<ElectionDataJson> objList = new ArrayList<>();
	    		File file = new File("./src/main/resources/static/json/mapTemp.json");
	        	for(int year : generateYearList()) {
	        		List<State> states = this.geoService.findState(year);
	        		ElectionDataJson obj = new ElectionDataJson();
	        		Map<String, List<District>> map = new HashMap<>();
	        		obj.setYear(year);
	        		obj.setStates(states);
	        		for(State state: states) {
	        			List<District> districts = this.geoService.findAllDistrict(state.getStateName(), year);
	        			if(state.getStateName().equals(superState.getStateName()) && state.getYear() == superState.getYear()) {
	        				districts = superDistricts;
	        			}
	        			map.put(state.getStateName(), districts);
	        		}
	        		obj.setDistricts(map);
	        		objList.add(obj);
	    		}
	        	objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	        objectMapper.writeValue(file, objList);
	        
	    } catch (JsonGenerationException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
 
    public void writeElectionDataToJson() {
        try {
        		List<ElectionDataJson> objList = new ArrayList<>();
        		File file = new File("./src/main/resources/static/json/mapTemp.json");
	        	for(int year : generateYearList()) {
	        		List<State> states = this.geoService.findState(year);
	        		ElectionDataJson obj = new ElectionDataJson();
	        		Map<String, List<District>> map = new HashMap<>();
	        		obj.setYear(year);
	        		obj.setStates(states);
	        		for(State state: states) {
	        			List<District> districts = this.geoService.findAllDistrict(state.getStateName(), year);
	        			map.put(state.getStateName(), districts);
	        		}
	        		obj.setDistricts(map);
	        		objList.add(obj);
	    		}
	        	objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	        objectMapper.writeValue(file, objList);
            
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public List<StateColor> generateColorJson(int year){
		List<State> states = this.geoService.findState(year);
		List<StateColor> stateLlist = new ArrayList<>();
		List<Votes> totalVotes = votesService.findAllVotes();
		for(State state: states) {
			StateColor stateColor = new StateColor();
			AllMeasure all = new AllMeasure();
			stateColor.setStateName(String.format(ControllerAttributes.STATE_COLOR_JSON_FORMAT, state.getStateName().toLowerCase()));
			List<District> districts = this.geoService.findAllDistrict(state.getStateName(), state.getYear());
			all.measure(votesService.findVotes(state.getStateName(), year), districts, state, totalVotes);
			stateColor.setTestFailed(all.getFailed());
			if(districts.size() < 6)
				stateColor.setTestFailed(-1);
			stateLlist.add(stateColor);
		}
		return stateLlist;
	}
	
	public List<DistrictColor> generateDistrictsColorJson(List<District> dists, List<Votes> superVotes, List<DistrictColor> list, State state){
		List<District> districts = dists;
		for(District district: districts) {
			DistrictColor districtColor = new DistrictColor();
			districtColor.setDistrictNum(String.format(ControllerAttributes.DISTRICT_COLOR_JSON_FORMAT,
					state.getStateName().toLowerCase(), generateTwoDigitsId(district.getDistrictId())));
			districtColor.setParty(district.getParty().getVal());
			list.add(districtColor);
		}
		return list;
	}
	
	public String generateTwoDigitsId(int id) {
		if(id/10 >= 1)
			return String.valueOf(id);
		else
			return "0"+String.valueOf(id);
	}

		
	public void updateDistrict() {
		List<Votes> votes = votesService.findAllVotes();
		for(Votes vote: votes) {
			double demo = vote.getDemoVotes();
			double repub = vote.getRepubVotes();
		
			if(vote.getDemoVotes() > vote.getRepubVotes()) {
				geoService.saveDistrict(new District(vote.getStateName(), vote.getDistrictId(),
						vote.getYear(), Party.DEMOCRATIC, demo, repub));
			}
			else {
				geoService.saveDistrict(new District(vote.getStateName(), vote.getDistrictId(), 
						vote.getYear(), Party.REPUBLICAN, demo, repub));
			}
		}
	}
	
	public void updateState() {
		List<District> districts = geoService.findAllDistrict();
		int demoSeats = 0, repubSeats = 0;
		String stateName = "";
		State state = new State();
//		if(districts.size() != 0) {
//			stateName = districts.get(0).getStateName();
//			state = new State(districts.get(0).getStateName(), districts.get(0).getYear());
//		}
		List<State> states = new ArrayList<>();
		for(District district: districts) {
			state = checkStateInList(district.getStateName(), states);
			if(state != null) {
				if(district.getParty() == Party.DEMOCRATIC) {
					int demo = state.getDemoSeats()+1;
					state.setDemoSeats(demo);
				}
				else {
					int repub = state.getRepubSeats()+1;
					state.setRepubSeats(repub);
				}
				state.setParty((state.getDemoSeats() > state.getRepubSeats()) ? Party.DEMOCRATIC : Party.REPUBLICAN);
			}
			else {
				State aState = new State();
				aState.setYear(district.getYear());
				aState.setStateName(district.getStateName());
				if(district.getParty() == Party.DEMOCRATIC) {
					int demo = aState.getDemoSeats()+1;
					aState.setDemoSeats(demo);
				}
				else {
					int repub = aState.getRepubSeats()+1;
					aState.setRepubSeats(repub);
				}
				aState.setParty((aState.getDemoSeats() > aState.getRepubSeats()) ? Party.DEMOCRATIC : Party.REPUBLICAN);
				states.add(aState);
			}
		}
		for(State s: states) {
			geoService.saveState(s);
		}
//		state.setParty((demoSeats > repubSeats) ? Party.DEMOCRATIC : Party.REPUBLICAN);
//		geoService.saveState(state);
	}
	
	public State checkStateInList(String stateName, List<State> states) {
		for(State state: states) {
			if(stateName.equals(state.getStateName()))
				return state;
		}
		return null;
	}
	
	public void writeTestResultJson() {
		try {
			File file = new File("./src/main/resources/static/json/testResults.json");
			List<ResultJson> list = new ArrayList<>();
	    		for(int year : generateYearList()) {
	    			list.add(writeTestResultJsonHelper(year));
	    		}
        		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		    objectMapper.writeValue(file, list);
	    } catch (JsonGenerationException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public ResultJson writeTestResultJsonHelper(int year) {
		ResultJson resultJson = new ResultJson();
		List<State> states = this.geoService.findState(year);
		List<Votes> totalVotes = votesService.findAllVotes();
//		System.out.println("*****************************");
//		System.out.println(year);
//		System.out.println("*****************************");
		resultJson.setYear(year);
		List<StateResult> list = new ArrayList<>();
		for(State state: states) {
			AllMeasure all = new AllMeasure();
			StateResult result = new StateResult();
			List<District> districts = this.geoService.findAllDistrict(state.getStateName(), state.getYear());
			result.setStateName(state.getStateName());
			all.measure(votesService.findVotes(state.getStateName(), state.getYear()), districts, state, totalVotes);
			Map<String, String> map = new HashMap<>();
			map.put("democraticSeats", state.getDemoSeats()+"");
			map.put("republicanSeats", state.getRepubSeats()+"");
			map.put("test1", all.getLopsidedWins().getResult().toString());
			map.put("test2", all.getConsistentAdvantage().getResult().toString());
			map.put("test3", all.getExcessSeats().getResult().toString());
			map.put("test4", all.getEfficiencyGap().getResult().toString());
			result.setResult(map);
			list.add(result);
		}
		resultJson.setResults(list);
		return resultJson;
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
