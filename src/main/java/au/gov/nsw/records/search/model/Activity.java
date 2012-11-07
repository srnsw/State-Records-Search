package au.gov.nsw.records.search.model;

import java.io.IOException;
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
@Table(name = "activities_view")
public class Activity {

	@Id
	@Column(name = "Activity_number")
	private int activityNumber;

	@Column(name = "Activity_title")
	private String title;

	@Column(name = "Descriptive_note")
	private String descriptiveNote;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "Last_amendment_date")
	private Date lastAmendmentDate;
	
	
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

	@OneToMany(mappedBy="activitiyId")
	private List<ActivityLinkSerie> series;
	
	@OneToMany(mappedBy="activitiyId")
	private List<ActivityLinkFunction> functions;
	
	public String getDateRange() {
		return DateHelper.dateRange(startDateQualifier, startDate, endDateQualifier, endDate);
	}

	public int getId(){
		return activityNumber;
	}
	
	public static List<Document> getIndexData(List<Activity> activities, CategoryDocumentBuilder builder) throws IOException{
		List<Document> activitiesIndex = new ArrayList<Document>();
		for (Activity activity: activities){
			Document doc = new Document();
			Field f = new Field("title", activity.getTitle(), Field.Store.YES, Field.Index.ANALYZED);
			f.setBoost(2.0f);
			doc.add(f);

			doc.add(new Field("content", activity.getDescriptiveNote(), Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("type", "activities", Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("url", String.format("/activities/%d",activity.getActivityNumber()), Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("startyear", DateHelper.getYearString(activity.getStartDate()), Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("endyear",  DateHelper.getYearString(activity.getEndDate()), Field.Store.YES, Field.Index.ANALYZED));

//			List<CategoryPath> categories = new ArrayList<CategoryPath>();
//			categories.add(new CategoryPath("startyear", DateHelper.getYearString(activity.getStartDate())));
//			categories.add(new CategoryPath("endyear", DateHelper.getYearString(activity.getEndDate())));
//
//			builder.setCategoryPaths(categories);
//			builder.build(doc);

			activitiesIndex.add(doc);
		}
		return activitiesIndex;
	}

}
