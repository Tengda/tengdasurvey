import fhc.cfm.com.*

class BootStrap {

    def init = { servletContext ->
		// Check whether the test data already exists.
		if(!Branch.count){
			
			/*
			def question_sex = new Question(name:"sex").save()
			def question_marital_status = new Question(id:"marital_status").save()
			def question_age = new Question(id:"age").save()
			def question_kids = new Question(id:"kids").save()
			def question_debt = new Question(id:"debt").save()
			
			def questions = [question_sex,
							question_marital_status,
							question_age,
							question_kids,
							question_debt]			
			*/
			def branch = new Branch(branchId: 1, name: "Tengda's Branch", logo: "/logos/cfmlogo.jpg", url: "http://www.cfms4.com")
			def survey = new Survey(surveyId: 1, name: "defaultBankSurvey")
			
			//survey.questions = questions
			survey.save()
			
			branch.survey = survey
			branch.save()
			
			branch = new Branch(branchId: 2, name: "Tengda's Branch 1", logo: "springsource.png",url: "http://www.dbsi-inc.com")
			survey = new Survey(surveyId: 2, name: "tengdaBankSurvey")
			
			//survey.questions = questions
			survey.save()
			
			branch.survey = survey
			branch.save()

		}
    }
    def destroy = {
    }
}
