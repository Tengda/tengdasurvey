package fhc.cfm.com

import java.io.Serializable;

import com.cfm.auth.User;

class UserSurvey implements Serializable {
	User user
	Survey survey

	static void removeAll(User u) {
		UserSurvey.where {
			user == User.load(u.id)
		}.deleteAll()
	}

	static UserSurvey get(long userId, long surveyId) {
		UserSurvey.where {
			user == User.load(userId) &&
			survey == Survey.load(surveyId)
		}.get()
	}
	
	static UserSurvey create(User user, Survey survey, boolean flush = false) {
		new UserSurvey(user: user, Survey: survey).save(flush: flush, insert: true)
	}
	
	static boolean remove(User u, Survey s, boolean flush = false) {
		
				int rowCount = UserSurvey.where {
					user == User.load(u.id) &&
					survey == Survey.load(b.id)
				}.deleteAll()
		
				rowCount > 0
			}
	
	static void removeAll(Survey b) {
		UserSurvey.where {
			survey == Survey.load(b.id)
		}.deleteAll()
	}
	
	static mapping = {
		id composite: ['survey', 'user']
		version false
	}
}
