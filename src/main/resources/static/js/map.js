/**
 * This is a JavaScript file for representing gerrymandering map with districts
 */

var currentSlide = 0;

function stateMap(){
	// Create the chart
	
	var states = [];
	var statesInfo = [];
	var currentYear;
	
	currentYear = document.getElementById("dropDownYear").value;

	document.getElementById("workNameLogo").style.visibility= "hidden";
	document.getElementById("viewNameLogoId").style.visibility= "hidden";
	document.getElementById("superDistrictLogo").style.visibility= "hidden";
	document.getElementById("superDistrictBtn").style.visibility= "hidden";
	
	//document.getElementById("superDistrictBtn").style.visibility= "hidden";
	
	$.ajax({
		url: './json/mapColor.json',
		dataType: 'json',
		type: 'get',
		async: false,
		cache: false,
		success: function(data){
			$(data).each(function(index, value){
				//states.push(data);
				var statesArr = [];
				var count = 0;
				//console.log(value.year);
				if(value.year==currentYear){
					while(value.states[count]!=null){
						statesArr = [value.states[count].stateName,value.states[count].testFailed];
						states.push(statesArr);
						count++;
					}
				}
				
				//console.log(df);
			});
		}
	});
	
	$.ajax({
		url: './json/testResults.json',
		dataType: 'json',
		type: 'get',
		async: false,
		cache: false,
		success: function(data){
			$(data).each(function(index, value){
				//states.push(data);
				var tempArr = [];
				var count = 0;
				
				if(value.year==currentYear){
					while(value.results[count]!=null){
						tempArr = [value.results[count].stateName,value.results[count].result.republicanSeats,value.results[count].result.democraticSeats,value.results[count].result.test1,value.results[count].result.test2,value.results[count].result.test3,value.results[count].result.test4];
						statesInfo.push(tempArr);
						count++;
					}
				}
			});
		}
	});
	
	Highcharts.mapChart('stateMap', {
		chart: {
			map: 'countries/us/us-all',
			backgroundColor: 'white',
			width: null,
			height: null
		},
		
		title: {
			text: 'U.S. Congressional Test Results',
		},
		
		subtitle: {
			text: 'United States of America in 2016'
		},
		
		mapNavigation: {
			enabled: true,
			buttonOptions: {
				verticalAlign: 'bottom'
			},
		},
		
		colorAxis: {
	       dataClasses: [{
	       		from: 0,
	       		to: 0,
	       		color: 'lightgreen',
	       		name: 'No Failed Test/All Skipped'
	       	},{
	       		from: 1,
	       		to: 1,
	       		color: 'yellow',
	       		name: '1 Tests Failed'
	       	},{
	       		from: 2,
	       		to: 2,
	       		color: 'orange',
	       		name: '2 Tests Failed'
	        }, {
	        		from: 3,
	        		to: 3,
	        		color: 'red',
	        		name: 'More than 3 Tests Failed'
	        }]
	    	},
	    	
	    	tooltip:{
				formatter: function(){
					var count = 0;
					
					var stateId = this.point["hc-key"].substring(3).toUpperCase()
					var datas;

					while(statesInfo[count][0] != null){
						if(statesInfo[count][0]==stateId){
							datas = "State: "+statesInfo[count][0] + " "+ currentYear+'<br/>';
							datas += "Democratic Seats: "+statesInfo[count][1] +'<br/>';
							datas += "Republican Seats: "+statesInfo[count][2] +'<br/>';
							datas += "Test 1: "+statesInfo[count][3] +'<br/>';
							datas += "Test 2: "+statesInfo[count][4] +'<br/>';
							datas += "Test 3: "+statesInfo[count][5] +'<br/>';
							datas += "Test 4: "+statesInfo[count][6] +'<br/>'; 
							break;
						}
						else{
							count++;
						}
					}
					
					return datas;
				}
		},
		
	    series: [{
			   data: states,
			   name: 'Test result',
			   states: {
				   hover: {
					   color: 'purple' 
			       }
			   },
			   point: {
				   events: {
		    				click: districtMap
		    			}
			   },
	    		}, {
			    name: 'Separators',
			    type: 'mapline',
			    fillColor: 'blue',
			    borderColor: 'red',
	            borderWidth: 3,
			    data: Highcharts.geojson(Highcharts.maps['countries/us/us-all'], 'mapline'),
			    color: 'red',
			    showInLegend: false,
			    enableMouseTracking: false
	    	}]
	});
}


