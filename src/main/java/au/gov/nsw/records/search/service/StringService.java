package au.gov.nsw.records.search.service;

public class StringService {

	public static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	public static String formatEacCpf(String field){
		
		field = field.replaceAll("\n", "</p><p>");
    field = field.replaceAll("<br />", "</p><p>");  
    field = field.replaceAll("nbsp;", " ");
    field = field.replaceAll("&middot;", "-");
    field = field.replaceAll("&ndash;", "-");
    field = field.replaceAll("&emdash;", "-");
    field = field.replaceAll("&quot;", "\"");
    field = field.replaceAll("&hellip;", "...");
    //field = field.replaceAll("/&[lr][sd]quo;/", "'");
    field = field.replaceAll("&rsquo;", "'");
    field = field.replaceAll("&ldquo;", "'");
    field = field.replaceAll("/<p>\\s*<\\/p>/", "");
    //field = field.replaceAll("/<\\/?(?(em|[\\/pbi])).*?>/", ""); // # ... HErE bE DRAgONs [should strip any html except - p, em, b, i tags. Maybe requiring nokogiri/loofah would be saner]
    field = field.replaceAll("<i>", "<span style=\"font-style:italic\">");
    field = field.replaceAll("</i>", "</span>");
    field = field.replaceAll("<em>", "<span style=\"font-style:italic\">");
    field = field.replaceAll("</em>", "</span>");
    field = field.replaceAll("<b>", "<span style=\"font-weight:bold\">");
    field = field.replaceAll("</b>", "</span>");
    field = field.replaceAll("<p></p>", "");
    field = field.replaceAll("& ", "&amp; ");
		return "<p>" + field + "</p>";
	}
}
