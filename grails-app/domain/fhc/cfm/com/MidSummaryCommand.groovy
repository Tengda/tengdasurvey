package fhc.cfm.com

import grails.converters.JSON

class MidSummaryCommand implements Serializable{
	//List<String> titles; 
	List<List<String>> chartData = [];
	List<List<String>> tableData = [];
	
	JSON convertToChartData(){
		return chartData as JSON 
	}
	
	JSON convertToTableData(){
		return tableData as JSON
	}
	
    static constraints = {
    }
}
