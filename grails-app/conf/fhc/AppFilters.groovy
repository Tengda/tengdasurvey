package fhc

import fhc.cfm.com.*

class AppFilters {

    def filters = {
        all(controller:'survey', action:'*') {
            before = {
				//println("AppOtherFilters: before action '${actionName}' is exected.");
				if (request != null) {
					//println("requestURI: " + request.requestURI +":"+request.params);
					//println("requestURI: " + request.queryString);
					//println("requestURI: " + request.requestURI);
				}
            }
			
            after = { Map model ->
                 /*
				println("AppOtherFilters: alter action '${actionName}' was exected.");
                // Create an entry for the pageId identified by controllerName and actionName if it is not yet added to the database.
                def pageId = controllerName + "::" + actionName;
                def pageStatistic = PageStatistic.findByPageId(pageId);
                if (pageStatistic == null) {
                    pageStatistic = new PageStatistic(pageId: pageId, odometer: 0);
                }
                pageStatistic.odometer++; // Increase the visited count, odometer.

                // Create an entry for the site wide identified by applicaiton context path if it is not yet added to the database.
               
				pageId = "site-grand-total";
                def siteStatistic = PageStatistic.findByPageId(pageId);
                if (siteStatistic == null) {
                    siteStatistic = new PageStatistic(pageId: pageId, odometer: 0);
                }
                siteStatistic.odometer++;   // Increase the site wide visiting count

                // Store the site and page counts in the model so that the view can use it for rendering etc...
                if (model != null) {
                    model.put("pageVisitedCount", pageStatistic.odometer);
                    model.put("siteVisitedCount", siteStatistic.odometer);
                }
                
                pageStatistic.save();*/
                //siteStatistic.save(flush: true);
            }
			/**/
            afterView = { Exception e ->
                //println("AppOtherFilters: after view");
				println("requestURI: " + request.queryString);
				println("requestURI: " + request.getMethod());
				println "actionName  :"+actionName 
				println "params  :"+params
				println "flash :" + flash 
            }
        }
    }
}
