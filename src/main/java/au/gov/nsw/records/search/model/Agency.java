package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.service.DateHelper;
import au.gov.nsw.records.search.service.QueryHelper;
import au.gov.nsw.records.search.service.StringService;

import java.util.*;
import java.util.regex.*;
import javax.persistence.*;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.facet.index.CategoryDocumentBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

@RooJavaBean
@RooToString
@Table(name = "agencies_view")
@RooJpaActiveRecord(versionField = "", finders = { "findAgencysByStartDateLessThan" })
public class Agency{
	  @Expose
    @Id
    @Column(name = "Agency_number")
    private int agencyNumber;

	  @Expose
    @Column(name = "Agency_title")
    private String title;

	  @Expose
    @Column(name = "Category")
    private String category;

	  @Expose
    @Column(name = "Creation")
    private String creation;

	  @Expose
    @Column(name = "Abolition")
    private String abolition;

	  @Expose
    @Column(name = "Administrative_history_note")
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
    @OneToMany(mappedBy = "agencyId")
    private List<AgencyLinkFunction> functions;

    @OneToMany(mappedBy = "agencyId")
    private List<AgencyLinkOrganisation> organisations;

    @OneToMany(mappedBy = "agencyId")
    private List<AgencyLinkPerson> persons;

    @OneToMany(mappedBy = "agencyId")
    private List<AgencyLinkSeriesControlled> seriesControlled;

    @OneToMany(mappedBy = "agencyId")
    private List<AgencyLinkSeriesCreated> seriesCreated;

    @OneToMany(mappedBy = "agencyId")
    private List<AgencyLinkPreceding> preceding;

    @OneToMany(mappedBy = "agencyId")
    private List<AgencyLinkRelated> related;

    @OneToMany(mappedBy = "agencyId")
    private List<AgencyLinkSubordinate> subordinates;

    @OneToMany(mappedBy = "agencyId")
    private List<AgencyLinkSucceeding> succeeding;

    @OneToMany(mappedBy = "agencyId")
    private List<AgencyLinkSuperior> superiors;

    public String getDateRange() {
        return DateHelper.dateRange(startDateQualifier, startDate, endDateQualifier, endDate);
    }

    public int getId() {
        return agencyNumber;
    }

    public String getHistoryEacFormat(){
    	return StringService.formatEacCpf(administrativeHistoryNote);
    }

    public class AgencyName{
        private String name;
        private Long startYear =null, endYear = null;
        public AgencyName(String name){
            this.name = StringUtils.strip(name);
        }

        public AgencyName(String name, Long startYear, Long endYear){
            this.name = StringUtils.strip(name);
            this.endYear = endYear;
            this.startYear = startYear;
        }

        public String getName() {
            return name;
        }

        public Long getStartYear() {
            return startYear;
        }

        public Long getEndYear() {
            return endYear;
        }

        public void setEndYear(Long endYear) {
            this.endYear = endYear;
        }

        public void setStartYear(Long startYear) {
            this.startYear = startYear;
        }

        @Override
        public String toString() {
            return String.format("%s (%d - %d)", name, startYear, endYear);
        }
    };

