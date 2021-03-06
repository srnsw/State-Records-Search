package au.gov.nsw.records.search.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DateHelper {

	private static SimpleDateFormat yearOnlyFormat = new SimpleDateFormat("yyyy");
	private static SimpleDateFormat monthAndYearOnlyFormat = new SimpleDateFormat("MM/yyyy");
	private static SimpleDateFormat fullFormat =  new SimpleDateFormat("dd/MM/yyyy");
	private static List<String> staticQualifiers = new ArrayList<String>(Arrays.asList("?", "circa", "c", "c."));
	
	public static String dateRange(String startQualifier, String start, String endQualifier, String end) {
		start = parseDate(startQualifier, start);
		end = parseDate(endQualifier, end);
  	String dateRange = "";
  	if (start!=null){
  		dateRange = start;
  		if (end!=null){
  			dateRange += " to " + end;
  		}else{
  			dateRange += " +";
  		}
  	}else if(end!=null){
  		dateRange = "- " + end;
  	}else{
  		dateRange = "unknown";
  	}
  	return dateRange;
  }
	
	private static String parseDate(String qualifier, String date){
  	if (date!=null){
  		if (qualifier==null){
  			// format dd/mm/yyyy
  			return date;
  		}else if (qualifier.equalsIgnoreCase("month and year only")){
  			return date.substring(date.indexOf("/"));
  		}else if (qualifier.equalsIgnoreCase("year only")){
  			// format yyyy
  			return date.substring(date.lastIndexOf("/"));
  		}else{
  			String s = staticQualifiers.contains(qualifier)? "c. " : qualifier + " ";
  			// format dd/mm/yyyy
  			return s += date;
  		}
  	}
  	return null;
  }
	
	public static String dateRange(String startQualifier, Date start, String endQualifier, Date end) {
  	String dateRange = "";
  	String startDate = parseDate(startQualifier, start);
  	String endDate = parseDate(endQualifier, end);
  	if (startDate!=null){
  		dateRange = startDate;
  		if (endDate!=null){
  			dateRange += " to " + endDate;
  		}else{
  			dateRange += " +";
  		}
  	}else if(endDate!=null){
  		dateRange = "- " + endDate;
  	}else{
  		dateRange = "unknown";
  	}
  	return dateRange;
  }
  
  public static String parseDate(String qualifier, Date date){
  	if (date!=null){
  		if (qualifier==null){
  			// format dd/mm/yyyy
  			return fullFormat.format(date);
  		}else if (qualifier.equalsIgnoreCase("year only")){
  			// format yyyy
  			return yearOnlyFormat.format(date);
  		}else if (qualifier.equalsIgnoreCase("month and year only")){
  			// format yyyy
  			return monthAndYearOnlyFormat.format(date);
  		}else{
  			String s = staticQualifiers.contains(qualifier.toLowerCase())? "c. " : qualifier + " ";
  			// format dd/mm/yyyy
  			return s += fullFormat.format(date);
  		}
  	}
  	return null;
  }
  
  public static String getYearString(Date date){
  	if (date!=null){
  		return yearOnlyFormat.format(date);
  	}
  	return "";
  }
  
  public static Date parseDate(String date) throws ParseException{
  	SimpleDateFormat parserSDF=new SimpleDateFormat("yyyy-MM-dd");
		return parserSDF.parse(date);
  }
}
