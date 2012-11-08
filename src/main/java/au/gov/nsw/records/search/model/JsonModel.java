package au.gov.nsw.records.search.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class JsonModel {

	private Gson gson;

   public JsonModel(){
  	gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
  	
   }
   
   public String getJsonString(){
  	 return gson.toJson(this); 
   }
}
