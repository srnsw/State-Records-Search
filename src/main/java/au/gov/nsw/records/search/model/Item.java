package au.gov.nsw.records.search.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
import au.gov.nsw.records.search.service.LocationHelper;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
@Table(name = "items_view")
public class Item {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "Seriestype")
    private String seriesType;

    @ManyToOne
    private Serie seriesNumber;

    @Column(name = "Item_number_or_control_symbol")
    private String itemNumberOrControlSymbol;

    @Column(name = "Item_title")
    private String title;

    @Column(name = "Descriptive_Note")
    private String descriptiveNote;

    @Column(name = "Accessdirectionno")
    private String accessDirectionNumber;

    @Column(name = "Availability")
    private String availability;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    @Column(name = "Start_date")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    @Column(name = "End_date")
    private Date endDate;

    public String getLocation(){
    	return LocationHelper.getSimpleLocation(seriesNumber.getRepository());
    }
    
    public static List<Document> getIndexData(List<Item> items, CategoryDocumentBuilder builder) throws IOException{
    	List<Document> itemsIndex = new ArrayList<Document>();
    	for (Item item: items){
    		Document doc = new Document();
    		Field f = new Field("title", item.getTitle(), Field.Store.YES, Field.Index.ANALYZED);
    		f.setBoost(2.0f);
    		doc.add(f);
    		   		
    		doc.add(new Field("location", item.getLocation(), Field.Store.YES, Field.Index.ANALYZED));
    		doc.add(new Field("series", item.getSeriesNumber().getTitle(), Field.Store.YES, Field.Index.ANALYZED));
    		doc.add(new Field("type", "items", Field.Store.YES, Field.Index.ANALYZED));
    		doc.add(new Field("content", item.getDescriptiveNote().replace("<i>", "").replace("</i>", ""), Field.Store.YES, Field.Index.ANALYZED));
    		doc.add(new Field("url", String.format("/items/%d",item.getId()), Field.Store.YES, Field.Index.ANALYZED));
    		doc.add(new Field("startyear", DateHelper.getYearString(item.getStartDate()), Field.Store.YES, Field.Index.ANALYZED));
    		doc.add(new Field("endyear",  DateHelper.getYearString(item.getEndDate()), Field.Store.YES, Field.Index.ANALYZED));
    		
    		List<CategoryPath> categories = new ArrayList<CategoryPath>();
  			categories.add(new CategoryPath("startyear", DateHelper.getYearString(item.getStartDate())));
  			categories.add(new CategoryPath("endyear", DateHelper.getYearString(item.getEndDate())));
  			categories.add(new CategoryPath("location", item.getLocation()));
  			categories.add(new CategoryPath("series", item.getSeriesNumber().getTitle()));
  			
  			builder.setCategoryPaths(categories);
  			builder.build(doc);
  			
    		itemsIndex.add(doc);
    	}
    	return itemsIndex;
    }
}
