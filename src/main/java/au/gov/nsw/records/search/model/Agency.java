package au.gov.nsw.records.search.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.facet.index.CategoryDocumentBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import au.gov.nsw.records.search.service.DateHelper;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
@Table(name="agencies_view")
public class Agency {
	
	@Id
	@Column(name="Agency_number")
	private int agencyNumber;
	
	@Column(name="Agency_title")
	private String title;
	
	@Column(name="Category")
	private String category;
	
	@Column(name="Creation")
	private String creation;
	
	@Column(name="Abolition")
	private String abolition;
	
	@Column(name="Administrative_history_note")
	private String administrativeHistoryNote;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "Start_date")
	private Date startDate;

	@Column(name = "Start_date_qualifier")
	private String startDateQualifier;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "End_date")
	private Date endDate;

	@Column(name = "End_date_qualifier")
	private String endDateQualifier;
	
	@OneToMany(mappedBy="agencyId")
	private List<AgencyLinkFunction> functions;
	
	@OneToMany(mappedBy="agencyId")
	private List<AgencyLinkOrganisation> organisations;
	
	@OneToMany(mappedBy="agencyId")
	private List<AgencyLinkPerson> persons;
	
	@OneToMany(mappedBy="agencyId")
	private List<AgencyLinkSeriesControlled> seriesControlled;
	
	@OneToMany(mappedBy="agencyId")
	private List<AgencyLinkSeriesCreated> seriesCreated;
	
	//
	@OneToMany(mappedBy="agencyId")
	private List<AgencyLinkPreceding> preceding;
	
	@OneToMany(mappedBy="agencyId")
	private List<AgencyLinkRelated> related;
	
	@OneToMany(mappedBy="agencyId")
	private List<AgencyLinkSubordinate> subordinates;
	
	@OneToMany(mappedBy="agencyId")
	private List<AgencyLinkSucceeding> succeeding;
	
	@OneToMany(mappedBy="agencyId")
	private List<AgencyLinkSuperior> superiors;
	
	public String getDateRange() {
		return DateHelper.dateRange(startDateQualifier, startDate, endDateQualifier, endDate);
	}
	
	public int getId(){
		return agencyNumber;
	}
	
	public static List<Document> getIndexData(List<Agency> agencies, CategoryDocumentBuilder builder){
		List<Document> agenciesIndex = new ArrayList<Document>();
  	for (Agency agency: agencies){
  		Document doc = new Document();
  		Field f = new Field("title", agency.getTitle(), Field.Store.YES, Field.Index.ANALYZED);
  		f.setBoost(2.0f);
  		doc.add(f);
  		doc.add(new Field("content", agency.getAdministrativeHistoryNote(), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("type", "agencies", Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("url", String.format("/agencies/%d",agency.getAgencyNumber()), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("startyear", DateHelper.getYearString(agency.getStartDate()), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("endyear",  DateHelper.getYearString(agency.getEndDate()), Field.Store.YES, Field.Index.ANALYZED));
  		agenciesIndex.add(doc);
  	}
  	return agenciesIndex;
	}
}
