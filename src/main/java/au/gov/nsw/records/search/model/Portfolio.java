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

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import au.gov.nsw.records.search.service.DateHelper;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
@Table(name="portfolios_view")
public class Portfolio{
	
	@Expose
  @Id
	@Column(name="Portfolio_number")
	private int portfolioNumber;
	
	@Expose
  @Column(name="Portfolio_title")
	private String title;
	
	@Expose
  @Column(name="Descriptive_note")
	private String descriptiveNote;

	@Expose
  @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "Last_amendment_date")
	private Date lastAmendmentDate;
  
	@Expose
  @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "Registered_Date")
	private Date registeredDate;
	
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
	
  @OneToMany(mappedBy="portfolioId")
	private List<PortfolioLinkMinistry> ministries;
	
	@OneToMany(mappedBy="portfolioId")
	private List<PortfolioLinkPerson> persons;
	
	@OneToMany(mappedBy="portfolioId")
	private List<PortfolioLinkAgency> agencies;
	
	@OneToMany(mappedBy="portfolioId")
	private List<PortfolioLinkPreceding> preceding;
	
	@OneToMany(mappedBy="portfolioId")
	private List<PortfolioLinkSucceeding> succeeding;
	
	public String getDateRange() {
		return DateHelper.dateRange(startDateQualifier, startDate, endDateQualifier, endDate);
	}

	public int getId(){
		return portfolioNumber;
	}
	
	public static List<Document> getIndexData(List<Portfolio> portfolios, CategoryDocumentBuilder builder){
  	List<Document> portfoliosIndex = new ArrayList<Document>();
  	for (Portfolio portfolio:portfolios){
  		Document doc = new Document();
  		Field f = new Field("title", portfolio.getTitle(), Field.Store.YES, Field.Index.ANALYZED);
  		f.setBoost(2.0f);
  		doc.add(f);

  		doc.add(new Field("type", "portfolios", Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("content", portfolio.getDescriptiveNote()==null?"":portfolio.getDescriptiveNote(), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("url", String.format("/portfolios/%d",portfolio.getPortfolioNumber()), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("startyear", DateHelper.getYearString(portfolio.getStartDate()), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("endyear",  DateHelper.getYearString(portfolio.getEndDate()), Field.Store.YES, Field.Index.ANALYZED));
  		portfoliosIndex.add(doc);
  	}
  	return portfoliosIndex;
	}
	
	 public String getJsonString(){
  	 return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(this); 
   }
}
