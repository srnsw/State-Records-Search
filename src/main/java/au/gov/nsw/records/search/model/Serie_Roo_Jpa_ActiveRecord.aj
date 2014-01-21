// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.Serie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Serie_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Serie.entityManager;
    
    public static final List<String> Serie.fieldNames4OrderClauseFilter = java.util.Arrays.asList("seriesNumber", "title", "descriptiveNote", "format", "arrangement", "copies", "seriesControlStatus", "accessNote", "repository", "bridgingAids", "lastAmendmentDate", "startDate", "startDateQualifier", "endDate", "endDateQualifier", "contentStartDate", "contentStartDateQualifier", "contentEndDate", "contentEndDateQualifier", "items", "activities", "controllingAgencies", "creatingAgencies", "persons", "precedingSeries", "succeedingSeries", "relatedSeries", "controllingSeries", "controlledSeries");
    
    public static final EntityManager Serie.entityManager() {
        EntityManager em = new Serie().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Serie.countSeries() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Serie o", Long.class).getSingleResult();
    }
    
    public static List<Serie> Serie.findAllSeries() {
        return entityManager().createQuery("SELECT o FROM Serie o", Serie.class).getResultList();
    }
    
    public static List<Serie> Serie.findAllSeries(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Serie o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Serie.class).getResultList();
    }
    
    public static Serie Serie.findSerie(int seriesNumber) {
        return entityManager().find(Serie.class, seriesNumber);
    }
    
    public static List<Serie> Serie.findSerieEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Serie o", Serie.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Serie> Serie.findSerieEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Serie o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Serie.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Serie.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Serie.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Serie attached = Serie.findSerie(this.seriesNumber);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Serie.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Serie.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Serie Serie.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Serie merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
