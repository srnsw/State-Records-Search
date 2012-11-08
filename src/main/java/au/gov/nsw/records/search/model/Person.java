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

import com.google.gson.annotations.Expose;

import au.gov.nsw.records.search.service.DateHelper;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
@Table(name = "persons_view")
public class Person extends JsonModel{
	  @Expose
    @Id
    @Column(name = "Person_number")
    private int personNumber;

	  @Expose
    @Column(name = "Surname")
    private String surname;

	  @Expose
    @Column(name = "Given_names")
    private String givenName;

	  @Expose
    @Column(name = "Prenomial_honorifics")
    private String prenomialHonorifics;

	  @Expose
    @Column(name = "Postnomial_honorifics")
    private String postnomialHonorifics;

	  @Expose
    @Column(name = "Offices_held")
    private String officeHeld;

	  @Expose
    @Column(name = "Biographical_note")
    private String biographicalNote;

	  @Expose
    @Column(name = "Minister")
    private boolean minister;

	  @Expose
    @Temporal(TemporalType.TIMESTAMP)
  	@DateTimeFormat(style = "M-")
  	@Column(name = "Last_amendment_date")
  	private Date lastAmendmentDate;
    
	  @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    @Column(name = "Birth_date")
    private Date birthDate;

	  @Expose
    @Column(name = "Birth_date_qualifier")
    private String birthDateQualifier;

	  @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    @Column(name = "Death_date")
    private Date deathDate;

	  @Expose
    @Column(name = "Death_date_qualifier")
    private String deathDateQualifier;

  	@OneToMany(mappedBy="personId")
  	private List<PersonLinkFunction> functions;
  	
  	@OneToMany(mappedBy="personId")
  	private List<PersonLinkAgency> agencies;
  	
  	@OneToMany(mappedBy="personId")
  	private List<PersonLinkSeries> series;
  	
  	@OneToMany(mappedBy="personId")
  	private List<PersonLinkPortfolio> portfolios;
  	
  	@OneToMany(mappedBy="personId")
  	private List<PersonLinkMinistry> ministries;
  	
    public String getDateRange() {
        return DateHelper.dateRange(birthDateQualifier, birthDate, deathDateQualifier, deathDate);
    }

    public int getId(){
    	return personNumber;
    }
    
    public String getTitle(){
    	return getFullName();
    }
    
    public String getFullName() {
        String s = (prenomialHonorifics != null ? prenomialHonorifics + " " : "");
        s = (givenName != null ? s + givenName + " " : s);
        s = (surname != null ? s + surname + " " : s);
        s = (postnomialHonorifics != null ? s + "(" + postnomialHonorifics + ")" : s);
        return s;
    }
    
    public static List<Document> getIndexData(List<Person> persons, CategoryDocumentBuilder builder){
    	List<Document> personsIndex = new ArrayList<Document>();
    	for (Person person: persons){
    		Document doc = new Document();
    		Field f = new Field("title", person.getFullName(), Field.Store.YES, Field.Index.ANALYZED);
    		f.setBoost(2.0f);
    		doc.add(f);

    		doc.add(new Field("type", "persons", Field.Store.YES, Field.Index.ANALYZED));
    		doc.add(new Field("content", person.getBiographicalNote(), Field.Store.YES, Field.Index.ANALYZED));
    		doc.add(new Field("url", String.format("/persons/%d", person.getPersonNumber()), Field.Store.YES, Field.Index.ANALYZED));
    		doc.add(new Field("startyear", DateHelper.getYearString(person.getBirthDate()), Field.Store.YES, Field.Index.ANALYZED));
    		doc.add(new Field("endyear",  DateHelper.getYearString(person.getDeathDate()), Field.Store.YES, Field.Index.ANALYZED));
    		personsIndex.add(doc);
    	}
    	return personsIndex;
    }
}
