package au.gov.nsw.records.search.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
@Table(name = "accessdirections_view")
public class AccessDirection {

	@Expose
	private String scope;
	
	@Expose
	private String furtherDetails;
	
	@Expose
	private String agencyTitle;
	
	@Expose
	private String typeAccess;
	
	@Expose
	private String effect;
	
	@Expose
	private String cpaDuration;
	
	@Expose
	private String cpaReasons;
	
	@Expose
	private String location;
	
	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date authorisationDate;
	
	public String getJsonString(){
 	 return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(this); 
  }
}
