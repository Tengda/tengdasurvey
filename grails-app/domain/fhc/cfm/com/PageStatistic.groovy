package fhc.cfm.com

import java.io.Serializable;

class PageStatistic implements Serializable {
	String pageId;
	Survey survey;
	long odometer = 0;
	
    static constraints = {
    }
}
