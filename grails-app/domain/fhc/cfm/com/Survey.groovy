package fhc.cfm.com

import java.io.Serializable;

class Survey implements Serializable  {
	Long surveyId
	String name
    static constraints = {

    }
	static hasMany = [questions: Question]
}