function statesChart(){
	
	var test = 0;
	var test0 = 0; 
	var test1 = 0;
	var test2 = 0;
	var test3 = 0;
	var currentYear;
	
	currentYear = document.getElementById("dropDownYear").value;
	console.log("Current Year: "+currentYear);
	
	$.ajax({
		url: './json/mapColor.json',
		dataType: 'json',
		type: 'get',
		async: false,
		cache: false,
		success: function(data){
			$(data).each(function(index, value){
				//states.push(data);
				var count = 0;
				//console.log(value.states);
				while(value.states[count]!=null){
					statesArr = [value.states[count].stateName,value.states[count].testFailed];
					
					if(value.year == currentYear){
						if(value.states[count].testFailed == -1){
							test++;
						} else if (value.states[count].testFailed == 0){
							test0++;
						} else if (value.states[count].testFailed == 1){
							test1++;
						} else if (value.states[count].testFailed == 2){
							test2++;
						} else{
							test3++;
						}
					}

					count++;
				}
				//console.log(df);
			});
		}
	});
	
	Highcharts.chart('chart', {
	    chart: {
	        type: 'column',
	        	width: null,
	        	height: null,
	    },
	    title: {
	        text: 'Total Number of Test Result'
	    },
	    xAxis: {
	        categories: ['']
	    },
	    credits: {
	        enabled: false
	    },
	    colors: ['lightgreen','yellow','orange','red'],
	    series: [{
	        name: 'Level 0',
	        data: [test0]
	    }, {
	        name: 'Level 1',
	        data: [test1]
	    }, {
	        name: 'Level 2',
	        data: [test2]
	    }, {
	        name: 'Level 3',
	        data: [test3]
	    }]
	});
}


