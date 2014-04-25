package fhc.cfm.com

import java.io.Serializable;

class Question implements Serializable {
	String id
	String name
	boolean requied
	Answer answer
	
	boolean isAnswered(){
		return answer != null ? true : false
	}
	
    static constraints = {
    }
}
