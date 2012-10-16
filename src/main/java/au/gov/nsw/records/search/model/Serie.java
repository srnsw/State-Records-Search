package au.gov.nsw.records.search.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.facet.index.CategoryDocumentBuilder;
import org.apache.lucene.facet.taxonomy.CategoryPath;
import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import au.gov.nsw.records.search.service.DateHelper;
import au.gov.nsw.records.search.service.LocationHelper;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
@Table(name = "series_view")
public class Serie {

	@Id
	@Column(name = "Series_number")
	private int seriesNumber;

	@Column(name = "Series_title")
	private String title;

	@Column(name="Descriptive_note")
	private String descriptiveNote;

	@Column(name="Format")
	private String format;

	@Column(name="Arrangement")
	private String arrangement;

	@Column(name="Copies")
	private String copies;

	@Column(name="Series_control_status")
	private String seriesControlStatus;

	@Column(name="Repository")
	private String repository;

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

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "Contents_start_date")
	private Date contentStartDate;

	@Column(name = "Contents_start_date_qualifier")
	private String contentStartDateQualifier;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "Contents_End_date")
	private Date contentEndDate;

	@Column(name = "Contents_End_date_qualifier")
	private String contentEndDateQualifier;

	@ManyToMany
	@JoinTable(name="series_link_preceding_series", joinColumns = {@JoinColumn(name="series_id")}, inverseJoinColumns = {@JoinColumn(name="preceding_id")})
	private List<Serie> preceedingSeries; 
	
	@ManyToMany
	@JoinTable(name="series_link_related_series", joinColumns = {@JoinColumn(name="series_id")}, inverseJoinColumns = {@JoinColumn(name="related_id")})
	private List<Serie> relatedSeries; 

//	@OneToMany(mappedBy="serie")
//	private List<SeriePrecedingSerie> precedingSeries;
//	
//	//@OneToMany(mappedBy="serie")
//	//private List<SerieRelatedSerie> relatedSeries;
//	
//	@OneToMany(mappedBy="serie")
//	private List<SerieControllingSerie> controllingSeries;
//	
//	@OneToMany(mappedBy="serie")
//	private List<SerieSucceedingAgency> succeedingAgencies;
//	
//	@OneToMany(mappedBy="serie")
//	private List<SerieCreatingAgency> creatingAgencies;
	
	public String getDateRange() {
		return DateHelper.dateRange(startDateQualifier, startDate, endDateQualifier, endDate);
	}

	public String getContentDateRange(){
		return DateHelper.dateRange(contentEndDateQualifier, contentStartDate, contentEndDateQualifier, contentEndDate);
	}
	
	public String getLocation(){
  	return LocationHelper.getSimpleLocation(repository);
  }
	
	public static List<Document> getIndexData(List<Serie> series, CategoryDocumentBuilder builder) throws IOException{
  	List<Document> seriesIndex = new ArrayList<Document>();
  	for (Serie serie:series){
  		Document doc = new Document();
  		Field f = new Field("title", serie.getTitle(), Field.Store.YES, Field.Index.ANALYZED);
  		f.setBoost(2.0f);
  		doc.add(f);

  		doc.add(new Field("type", "series", Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("location", serie.getLocation(), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("content", serie.getDescriptiveNote()==null? "" : serie.getDescriptiveNote(), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("url", String.format("/series/%d", serie.getSeriesNumber()), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("startyear", DateHelper.getYearString(serie.getStartDate()), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("endyear",  DateHelper.getYearString(serie.getEndDate()), Field.Store.YES, Field.Index.ANALYZED));
  		
  		List<CategoryPath> categories = new ArrayList<CategoryPath>();
			categories.add(new CategoryPath("startyear", DateHelper.getYearString(serie.getStartDate())));
			categories.add(new CategoryPath("endyear", DateHelper.getYearString(serie.getEndDate())));
			categories.add(new CategoryPath("location", serie.getLocation()));
			categories.add(new CategoryPath("series", serie.getTitle()));
			
			builder.setCategoryPaths(categories);
			
  		seriesIndex.add(doc);
  	}
  	return seriesIndex;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this.getTitle(), ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