function districtMap() {
	
	var state;
	var districts = [];
	var districtInfo = [];
	var postalCode = this.properties['postal-code'];
	var year;
	var demoTotalRatio;
	var repubTotalRatio;
	var selectedDistricts = [];
	var selectedDistrictsId = [];
	var printSelectedDistricts;
	var countSelectedDistricts = 0;
	var totalDemoRatio = 0;
	var totalRepubRatio = 0;
	
	$(document).ready(function(){
		$('html,body').animate(
				{scrollTop: $("#districtMap").position().top+100}, 1000);
	});
	
	document.getElementById("superDistrictBtn").innerHTML = "<input id='superDistrictBtn' type='submit' value='Click to see the result'><br/>";
	document.getElementById("superDistrictLogo").style.visibility= "visible";
	document.getElementById("superDistrictBtn").style.visibility= "visible";
	document.getElementById("leftBorderLogo").style.visibility= "hidden";

	currentSlide = 2;
	
	currentYear = document.getElementById("dropDownYear").value;
	
	switch(this.name){
	case 'Alabama':
		state = 'countries/us/custom/us-al-congress-113'
		break;d
	case 'Alaska':
		state = 'countries/us/custom/us-ak-congress-113'
		break;
	case 'American Samoa':
		state = 'countries/us/custom/us-as-congress-113'
		break;
	case 'Arizona':
		state = 'countries/us/custom/us-az-congress-113'
		break;
	case 'Arkansas':
		state = 'countries/us/custom/us-ar-congress-113'
		break;
	case 'California':
		state = 'countries/us/custom/us-ca-congress-113'
		break;
	case 'Colorado':
		state = 'countries/us/custom/us-co-congress-113'
		break;
	case 'Connecticut':
		state = 'countries/us/custom/us-ct-congress-113'
		break;
	case 'Delaware':
		state = 'countries/us/custom/us-de-congress-113'
		break;
	case 'District of Columbia':
		state = 'countries/us/custom/us-dc-congress-113'
		break;
	case 'Florida':
		state = 'countries/us/custom/us-fl-congress-113'
		break;
	case 'Georgia':
		state = 'countries/us/custom/us-ga-congress-113'
		break;
	case 'Hawaii':
		state = 'countries/us/custom/us-hi-congress-113'
		break;
	case 'Idaho':
		state = 'countries/us/custom/us-id-congress-113'
		break;
	case 'Illinois':
		state = 'countries/us/custom/us-il-congress-113'
		break;
	case 'Indiana':
		state = 'countries/us/custom/us-in-congress-113'
		break;
	case 'Iowa':
		state = 'countries/us/custom/us-ia-congress-113'
		break;
	case 'Kansas':
		state = 'countries/us/custom/us-ks-congress-113'
		break;
	case 'Kentucky':
		state = 'countries/us/custom/us-ky-congress-113'
		break;
	case 'Louisiana':
		state = 'countries/us/custom/us-la-congress-113'
		break;
	case 'Maine':
		state = 'countries/us/custom/us-me-congress-113'
		break;
	case 'Maryland':
		state = 'countries/us/custom/us-md-congress-113'
		break;
	case 'Massachusetts':
		state = 'countries/us/custom/us-ma-congress-113'
		break;
	case 'Michigan':
		state = 'countries/us/custom/us-mi-congress-113'
		break;
	case 'Minnesota':
		state = 'countries/us/custom/us-mn-congress-113'
		break;
	case 'Mississippi':
		state = 'countries/us/custom/us-ms-congress-113'
		break;
	case 'Missouri':
		state = 'countries/us/custom/us-mo-congress-113'
		break;
	case 'Montana':
		state = 'countries/us/custom/us-mt-congress-113'
		break;
	case 'Nebraska':
		state = 'countries/us/custom/us-ne-congress-113'
		break;
	case 'Nevada':
		state = 'countries/us/custom/us-nv-congress-113'
		break;
	case 'New Hampshire':
		state = 'countries/us/custom/us-nh-congress-113'
		break;
	case 'New Jersey':
		state = 'countries/us/custom/us-nj-congress-113'
		break;
	case 'New Mexico':
		state = 'countries/us/custom/us-nm-congress-113'
		break;
	case 'New York':
		state = 'countries/us/custom/us-ny-congress-113'
		break;
	case 'North Carolina':
		state = 'countries/us/custom/us-nc-congress-113'
		break;
	case 'North Dakota':
		state = 'countries/us/custom/us-nd-congress-113'
		break;
	case 'Ohio':
		state = 'countries/us/custom/us-oh-congress-113'
		break;
	case 'Oklahoma':
		state = 'countries/us/custom/us-ok-congress-113'
		break;
	case 'Oregon':
		state = 'countries/us/custom/us-or-congress-113'
		break;
	case 'Pennsylvania':
		state = 'countries/us/custom/us-pa-congress-113'
		break;
	case 'Rhode Island':
		state = 'countries/us/custom/us-ri-congress-113'
		break;
	case 'South Carolina':
		state = 'countries/us/custom/us-sc-congress-113'
		break;
	case 'South Dakota':
		state = 'countries/us/custom/us-sd-congress-113'
		break;
	case 'Tennessee':
		state = 'countries/us/custom/us-tn-congress-113'
		break;
	case 'Texas':
		state = 'countries/us/custom/us-tx-congress-113'
		break;
	case 'Utah':
		state = 'countries/us/custom/us-ut-congress-113'
		break;
	case 'Vermont':
		state = 'countries/us/custom/us-vt-congress-113'
		break;
	case 'Virginia':
		state = 'countries/us/custom/us-va-congress-113'
		break;
	case 'Washington':
		state = 'countries/us/custom/us-wa-congress-113'
		break;
	case 'West Virginia':
		state = 'countries/us/custom/us-wv-congress-113'
		break;
	case 'Wisconsin':
		state = 'countries/us/custom/us-wi-congress-113'
		break;
	case 'Wyoming':
		state = 'countries/us/custom/us-wy-congress-113'
		break;
	}
	
	console.log(state.substring(23,25).toUpperCase());
	document.getElementById("stateNameLogo").innerHTML = "<p id=stateNameLogo>Selected State:&emsp;&emsp;&nbsp;&nbsp; <input id='stateName' type='text' name='stateName' value="+state.substring(23,25).toUpperCase()+"></p>";
		
		
	//<p id = "stateNameLogo">Selected State:&emsp;&emsp;&nbsp;&nbsp;
	//<input id="stateName" type="text" name="stateName" value="AL" ></p>
	//document.getElementById("stateName").innerHTML = "<input id='stateName' type='text' name='stateName' value="+state.substring(23,25).toUpperCase()+">"
    	/**
    	 * Use Jquery to generate color of the district map
    	 */	
	$.ajax({
		url: './json/mapColor.json',
		dataType: 'json',
		type: 'get',
		async: false,
		cache: false,
		success: function(data){
			$(data).each(function(index, value){
				//states.push(data);
				var tempArr = [];
				var count = 0;
				//console.log(value.districts);
				if(value.year==currentYear){
					while(value.districts[count]!=null){
						tempArr = [value.districts[count].districtNum,value.districts[count].party];
						districts.push(tempArr);
						count++;
					}
				}
				
			});
		}
	});
	
	/**
	 * Use JQuery to load information to display when hovering over map
	 */
	
	$.ajax({
		url: './json/mapTemp.json',
		dataType: 'json',
		type: 'get',
		async: false,
		cache: false,
		success: function(data){
			$(data).each(function(index1, value1){
				//console.log(value1.districts[postalCode]);
				
				var tempArr = [];
				var count = 0;
				
				//console.log(currentYear);
				
				while(value1.districts[postalCode][count]!=null){
				
					if(value1.districts[postalCode][count].year == currentYear){
						year = this.year;
						tempArr = [
									value1.districts[postalCode][count].districtId,
									value1.districts[postalCode][count].stateName,	
									value1.districts[postalCode][count].demoRatio,
									value1.districts[postalCode][count].repubRatio
									]
						districtInfo.push(tempArr);
						
						count++;
					}
					else{
						count++;
					}
				}
			});
		}
	});
	
	//console.log("########"+districtInfo);
	
	Highcharts.mapChart('districtMap',{
		chart: {
			map: state,
			backgroundColor:'white',
			width: null,
			height: null,
		},
		title: {
			text: 'U.S. Congressional District Test Results',
		},
		mapNavigation: {
			enabled: true,
			buttonOptions: {
				verticalAlign: 'bottom'
			}
		},
		plotOptions: {
			series: {
				point:{
					events: {
						select: function(){
							var text = "Selected State: " + postalCode + "Selected Districts: " + selectedDistricts +
										"\n Year: " + year,
								chart = this.series.chart;
							
							if (!chart.selectedLabel) {
                                chart.selectedLabel = chart.renderer.label(text, 0, 320)
                                    .add();
                            	}
							else {
                                chart.selectedLabel.attr({
                                    text: text
                                });
                            	}
							
							var i = 0;
							var isTyped = false;
							
							var id;
							var check = this.options["hc-key"].substring(6,7);
							
							
							if(check==0){
								id = this.options["hc-key"].substring(7);
							}
							else{
								id = this.options["hc-key"].substring(6);
							}
							
							for(i = 0; i<countSelectedDistricts; i++){
								if(selectedDistricts[i] == null){
									selectedDistricts[i] = districtInfo[(this.options["hc-key"].substring(6))-1];
									selectedDistrictsId[i] = id
									isTyped = true;
									countSelectedDistricts++;
								}
							}
							
							if(isTyped==false){
								selectedDistricts[countSelectedDistricts]=districtInfo[(this.options["hc-key"].substring(6))-1];
								selectedDistrictsId[i] = id;
								countSelectedDistricts++;
							}
							
	
							console.log("District Selected!");
							console.log("Total Selected Districts: "+ selectedDistricts);
							console.log("Number of Selected Districts: "+countSelectedDistricts);
							console.log("Year: "+year);
							console.log("State: "+postalCode);
							
							document.getElementById("selected").value = selectedDistrictsId;
							document.getElementById("selectYear").value = year;
							document.getElementById("selectStateName").value = postalCode;
						},
						
						unselect: function(){
							var id;
							var check = this.options["hc-key"].substring(6,7);
							
							
							if(check==0){
								id = this.options["hc-key"].substring(7);
							}
							else{
								id = this.options["hc-key"].substring(6);
							}
	
							var i = 0;
							var isEmpty = false;
							
							
							for(i=0; i<20; i++){
								if(selectedDistricts[i]!=null){
									if(selectedDistricts[i][0] == id){
										selectedDistricts[i] = null;
										countSelectedDistricts--;
									}
								}
							}
							
							console.log("District Unselected!");
							console.log("Remaining destricts: " + selectedDistricts);
							console.log("Number of districts: " + countSelectedDistricts);
							console.log("Year: "+year);
							console.log("State: "+postalCode);
							console.log("DistrictId: "+selectedDistricts);
							//console.log(selectedDistrictsId+=selectedDistricts[0][0]);
							
						}
					}
				}
			}
		},
		colorAxis: {
			dataClasses: [{
	    	   		from: 0,
	    	   		to: 0,
	    	   		color: 'blue',
	    	   		name: 'Democratic'
				},{
	       		from: 1,
	       		to: 1,
	       		color: 'red',
	       		name: 'Republican'
			}]
		},
		tooltip:{
			formatter: function(){
				var count = 0;
				var districtID = this.key.substring(23);
				var datas = this.key+' ('+districtInfo[districtID-1][1]+')'+ '<br/>';
				datas+= "Democratic: "+ districtInfo[districtID-1][2]+'%'+'<br/>';
				datas+= "Republican: "+ districtInfo[districtID-1][3]+'%';
				
				return datas;
			},	
		},
	
		series: [{
			data: districts,
		states: {
				hover: {
					color: 'grey'
				}
			},
			
		   allowPointSelect: true
		}]
	});
	
	
	document.getElementById("stateName").value = this.properties['postal-code'];
	
}

