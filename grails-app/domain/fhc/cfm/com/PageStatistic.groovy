package fhc.cfm.com

class PageStatistic {
	String pageId;
	long odometer = 0;
	
    static constraints = {
		pageId(nullable: false, blank: false, unique: true)
    }
}
