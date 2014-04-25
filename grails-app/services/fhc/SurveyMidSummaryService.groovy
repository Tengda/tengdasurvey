package fhc

import grails.transaction.Transactional


import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.rule.FactHandle;

import fhc.cfm.com.*;

@Transactional
class SurveyMidSummaryService {

    def serviceMethod() {

    }
	
	def testRules(){
	
		println '1231231233333------------'
		def kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("rules/example.drl"),ResourceType.DRL)
 
		//kbuilder.add(newReaderResource(new StringReader(drl)), DRL)
		def kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.knowledgePackages)
		def ksession = kbase.newStatefulKnowledgeSession()
		
		ksession.fireAllRules()
		ksession.dispose()
		println '1231231233333--------done----'
	}
	
	def MidSummaryCommand fireMidSummaryRule(Question[] questions){
		println 'fireMidSummaryRule-------------start----------------'+questions
		
		def kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("rules/midSummary.drl"),ResourceType.DRL)
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
		
		println 'fireMidSummaryRule-------------end----------------: '+midSummaryCommand.data
		return midSummaryCommand
	}
	
}