function districtClick(){
	document.getElementById("districtId").value = this.properties['hc-key'].substring(6);
	currentSlide = 2;
	console.log("current slide in district click: "+currentSlide);
}

function superDistrict(){
	
	var state;
	var superDistricts = [];
	
	currentYear = document.getElementById("dropDownYear").value;

	$.ajax({
		url: './json/superdistrict.json',
		dataType: 'json',
		type: 'get',
		async: false,
		cache: false,
		success: function(data){
			$(data).each(function(index, value){
				//states.push(data);
				var tempArr = [];
				var count = 0;
				//console.log(value.districts);
				if(value.year==currentYear){
					while(value.districts[count]!=null){
						tempArr = [value.districts[count].districtNum,value.districts[count].party];
						superDistricts.push(tempArr);
						count++;
					}
				}
				
			});
		}
	});
	
	state = 'countries/us/custom/'+superDistricts[0][0].substring(0,5)+'-congress-113';
	
	Highcharts.mapChart('superDistrictMap',{
		chart: {
			map: state,
			backgroundColor:'white',
			width: null,
			height: null,
		},
		title: {
			text: 'U.S. Congressional Super District Test Results',
		},
		mapNavigation: {
			enabled: true,
			buttonOptions: {
				verticalAlign: 'bottom'
			}
		},
		
		plotOptions: {
			series: {
				point:{
					events: {
						//click: districtMap;
					}
				}
			}
		},
		colorAxis: {
			dataClasses: [{
	    	   		from: 0,
	    	   		to: 0,
	    	   		color: 'blue',
	    	   		name: 'Democratic'
				},{
	       		from: 1,
	       		to: 1,
	       		color: 'red',
	       		name: 'Republican'
			}]
		},
		series: [{
			data: superDistricts,
		states: {
				hover: {
					color: 'grey'
				}
			},
			
		   //allowPointSelect: true
		}]
	});
}

