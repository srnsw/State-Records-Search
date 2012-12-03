package au.gov.nsw.records.search.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import au.gov.nsw.records.search.service.DateHelper;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
@Table(name="organisations_view")
public class Organisation{
	
	@Expose
  @Id
	@Column(name="Organisation_number")
	private int organisationNumber;
	
	@Expose
  @Column(name="Organisation_title")
	private String title;

	@Expose
  @Column(name="Creation")
	private String creation;
	
	@Expose
  @Column(name="Abolition")
	private String abolition;
	
	@Expose
  @Column(name="CO_number")
	private String coNumber;
	
	@Expose
  @Column(name="Administrative_history_note")
	private String administrativeHistoryNote;

	@Expose
  @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "Last_amendment_date")
	private Date lastAmendmentDate;
  
	@Expose
  @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "Start_date")
	private Date startDate;

	@Expose
  @Column(name = "Start_date_qualifier")
	private String startDateQualifier;

	@Expose
  @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "End_date")
	private Date endDate;

	@Expose
  @Column(name = "End_date_qualifier")
	private String endDateQualifier;
	
	public String getDateRange() {
		return DateHelper.dateRange(startDateQualifier, startDate, endDateQualifier, endDate);
	}
	
	@OneToMany(mappedBy="organisationId")
	private List<OrganisationLinkSucceeding> succeeding;
	
	@OneToMany(mappedBy="organisationId")
	private List<OrganisationLinkPreceding> preceding;
	
	@OneToMany(mappedBy="organisationId")
	private List<OrganisationLinkAgency> agencies;
	
	public int getId(){
		return organisationNumber;
	}
	 public String getJsonString(){
  	 return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(this); 
   }
}
