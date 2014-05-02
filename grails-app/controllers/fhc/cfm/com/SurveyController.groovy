package fhc.cfm.com

import org.junit.After;

import com.sun.java.util.jar.pack.Instruction.Switch;

import groovy.swing.impl.DefaultAction;

class SurveyController {
	
	static allowedMethods = [index: ['GET','POST']]
	
	def surveyService
	//def flowManagerService
	//FlowInfo flowInfo
	def index() {
		// default branch
		
		if(!params.branchId)
			params.branchId = 1
		def branch = Branch.get(params.branchId)
		render(view: "index", model: [branch: branch])
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
		println "*************************startSuvey**************************:"+branch.survey.name 
		redirect(action:branch.survey.name, params: [branchId: params.branchId])
	}
	
	def defaultBankSurveyFlow = {
		defaultBankSurveyFlowStart {
			//render(view:"/shared/startSurvey/demographicInfo.gsp")
			action{
				def branch = Branch.get(params.branchId)
				flow.branch = branch
				surveyService.pageStatisticUpdate("demographicInfo",flow.branch.survey)
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
				surveyService.pageStatisticUpdate("financialProductsInfo",flow.branch.survey)
			}.to "financialProductsInfo"
		}
		
		financialProductsInfo{
			on("next"){FinancialProductsInfoCommand cmd ->
				println "*************************FinancialProductsInfoCommand**************************:"+cmd.checkingAccountsCount
				flow.financialProductsInfoCommand = cmd
				if(cmd.hasErrors()){
					return demographicInfo()
				}
				def questions = []
				questions.addAll(flow.demographicCommand.createQuestionAndAnswers())
				questions.addAll(flow.financialProductsInfoCommand.createQuestionAndAnswers())
				def command = surveyService.fireMidSummaryRule(questions)
				flow.midSummaryCommand = command
				surveyService.pageStatisticUpdate("midSummary",flow.branch.survey)
			}.to "midSummary"
		}
		
		midSummary{
			on("next"){
				// init goal selection				
				if(!flow.goalSelectionCommand){
					flow.goalSelectionCommand = surveyService.initGoalSelectionCommand()
				}
				surveyService.pageStatisticUpdate("goalSelection",flow.branch.survey)
				println "*************************midSummary**************************:"+flow.goalSelectionCommand
			}.to "goalSelection"
		}
		
		goalSelection{
			on("next"){
				// set up selected value every time
				flow.goalSelectionCommand = surveyService.initGoalSelectionCommand()
				flow.goalSelectionSteps = []
				def myItems = []
				if(params.items)
					myItems.addAll(params.items)
				for(value in myItems){
					println "*************************single item************************:"+value
					for(item in flow.goalSelectionCommand.items){
						if(item.value == value)
							item.isSelected = true
							flow.goalSelectionSteps.add(value)
					}
				}
				flow.goalSelectionSteps.add("financialProductsInfo")  //this is for the last page
				flow.goalSelectionSteps.unique()
			}.to "goalSelectionStepAction" //"finalSummary" //flow.finalSummaryCommand.items[0].value //"finalSummary"
		}
		
		
		goalSelectionStepAction{
			action{
				println "goalSelectionStepAction---------------------------"+flow.goalSelectionSteps
				if(flow.goalSelectionSteps.size() >0){
					def nextStep = flow.goalSelectionSteps[0]
					flow.goalSelectionSteps.remove(0)
					switch (nextStep){
						case "financialProductsInfo":
							println "goalSelectionAction---last page-----financialProductsInfo-------------------"
							def questions = flow.demographicCommand.createQuestionAndAnswers()
							def command = surveyService.fireFinalSummaryCommandRule(questions)
							flow.finalSummaryCommand = command
							return financialProductsInfo()
						case "goal_reduce_credit":
							return goal_reduce_credit()
						case "goal_save_collage" :
							return goal_save_collage()
						case "goal_buy_house" :
							return goal_buy_house()
						/*	
						case "goal_student_loans" :
							return goal_student_loans()
						case "goal_save_boat" :
							return goal_save_boat()
						case "goal_buy_car" :
							return goal_buy_car()
						case "goal_family_trust" :
							return goal_family_trust()
						case "goal_retire" :
							return goal_retire()
						case "goal_remodel_house" :
							return goal_remodel_house()
						case "goal_pay_off_car" :
							return goal_pay_off_car()
						case "goal_increase_income" :
							return goal_increase_income()
						*/
					}
					
				}
			}
			on("financialProductsInfo"){
				surveyService.pageStatisticUpdate("finalSummary",flow.branch.survey)
			}.to "finalSummary"	
			
			on("goal_reduce_credit"){
				surveyService.pageStatisticUpdate("goal_reduce_credit",flow.branch.survey)
			}.to "goal_reduce_credit"
			on("goal_save_collage"){
				surveyService.pageStatisticUpdate("goal_save_collage",flow.branch.survey)
			}.to "goal_save_collage"
			on("goal_buy_house"){
				surveyService.pageStatisticUpdate("goal_buy_house",flow.branch.survey)
			}.to "goal_buy_house"
		}
		
		goal_reduce_credit{
			render(view:"/shared/goalSelection/goal_reduce_credit.gsp")
			on("next").to "goalSelectionStepAction"
		}
		goal_save_collage{
			render(view:"/shared/goalSelection/goal_save_collage.gsp")
			on("next").to "goalSelectionStepAction"
		}
		goal_buy_house{
			render(view:"/shared/goalSelection/goal_buy_house.gsp")
			on("next").to "goalSelectionStepAction"
		}
		/*
		goal_buy_car{
			on("next").to "goalSelectionAction"
		}
		*/
		finalSummary{
			on("next"){FinalSummaryCommand cmd ->
				println "FinalSummaryCommand--------email-------------------"+cmd.email
				println "FinalSummaryCommand--------telepressence-------------------"+cmd.telepressence
				if(cmd.hasErrors()){
					return finalSummary()
				}
				/*
				sendMail {   
				  from "service@softwarehousecall.com"  
				  to "twang@cfms4.com"  
				  subject "Hello Fred"+cmd.email  
				  body 'How are you?</br>' +cmd.telepressence+":asdasdsadsa"+flow.demographicCommand.age
				}
				*/
				def questions = []
				questions.addAll(flow.demographicCommand.createQuestionAndAnswers())
				questions.addAll(flow.financialProductsInfoCommand.createQuestionAndAnswers())
				surveyService.saveUserSurvey(questions, cmd)
				surveyService.pageStatisticUpdate("endSurvey",flow.branch.survey)
			}.to "endSurvey"
		}
		
		endSurvey{
			redirect(action:"index", params: [branchId: flow.branch.branchId])
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