function nextSlideBtn(){
	if(currentSlide == 0){
		console.log("Move from: "+currentSlide+"Move to: "+(currentSlide+1));
		$(document).ready(function(){
			$('html,body').animate(
					{scrollTop: $("#chart").position().top+100}, 1000)
		});
		
		document.getElementById("leftBorderLogo").innerHTML = "<br/><br/><br/><br/><br/><h3>The graph represents total number<br/> of U.S. congressional test results</h3>";
		currentSlide = 1;
		
	}
	
	else if(currentSlide == 1){
		$(document).ready(function(){
			$('html,body').animate(
					{scrollTop: $("#districtMap").position().top+100}, 1000);
		});
		
		document.getElementById("superDistrictBtn").innerHTML = "<input id='superDistrictBtn' type='submit' value='Create a super district'><br/>";
		document.getElementById("leftBorderLogo").style.visibility = "hidden";
		document.getElementById("superDistrictLogo").style.visibility= "visible";
		document.getElementById("superDistrictBtn").style.visibility= "visible";
		
		currentSlide = 2;
	}
	else{
		$(document).ready(function(){
			$('html,body').animate(
					{scrollTop: $("#superDistrictMap").position().top+100}, 1000);
		});
		
		document.getElementById("workNameLogo").style.visibility= "visible";
		document.getElementById("viewNameLogoId").style.visibility= "visible";
		document.getElementById("superDistrictLogo").style.visibility= "hidden";
		document.getElementById("superDistrictBtn").style.visibility= "hidden";
		document.getElementById("leftBorderLogo").style.visibility = "hidden";
		
		currentSlide = 3;
	}
}

