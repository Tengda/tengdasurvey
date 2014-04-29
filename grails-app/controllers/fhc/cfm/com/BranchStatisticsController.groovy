package fhc.cfm.com

import net.bull.javamelody.*;
import com.cfm.auth.*;

class BranchStatisticsController {
	def springSecurityService
	def surveyService
    def index() { 
		
		println "current user: "+springSecurityService.getCurrentUser()
		
		def userBranchs = UserBranch.where {
			user == springSecurityService.getCurrentUser()
		}
		println "current userBranch: "+userBranchs.first().branch.name
		
		def branchStatisticsCommand = surveyService.getBranchCounterRequest(userBranchs.first())
		
		render(view: "index", model: [branch: userBranchs.first().branch,branchStatisticsCommand:branchStatisticsCommand])
	}
}
