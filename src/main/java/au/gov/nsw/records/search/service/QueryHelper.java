package au.gov.nsw.records.search.service;

import java.util.Date;

public class QueryHelper {

	public static String buildAdditionalQuery(Date from, Date until){
		 String additionalCondition = "";
     if (from != null || until != null) {
         additionalCondition += " where";
         if (from != null) {
             additionalCondition += String.format(" lastAmendmentDate > :from");
         }
         if (from != null && until != null) {
             additionalCondition += " AND ";
         }
         if (until != null) {
             additionalCondition += String.format(" lastAmendmentDate < :until");
         }
     }
		return additionalCondition;
	}
	
	public static void populateQuery(){
		
	}
}