function prevSlideBtn(){
	if(currentSlide == 1){
		console.log("Move from: "+currentSlide+"Move to: "+(currentSlide-1));
		$(document).ready(function(){
			$('html,body').animate(
					{scrollTop: $("html,body").position().top}, 1000);
		});
		
		document.getElementById("leftBorderLogo").innerHTML = "<br/><br/><br/><br/><br/><h3>Click on the one of states to see<br/>how it performs on our gerrymander<br/> detection tests.</h3>";
		
		currentSlide = 0;
	}
	else if(currentSlide == 2){
		$(document).ready(function(){
			$('html,body').animate(
					{scrollTop: $("#chart").position().top+100}, 1000);
		});
		
		document.getElementById("leftBorderLogo").innerHTML = "<br/><br/><br/><br/><br/><h3>The graph represents total number<br/> of U.S. congressional test results</h3>";
		document.getElementById("superDistrictLogo").style.visibility= "hidden";
		document.getElementById("superDistrictBtn").style.visibility= "hidden";
		document.getElementById("leftBorderLogo").style.visibility = "visible";
		
		currentSlide = 1;
	}
	else if(currentSlide == 3){
		$(document).ready(function(){
			$('html,body').animate(
					{scrollTop: $("#districtMap").position().top+100}, 1000);
		});
		
		document.getElementById("superDistrictBtn").innerHTML = "<input id='superDistrictBtn' type='submit' value='Create a super district'><br/>";
		document.getElementById("superDistrictLogo").style.visibility= "visible";
		document.getElementById("superDistrictBtn").style.visibility= "visible";
		document.getElementById("workNameLogo").style.visibility= "hidden";
		document.getElementById("viewNameLogoId").style.visibility= "hidden";
		document.getElementById("leftBorderLogo").style.visibility = "hidden";
		
		currentSlide = 2;
	}
	else{
	}
}

function backToState(){
	$(document).ready(function(){
		$('html,body').animate(
				{scrollTop: $("html,body").position().top}, 1000);
	});
	
	document.getElementById("workNameLogo").style.visibility= "hidden";
	document.getElementById("viewNameLogoId").style.visibility= "hidden";
	document.getElementById("superDistrictLogo").style.visibility= "hidden";
	document.getElementById("superDistrictBtn").style.visibility= "hidden";
	document.getElementById("leftBorderLogo").style.visibility = "visible";
	document.getElementById("leftBorderLogo").innerHTML = "<br/><br/><br/><br/><br/><h3>Click on the one of states to see<br/>how it performs on our gerrymander<br/> detection tests.</h3>";
	
	currentSlide = 0;
}
