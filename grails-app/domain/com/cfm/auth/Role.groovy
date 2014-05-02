package com.cfm.auth

import java.io.Serializable;

class Role implements Serializable {

	String authority
	String description
	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
