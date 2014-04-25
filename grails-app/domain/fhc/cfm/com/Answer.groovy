package fhc.cfm.com

import java.io.Serializable;

class Answer implements Serializable {
	String value
	
	//static belongsTo = [questions: Question]
	
    static constraints = {
		value blank:false
    }
}
