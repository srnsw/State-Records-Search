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
@Table(name="ministries_view")
public class Ministry {
	
	@Id
	@Column(name="Ministry_Number")
	private int ministryNumber;
	
	@Column(name="Ministry_title")
	private String title;

  @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "Last_amendment_date")
	private Date lastAmendmentDate;
  
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "Registered_Date")
	private Date registeredDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "Start_date")
	private Date startDate;


	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "End_date")
	private Date endDate;
	
	public String getDateRange() {
		return DateHelper.dateRange(null, startDate, null, endDate);
	}
	
	public int getId(){
		return ministryNumber;
	}
	
	@OneToMany(mappedBy="ministryId")
	private List<MinistryLinkPortfolio> portfolios;
	
	public static List<Document> getIndexData(List<Ministry> ministries, CategoryDocumentBuilder builder){
		List<Document> ministriesIndex = new ArrayList<Document>();
  	for (Ministry ministry:ministries){
  		Document doc = new Document();
  		Field f = new Field("title", ministry.getTitle(), Field.Store.YES, Field.Index.ANALYZED);
  		f.setBoost(2.0f);
  		doc.add(f);

  		doc.add(new Field("type", "ministries", Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("url", String.format("/ministries/%d",ministry.getMinistryNumber()), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("startyear", DateHelper.getYearString(ministry.getStartDate()), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("endyear",  DateHelper.getYearString(ministry.getEndDate()), Field.Store.YES, Field.Index.ANALYZED));
  		ministriesIndex.add(doc);
  	}
  	return ministriesIndex;
	}
}
