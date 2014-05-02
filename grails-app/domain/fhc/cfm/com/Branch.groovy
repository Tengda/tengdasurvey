package fhc.cfm.com

class Branch implements Serializable {
	String branchId
	String name
	String logo
	String url
	Survey survey
    static constraints = {
		branchId blank:false
		name blank:false 
		logo blank:false 
    }
}
