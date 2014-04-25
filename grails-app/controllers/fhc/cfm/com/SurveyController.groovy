package fhc.cfm.com

import org.junit.After;

import groovy.swing.impl.DefaultAction;

class SurveyController {
	def surveyMidSummaryService
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
			}.to "midSummaryAction"
		}
		
		midSummary{
			on("midSummary").to "midSummaryAction"
		}
		
		midSummaryAction{
			action{
				println "midSummary:"+ flow
				def questions = flow.demographicCommand.createQuestionAndAnswers()
				println "questions--------------:"+ questions
				surveyMidSummaryService.fireMidSummaryRule(questions)
				//[results:SurveyMidSummaryService.excuteRule(flow)]
				return midSummary()
			}
			on("midSummary").to "midSummary"
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

