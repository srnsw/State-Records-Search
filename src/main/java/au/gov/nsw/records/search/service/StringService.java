package au.gov.nsw.records.search.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

public class StringService {

	public static boolean isNumeric(String str)
	{
		if (str==null || str.isEmpty()){
			return false;
		}
		Pattern p = Pattern.compile( "([0-9]*)+" );
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	public static String formatEacCpf(String field){
		
		field = field.replaceAll("\n", "=newline=");
		field = field.replaceAll("<br />", "=newline=");
    field = field.replaceAll("<br/>", "=newline=");
    field = field.replaceAll("<br>", "=newline=");
    field = field.replaceAll("<i>", "=i=");
    field = field.replaceAll("</i>", "=/i=");
    field = field.replaceAll("<em>", "=em=");
    field = field.replaceAll("</em>", "=/em=");
    field = field.replaceAll("<b>", "=b=");
    field = field.replaceAll("</b>", "=/b=");
    
    String resp = Jsoup.parse(field).text();
    resp = resp.replaceAll("=i=", "<span style=\"font-style:italic\">");
    resp = resp.replaceAll("=/i=", "</span>");
    resp = resp.replaceAll("=em=", "<span style=\"font-style:italic\">");
    resp = resp.replaceAll("=/em=", "</span>");
    resp = resp.replaceAll("=b=", "<span style=\"font-weight:bold\">");
    resp = resp.replaceAll("=/b=", "</span>");
    resp = resp.replaceAll("=newline=", "</p><p>");
    resp = resp.replaceAll("&", "&amp; ");
		return "<p>" + resp + "</p>";
	}
}
