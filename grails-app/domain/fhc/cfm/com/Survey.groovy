package fhc.cfm.com

import java.io.Serializable;

class Survey implements Serializable  {
	String name
    static constraints = {

    }
	static hasMany = [questions: Question]
}
