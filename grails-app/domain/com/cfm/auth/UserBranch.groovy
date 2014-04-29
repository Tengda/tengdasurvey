package com.cfm.auth
import java.io.Serializable;

import fhc.cfm.com.Branch
class UserBranch implements Serializable {
	
	private static final long serialVersionUID = 1
	
	User user
	Branch branch

	static void removeAll(User u) {
		UserBranch.where {
			user == User.load(u.id)
		}.deleteAll()
	}

	static UserBranch get(long userId, long branchId) {
		UserBranch.where {
			user == User.load(userId) &&
			branch == Branch.load(branchId)
		}.get()
	}
	
	static UserBranch create(User user, Branch branch, boolean flush = false) {
		new UserBranch(user: user, branch: branch).save(flush: flush, insert: true)
	}
	
	static boolean remove(User u, Branch b, boolean flush = false) {
		
				int rowCount = UserBranch.where {
					user == User.load(u.id) &&
					branch == Branch.load(b.id)
				}.deleteAll()
		
				rowCount > 0
			}
	
	static void removeAll(Branch b) {
		UserBranch.where {
			branch == Branch.load(b.id)
		}.deleteAll()
	}
	
	static mapping = {
		id composite: ['branch', 'user']
		version false
	}
}
