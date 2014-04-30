package fhc.cfm.com

class FinancialProductsInfoCommand implements Serializable{

	String salary;
	String checkingAccountsCount;
	String checkingAccountsAmount;
	static constraints = {
		//sex(blank: false)
	}

	Question[] createQuestionAndAnswers(){
		def questions = []
		if(salary){
			def question = new Question()
			question.setName("salary")
			questions.add(createAnswer(question,salary))
		}
		if(checkingAccountsCount){
			def question = new Question()
			question.setName("checkingAccountsCount")
			questions.add(createAnswer(question,checkingAccountsCount))
		}
		if(checkingAccountsAmount){
			def question = new Question()
			question.setName("checkingAccountsAmount")
			questions.add(createAnswer(question,checkingAccountsAmount))
		}
		return questions
	}
	def Question createAnswer(Question question, String value){
		def answer = new Answer()
		answer.value = value
		question.answer = answer.save()
		question.save()
		println "get question name: "+question.name
		println "and id: "+question.id
		return question
	}
}
