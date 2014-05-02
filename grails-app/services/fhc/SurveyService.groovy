package fhc

import grails.transaction.Transactional

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.authentication.dao.NullSaltSource
import grails.plugin.springsecurity.ui.RegistrationCode

import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.rule.FactHandle;

import fhc.cfm.com.*;
import net.bull.javamelody.*;
import com.cfm.auth.*;

import grails.transaction.Transactional

@Transactional
class SurveyService {

    def serviceMethod() {

    }
	
	def testRules(){
			def kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			kbuilder.add(ResourceFactory.newClassPathResource("rules/example.drl"),ResourceType.DRL)
	 
			//kbuilder.add(newReaderResource(new StringReader(drl)), DRL)
			def kbase = KnowledgeBaseFactory.newKnowledgeBase();
			kbase.addKnowledgePackages(kbuilder.knowledgePackages)
			def ksession = kbase.newStatefulKnowledgeSession()
			
			ksession.fireAllRules()
			ksession.dispose()
		}
		
		def MidSummaryCommand fireMidSummaryRule(List<Question> questions){
			println 'fireMidSummaryRule-------------start----------------'+questions
			
			def kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			kbuilder.add(ResourceFactory.newClassPathResource("rules/summary.drl"),ResourceType.DRL)
			def kbase = KnowledgeBaseFactory.newKnowledgeBase();
			kbase.addKnowledgePackages(kbuilder.knowledgePackages)
			def ksession = kbase.newStatefulKnowledgeSession()
			for(Question question in questions){
				println 'insert question : '+ question.name
				ksession.insert(question)
			}
			def midSummaryCommand = new MidSummaryCommand()
			ksession.insert(midSummaryCommand)
			println 'insert midSummaryCommand : '
			ksession.fireAllRules()
			ksession.dispose()
			
			println 'fireMidSummaryRule-------------end----------------: '
			return midSummaryCommand
		}
		
		def FinalSummaryCommand fireFinalSummaryCommandRule(Question[] questions){
			println 'fireFinalSummaryCommand-------------start----------------'+questions
			
			def kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			kbuilder.add(ResourceFactory.newClassPathResource("rules/summary.drl"),ResourceType.DRL)
			def kbase = KnowledgeBaseFactory.newKnowledgeBase();
			kbase.addKnowledgePackages(kbuilder.knowledgePackages)
			def ksession = kbase.newStatefulKnowledgeSession()
			for(Question question in questions){
				println 'insert question : '+ question.name
				ksession.insert(question)
			}
			def finalSummaryCommand = new FinalSummaryCommand()
			ksession.insert(finalSummaryCommand)
			println 'insert finalSummaryCommand : '
			ksession.fireAllRules()
			ksession.dispose()
			
			println 'fireFinalSummaryCommand-------------end----------------: '
			return finalSummaryCommand
		}
		
		
		def GoalSelectionCommand initGoalSelectionCommand(){
			def item = new Item(name:"reduce credit card debt",value:"goal_reduce_credit",isSelected:false)
			def goalSelectionCommand = new GoalSelectionCommand()
			goalSelectionCommand.items.add(item)
			
			item = new Item(name:"save for collage",value:"goal_save_collage",isSelected:false)
			goalSelectionCommand.items.add(item)
	
			item = new Item(name:"Buy a house",value:"goal_buy_house",isSelected:false)
			goalSelectionCommand.items.add(item)
			
			item = new Item(name:"pay off student loans",value:"goal_student_loans",isSelected:false)
			goalSelectionCommand.items.add(item)
			
			item = new Item(name:"save for a boat",value:"goal_save_boat",isSelected:false)
			goalSelectionCommand.items.add(item)
			
			item = new Item(name:"buy a car",value:"goal_buy_car",isSelected:false)
			goalSelectionCommand.items.add(item)
			
			item = new Item(name:"establish family trust",value:"goal_family_trust",isSelected:false)
			goalSelectionCommand.items.add(item)
			
			item = new Item(name:"retire",value:"goal_retire",isSelected:false)
			goalSelectionCommand.items.add(item)
	
			item = new Item(name:"remodel house",value:"goal_remodel_house",isSelected:false)
			goalSelectionCommand.items.add(item)
			
			item = new Item(name:"pay off my car",value:"goal_pay_off_car",isSelected:false)
			goalSelectionCommand.items.add(item)
			
			item = new Item(name:"increase income",value:"goal_increase_income",isSelected:false)
			goalSelectionCommand.items.add(item)
	
			return goalSelectionCommand
		}
		
		def BranchStatisticsCommand getBranchCounterRequest(UserBranch userBranch){
			def branchStatisticsCommand = new BranchStatisticsCommand()
			
			def context = new FilterContext()
			context.initCounters()
			def collector = context.getCollector()
			
			def httpCounter = collector.getCounterByName(Counter.HTTP_COUNTER_NAME)
			def counterRequests = httpCounter.getOrderedRequests()
			
			//def springCounter = collector.getCounterByName("spring")
			//def springCounterRequests = springCounter.getOrderedRequests()
			//branchStatisticsCommand.counterRequests.addAll(springCounterRequests)
			
			
			def surveyName = userBranch.branch.survey.name
			for (counterRequest in counterRequests){
				if(counterRequest.getName().contains(surveyName)){
					branchStatisticsCommand.counterRequests.add(counterRequest)
					/*
					for(entry in counterRequest.getChildRequestsExecutionsByRequestId().entrySet()){
						def requestId = entry.getKey();
						println 'child request id: ------: '+requestId
					}
					*/
					//println 'child request : ------------------------: '+counterRequest.getChildRequestsExecutionsByRequestId()
					//println 'child request : ------------------------: '+counterRequest.childHits
				}
			}
			println "http counterRequest------getBranchCounterRequest---------------------:"+branchStatisticsCommand.counterRequests.size()
			return branchStatisticsCommand
		}
		
		def saveUserSurvey(List<Question> questions,FinalSummaryCommand cmd){
			//save survey
			def survey = new Survey(name: "customer saved")
			survey.questions = questions
			survey.save()
			// first check if user exists as email
			def user = User.where{
				username == cmd.email
			}.find()
			
			def userSurvey
			if(user){
				userSurvey= UserSurvey.where {
					user == user
				}.find()
				if(userSurvey){
					userSurvey.survey.deleteAll()
				}else{
					userSurvey = new UserSurvey()
				}
			}else{
				//create user
				def role =   Role.where{
					authority == "ROLE_ADMIN"
				}.find()
				user = new User(username: cmd.email, password: cmd.telepressence,phoneNumber:cmd.telepressence,enabled: true).save()
				println "------start---------------------saveUserSurvey save? user?"+user.username
				def userRole = new UserRole()
				userRole.user = user
				userRole.role = role
				userRole.save()
				
				userSurvey = new UserSurvey()
			}
			userSurvey.user = user
			userSurvey.survey = survey
			userSurvey.save()
		}
		
		def pageStatisticUpdate(String pageId, Survey survey){
			def pageStatistic = PageStatistic.where{
				pageId == pageId &&
				survey == survey
			}.find()
			if(pageStatistic){
				pageStatistic.odometer = pageStatistic.odometer+1
			}else{
				pageStatistic = new PageStatistic()
				pageStatistic.pageId = pageId
				pageStatistic.survey = survey
				pageStatistic.odometer = 1
			}
			pageStatistic.save()
		}
}
