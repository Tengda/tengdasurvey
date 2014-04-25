package com.jigar.workflow

class WorkController {

	def workFlow = {

		initialize {
			render(view:"/shared/work/step1")
			on ("success"){
				println "***************************************1231232"
			}.to "step1"
		}
		
		step1 {
			render(view:"/shared/work/step1")
			println "***************************************dfdfdf"
			on("step1").to "step1Action"
			on("step2"){NameCommand cmd ->
				if(cmd.hasErrors()) {
					flow.nameCmd = cmd
					return step1()
				}
			}.to "step2Action"
			on("step3").to "step2"
			on("step1Menu").to "step1"
			on("step3Menu").to "step1"
			on("step2Menu").to "step1"
		}
		
		step1Action {
			action{ return step1() }
			on("step1").to "step1"
		}
		
		step2 {
			println "***************************************2222222222222222"
			render(view:"/shared/work/step2")
			on("step1").to "step1Action"
			on("step2").to "step2Action"
			on("step3"){AddressCommand cmd ->
				if(cmd.hasErrors()) {
					flow.addressCmd = cmd
					return step2()
				}
			}.to "step3Action"
			on("step1Menu").to "step1"
			on("step2Menu").to "step2"
			on("step3Menu").to "step2"
		}
		
		step2Action {
			render(view:"/shared/work/step2")
			action{
				flow.fname=params.fname
				flow.lname=params.lname
				return step2()
			}
			on("step2").to "step2"
			on("step1").to "step1"
		}
		
		step3 {
			render(view:"/shared/work/step3")
			on("submit"){RegionCommand cmd ->
				if(cmd.hasErrors()) {
					flow.regionCmd = cmd
					return step3()
				}
			}.to "successPageAction"
			on("step1").to "step1Action"
			on("step2").to "step2Action"
			on("step3").to "step3Action"
			on("step1Menu").to "step1"
			on("step2Menu").to "step2"
			on("step3Menu").to "step3"
		}
		
		step3Action {
			action{
				flow.address=params.address
				flow.city=params.city
				return step3()
			}
			on("step2").to "step2"
			on("step3").to "step3"
		}
		
		successPage{
			render(view:"/shared/work/successPage")
			on("step1Menu").to "successPage"
			on("step2Menu").to "successPage"
			on("step3Menu").to "successPage"
		}
		
		successPageAction {
			action{
				flow.state=params.state
				flow.country=params.country
				return successPage()
			}
			on("successPage").to "successPage"
			on("step3").to "step3"
		}
	}
	
	def index = { redirect(action: "work") }
}

class NameCommand implements Serializable{
	String fname
	String lname
	static constraints = {
		fname(blank: false)
		lname(blank: false)
	}
}

class AddressCommand implements Serializable{
	String address
	String city
	static constraints = {
		address(blank: false)
		city(blank: false)
	}
}

class RegionCommand implements Serializable{
	String state
	String country
	static constraints = {
		state(blank: false)
		country(blank: false)
	}
}