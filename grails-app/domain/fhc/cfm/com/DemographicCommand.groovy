package fhc.cfm.com

class DemographicCommand implements Serializable{
	
	String sex;
	String marital_status;
	String age;
	String kids;
	String salary;
	static constraints = {
		 //sex(blank: false)
		// marital_status(blank: false)
		// age(blank: false)
	}
	
	Question[] createQuestionAndAnswers(){
		def questions = []
		if(sex){
			def question = new Question()
			question.setName("sex")
			questions.add(createAnswer(question,sex))
		}
		if(marital_status){
			def question = new Question()
			question.setName("marital_status")
			questions.add(createAnswer(question,marital_status))
		}
		if(age){
			def question = new Question()
			question.setName("age")
			questions.add(createAnswer(question,age))
		}
		if(kids){
			def question = new Question()
			question.setName("kids")
			questions.add(createAnswer(question,kids))
		}
		if(salary){
			def question = new Question()
			question.setName("salary")
			questions.add(createAnswer(question,salary))
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