    public List<Object> getNames(){
        AgencyName name;
        List<Object> names = new ArrayList<Object>();
        Pattern p = Pattern.compile("((([\\w\\\\t \\.,'\"’‒–—―\\-;+]+)(\\([0-9]+\\))?(\\([^0-9]+\\))?(\\(No [\\d]+\\))?([\\w\\\\t \\.,'’‒–—―\\-;+]+)?(\\t?\\[[IVXMDC0-9]+\\])?([\\w\\\\t \\.,'\"’‒–—―\\-;+]+)?)((\\t? ?\\([/\\w\\t .,?‒–—―\\-;+]+\\)\\s?)+)?(-[ \\w]+)?(\\s)?(\\t? ?\\[[\\w\\\\t \\.,'’‒–—―\\-;+]+\\])?((\\t? ?\\([/\\w\\t .,?‒–—―\\-;+]+\\)\\s?)+)?,?)");
        Matcher matcher =p.matcher(title);
        while (matcher.find()){

            name= new AgencyName(matcher.group().replaceAll("\\([cby./?\\d ‒–—―\\-]+\\)", ""));


            Pattern datePattern = Pattern.compile("\\([cby./?\\d‒–—―\\-\\ ]+\\)");

            Matcher dateMatcher = datePattern.matcher(matcher.group());
            while (dateMatcher.find()){
                Matcher yearMatcher = Pattern.compile("\\d{4}").matcher(dateMatcher.group());
                List<String> years =new ArrayList<String>();
                while (yearMatcher.find()) years.add(yearMatcher.group());

                if(years.size()  == 2){
                    name.setStartYear(Long.parseLong(years.get(0)));
                    name.setEndYear(Long.parseLong(years.get(1)));
                    break;
                }
                else if(years.size()  == 1 && !dateMatcher.group().matches(".*[‒–—―\\-].*")){
                    name.setStartYear(Long.parseLong(years.get(0)));
                    name.setEndYear(Long.parseLong(years.get(0)));
                    break;
                }

                yearMatcher = Pattern.compile("\\d{4}(?=([ ]+)?-([ ]+)?)").matcher(dateMatcher.group());
                years =new ArrayList<String>();
                while (yearMatcher.find())years.add(yearMatcher.group());

                if(years.size()  == 1){
                    name.setStartYear(Long.parseLong(years.get(0)));
                    break;
                }

                yearMatcher = Pattern.compile("\\d{4}(?=(?:[ ?]+)?\\))").matcher(dateMatcher.group());
                years =new ArrayList<String>();
                while (yearMatcher.find())years.add(yearMatcher.group());

                if(years.size()  == 1){
                    name.setStartYear(Long.parseLong(years.get(0)));
                    break;
                }
            }


            names.add(name);
        }

        if(names.size()==1 && this.startDate !=null){
            name = (AgencyName)names.get(0);
            names.remove(name);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(this.startDate);
            name.setStartYear((long)calendar.get(Calendar.YEAR));
            names.add(name);
        }
        if(names.size()==1 && this.endDate !=null){
            name = (AgencyName)names.get(0);
            names.remove(name);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(this.endDate);
            name.setEndYear((long) calendar.get(Calendar.YEAR));
            names.add(name);
        }
        return names;
    }
    
    public static List<org.apache.lucene.document.Document> getIndexData(List<au.gov.nsw.records.search.model.Agency> agencies, CategoryDocumentBuilder builder) {
        List<Document> agenciesIndex = new ArrayList<Document>();
        for (Agency agency : agencies) {
            Document doc = new Document();
            Field f = new Field("title", agency.getTitle(), Field.Store.YES, Field.Index.ANALYZED);
            f.setBoost(2.0f);
            doc.add(f);
            doc.add(new Field("content", agency.getAdministrativeHistoryNote(), Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("type", "agencies", Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("url", String.format("/agencies/%d", agency.getAgencyNumber()), Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("startyear", DateHelper.getYearString(agency.getStartDate()), Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("endyear", DateHelper.getYearString(agency.getEndDate()), Field.Store.YES, Field.Index.ANALYZED));
            agenciesIndex.add(doc);
        }
        return agenciesIndex;
    }

    public static List<au.gov.nsw.records.search.model.Agency> findAgencyFromLastAmendmentDate(Date from, Date until, int page, int pageSize) {
        String additionalCondition = QueryHelper.buildAdditionalQuery(from, until);
        
        EntityManager em = Agency.entityManager();
        TypedQuery<Agency> q = em.createQuery("SELECT o FROM Agency AS o" + additionalCondition, Agency.class);
        if (from!=null){
        	q.setParameter("from", from);	
        }
        if (until!=null){
        	q.setParameter("until", until);
        }
        return q.setFirstResult((page-1)*pageSize).setMaxResults(pageSize).getResultList();
    }

    public static long countAgencyFromLastAmendmentDate(Date from, Date until) {
    	  String additionalCondition = QueryHelper.buildAdditionalQuery(from, until);
    	  EntityManager em = Agency.entityManager();
    	  TypedQuery<Long> q = em.createQuery("SELECT count(o) FROM Agency o " + additionalCondition, Long.class);
        if (from!=null){
        	q.setParameter("from", from);	
        }
        if (until!=null){
        	q.setParameter("until", until);
        }
        return q.getSingleResult();
    }

    public static List<Agency> findOpenAgencies(int firstResult, int sizeNo) {
        EntityManager em = Agency.entityManager();
        TypedQuery<Agency> q = em.createQuery("SELECT o FROM Agency as o where o.endDate = null", Agency.class);
        return q.setFirstResult(firstResult).setMaxResults(sizeNo).getResultList();
    }
    
    public String getJsonString(){
        JsonElement jsonElement = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJsonTree(this);
        JsonElement jsonElementList = new GsonBuilder().create().toJsonTree(this.getNames());
        jsonElement.getAsJsonObject().add("names", jsonElementList);
        return jsonElement.toString();
    }
}
