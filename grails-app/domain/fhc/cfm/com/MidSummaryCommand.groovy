package fhc.cfm.com

import grails.converters.JSON

class MidSummaryCommand implements Serializable{
	//List<String> titles; 
	List<List<String>> data = [];
	
	JSON concevertToChartData(){
		return data as JSON 
	}
    static constraints = {
    }
}
