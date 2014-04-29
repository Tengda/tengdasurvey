package com.cfm.auth

class Role {

	String authority
	String description
	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
