package fhc.cfm.com

import org.junit.After;

import com.sun.java.util.jar.pack.Instruction.Switch;

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
					flow.goalSelectionCommand = surveyService.initGoalSelectionCommand()
				}
				println "*************************midSummary**************************:"+flow.goalSelectionCommand
			}.to "goalSelection"
		}
		
		goalSelection{
			on("next"){
				// set up selected value every time
				flow.goalSelectionCommand = surveyService.initGoalSelectionCommand()
				flow.goalSelectionSteps = []
				for( value in params.items){
					for(item in flow.goalSelectionCommand.items){
						if(item.value == value)
							item.isSelected = true
							flow.goalSelectionSteps.add(value)
					}
				}	
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
					
				}else{
					println "goalSelectionAction--------financialProductsInfo-------------------"
					def questions = flow.demographicCommand.createQuestionAndAnswers()
					def command = surveyService.fireFinalSummaryCommand(questions)
					flow.finalSummaryCommand = command
					return financialProductsInfo()
				}
				
			}
			on("financialProductsInfo").to "finalSummary"	
			
			on("goal_reduce_credit").to "goal_reduce_credit"
			on("goal_save_collage").to "goal_save_collage"
			on("goal_buy_house").to "goal_buy_house"
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
				sendMail {     
				  to "twang@cfms4.com"     
				  subject "Hello Fred"     
				  body 'How are you?' 
				}
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

