package fhc

import grails.transaction.Transactional

@Transactional
class FlowManagerService {

    def serviceMethod() {
		
    }
	
	List getSequenceAsPerFlowType(def flowType) {
	   List sequence
	   switch (flowType) {
	       case 'defaultBankSurvey':
		   		sequence = ['start', 'demographicInfo', 'financialProductsInfo', 'midSummary','goalSelection','finalSummary']
	       break;
		   case 'defaultBankSurvey1':
		   		sequence = ['start', 'demographicInfo', 'financialProductsInfo', 'midSummary','goalSelection','finalSummary']
           break;
	   }
	   return sequence
	}
}
