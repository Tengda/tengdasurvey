package fhc.cfm.com

import org.junit.After;

import groovy.swing.impl.DefaultAction;

class SurveyController {
	def surveyService
	//def flowManagerService
	//FlowInfo flowInfo
	def index() {
		// default branch
		
		if(!params.branchId)
			params.branchId = 1
		def branch = Branch.get(params.branchId)
		render(view: "index", model: [branch: branch])
		/*
		//redirect(action: "survey")
		println "****************************index?***********************"
		redirect(action:'init')*/
	}
	
	def init={
		println "*****************************init?**********************:"+params.branchId
		if(!params.branchId)
			params.branchId = 1
		def branch = Branch.get(params.branchId)
		render(view: "index", model: [branch: branch])
	}
	
	def startSuvey(){
		def branch = Branch.get(params.branchId)
		//branch.save()
		//redirect(action:branch.survey.name,model: [branch:branch])
		println "*************************startSuvey**************************:"+branch.survey.name 
		redirect(action:branch.survey.name, params: [branchId: params.branchId])
		/*
		def myid =2 
		if( myid ==2 )
			redirect(action:'defaultBankSurvey')
		else
			redirect(action:'tengdaBankSurvey')
		*/
	}
	
	def defaultBankSurveyFlow = {
		defaultBankSurveyFlowStart {
			//render(view:"/shared/startSurvey/demographicInfo.gsp")
			action{
				def branch = Branch.get(params.branchId)
				flow.branch = branch
				return next()
			}
			on("next").to "demographicInfo"
		}
		
		demographicInfo {
			on("next"){DemographicCommand cmd ->
				flow.demographicCommand = cmd
				if(cmd.hasErrors()){
					return demographicInfo()
				}
			}.to "financialProductsInfo"
		}
		
		financialProductsInfo{
			on("next"){FinancialProductsInfoCommand cmd ->
				flow.financialProductsInfoCommand = cmd
				if(cmd.hasErrors()){
					return demographicInfo()
				}
				def questions = flow.demographicCommand.createQuestionAndAnswers()
				def command = surveyService.fireMidSummaryRule(questions)
				flow.midSummaryCommand = command
			}.to "midSummary"
		}
		
		midSummary{
			on("next"){
				// init goal selection				
				if(!flow.goalSelectionCommand){
					flow.goalSelectionCommand = surveyMidSummaryService.initGoalSelectionCommand()
				}
				println "*************************midSummary**************************:"+flow.goalSelectionCommand
			}.to "goalSelection"
		}
		
		goalSelection{
			on("next"){
				// set up selected value every time
				flow.goalSelectionCommand = surveyMidSummaryService.initGoalSelectionCommand()
				for( value in params.items){
					for(item in flow.goalSelectionCommand.items){
						if(item.value == value)
							item.isSelected = true
					}
				}	
				
				def questions = flow.demographicCommand.createQuestionAndAnswers()
				def command = surveyService.fireFinalSummaryCommand(questions)
				flow.finalSummaryCommand = command
					
			}.to "finalSummary"
		}
		
		finalSummary{
			
		}

	}
	
	def tengdaBankSurveyFlow = {
		initialize{
			action{
				def branch = Branch.get(params.branchId)
				flow.branch = branch
				return success()
			}
			on ("success").to "demographicInfo"
		}
		
		
		demographicInfoAction{
			action{
				flow.demographicCommand = params.demographicCommand
				return demographicInfo()
			}
			on("demographicInfo").to "demographicInfo"
			
		}
		demographicInfo {
			//render(view:"/shared/startSurvey/demographicInfo.gsp")
			on("demographicInfo").to "demographicInfoAction"
			on("next"){DemographicCommand cmd ->
				flow.demographicCommand = cmd
				if(cmd.hasErrors()){
					return demographicInfo()
				}
			}.to "financialProductsInfo"
		}
	

		financialProductsInfoAction{
			action{
				println "financialProductsInfoAction---------------------------"+params
				return financialProductsInfo()
			}
			on("financialProductsInfo").to "financialProductsInfo"
		}

		financialProductsInfo{
			//render(view:"/shared/startSurvey/financialProductsInfo.gsp")
			on("financialProductsInfo").to "financialProductsInfoAction"
			on("next"){DemographicCommand cmd ->
				flow.demographicCommand = cmd
				if(cmd.hasErrors()){
					return financialProductsInfo()
				}
				println "financialProductsInfo***********************************"+cmd.sex
			}.to "demographicInfo"
		}
		
		
		
		showSurvey {
			render(view:"/shared/startSurvey/showSurvey.gsp")
			action{
				flow.demographicCommand = params.demographicCommand
				println "demographicInfoAction***********************************"+params
				return success()
			}
			on("next").to "demographicInfo"
		}
		
	}	
}

