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
}
