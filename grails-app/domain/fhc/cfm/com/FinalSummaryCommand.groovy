package fhc.cfm.com

import grails.converters.JSON;

import java.util.List;

class FinalSummaryCommand implements Serializable{
	String email;
	String telepressence;
	
	List<List<String>> tableData = [];

	JSON convertToTableData(){
		return tableData as JSON
	}
	
    static constraints = {
    }
}
