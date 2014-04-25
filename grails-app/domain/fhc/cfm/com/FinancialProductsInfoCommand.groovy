package fhc.cfm.com

class FinancialProductsInfoCommand implements Serializable{
	
	String sex;
	String marital_status;
	String age;
	String kids;
	String debt;
	static constraints = {
		//sex(blank: false)
		//marital_status(blank: false)
		//age(blank: false)
		//kids(blank: false)
		//debt(blank: false)
	}
	
}
