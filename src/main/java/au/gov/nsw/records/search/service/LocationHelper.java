package au.gov.nsw.records.search.service;

import java.util.HashMap;
import java.util.Map;

public class LocationHelper {

	private static Map<String, String> locationsMap;

	public static String getSimpleLocation(String location){
		if (locationsMap==null){
			locationsMap = new HashMap<String, String>();
			locationsMap.put("Charles Sturt", "Charles Sturt University Regional Archives, Wagga Wagga");
			locationsMap.put("University of Newcastle","Auchmuty Library, University of Newcastle");
			locationsMap.put("New England","University of New England and Regional Archives, Armidale");
			locationsMap.put("Outback","Outback Archives, Broken Hill City Library");
			locationsMap.put("Police Force","New South Wales Police Force");
			locationsMap.put("Newcastle Region","Newcastle Region Library");
			locationsMap.put("Mitchell","Mitchell Library, State Library of NSW");
			locationsMap.put("University of Western Sydney","University of Western Sydney, Hawkesbury");
			locationsMap.put("University of Wollongong","Library, University of Wollongong");
		}
		
		for (String loc: locationsMap.keySet()){
			if (location.contains(loc)){
				return locationsMap.get(loc);
			}
		}
		// default location
		return "Western Sydney Records Centre, Kingswood";
	}
	
	public static String getAddress(String location){
		String loc = getSimpleLocation(location);
		if (loc.startsWith("Western")){
			return "Western Sydney Records Centre \n143 O'Connell Street KINGSWOOD NSW";
		}else if (loc.startsWith("University of New England")){
			return "University of New England Heritage Centre \nC. B. Newling Campus \nCnr Dangar and Kentucky Sts ARMIDALE NSW";
		}else if (loc.startsWith("Outback")){
			return "Outback Archives Broken Hill City Library \nBlende Street BROKEN HILL NSW";
		}else if (loc.startsWith("Newcastle")){
			return "Newcastle Region Library \nLaman Street NEWCASTLE NSW";
		}else if (loc.startsWith("Charles Sturt")){
			return "Charles Sturt University Regional Archives \nBlakemore Building Hely Avenue WAGGA WAGGA NSW";
		}else if (loc.startsWith("Library, University of Wollongong")){
			return "The Library, University of Wollongong \nNorthfields Avenue WOLLONGONG NSW";
		}
		
		return "";
	}
}
