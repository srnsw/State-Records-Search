package au.gov.nsw.records.search.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.facet.index.CategoryDocumentBuilder;
import org.apache.lucene.facet.taxonomy.CategoryPath;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import au.gov.nsw.records.search.service.DateHelper;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
@Table(name = "functions_view")
public class Functionn {
	//Use Functionn to avoid the reserved word "Function"
	@Id
	@Column(name = "Function_number")
  private int functionNumber;
  
  @Column(name = "Function_title")
  private String title;
  
  @Column(name = "Abolition")
  private String abolition;
  
  @Column(name = "Descriptive_note")
  private String descriptiveNote;
  
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "Registered_date")
	private Date registeredDate;
	
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
	
	public String getDateRange() {
		return DateHelper.dateRange(startDateQualifier, startDate, endDateQualifier, endDate);
	}
	
	public int getId(){
		return functionNumber;
	}
	
	public static List<Document> getIndexData(List<Functionn> functions, CategoryDocumentBuilder builder) throws IOException{
  	List<Document> functionsIndex = new ArrayList<Document>();
  	for (Functionn func: functions){
  		Document doc = new Document();
  		Field f = new Field("title", func.getTitle(), Field.Store.YES, Field.Index.ANALYZED);
  		f.setBoost(2.0f);
  		doc.add(f);
  		
  		doc.add(new Field("type", "functions", Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("content", func.getDescriptiveNote(), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("url", String.format("/functions/%d",func.getFunctionNumber()), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("startyear", DateHelper.getYearString(func.getStartDate()), Field.Store.YES, Field.Index.ANALYZED));
  		doc.add(new Field("endyear",  DateHelper.getYearString(func.getEndDate()), Field.Store.YES, Field.Index.ANALYZED));
  		
  		List<CategoryPath> categories = new ArrayList<CategoryPath>();
			categories.add(new CategoryPath("startyear", DateHelper.getYearString(func.getStartDate())));
			categories.add(new CategoryPath("endyear", DateHelper.getYearString(func.getEndDate())));
			
			builder.setCategoryPaths(categories);
			builder.build(doc);
			
			functionsIndex.add(doc);
  	}
  	return functionsIndex;
  }
}
