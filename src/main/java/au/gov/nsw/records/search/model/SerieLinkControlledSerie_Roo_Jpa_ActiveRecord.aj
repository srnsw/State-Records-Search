// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.SerieLinkControlledSerie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SerieLinkControlledSerie_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager SerieLinkControlledSerie.entityManager;
    
    public static final List<String> SerieLinkControlledSerie.fieldNames4OrderClauseFilter = java.util.Arrays.asList("id", "seriesId", "serieId", "startDate", "startDateQualifier", "endDate", "endDateQualifier");
    
    public static final EntityManager SerieLinkControlledSerie.entityManager() {
        EntityManager em = new SerieLinkControlledSerie().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long SerieLinkControlledSerie.countSerieLinkControlledSeries() {
        return entityManager().createQuery("SELECT COUNT(o) FROM SerieLinkControlledSerie o", Long.class).getSingleResult();
    }
    
    public static List<SerieLinkControlledSerie> SerieLinkControlledSerie.findAllSerieLinkControlledSeries() {
        return entityManager().createQuery("SELECT o FROM SerieLinkControlledSerie o", SerieLinkControlledSerie.class).getResultList();
    }
    
    public static List<SerieLinkControlledSerie> SerieLinkControlledSerie.findAllSerieLinkControlledSeries(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM SerieLinkControlledSerie o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, SerieLinkControlledSerie.class).getResultList();
    }
    
    public static SerieLinkControlledSerie SerieLinkControlledSerie.findSerieLinkControlledSerie(int id) {
        return entityManager().find(SerieLinkControlledSerie.class, id);
    }
    
    public static List<SerieLinkControlledSerie> SerieLinkControlledSerie.findSerieLinkControlledSerieEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM SerieLinkControlledSerie o", SerieLinkControlledSerie.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<SerieLinkControlledSerie> SerieLinkControlledSerie.findSerieLinkControlledSerieEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM SerieLinkControlledSerie o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, SerieLinkControlledSerie.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void SerieLinkControlledSerie.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void SerieLinkControlledSerie.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            SerieLinkControlledSerie attached = SerieLinkControlledSerie.findSerieLinkControlledSerie(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void SerieLinkControlledSerie.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void SerieLinkControlledSerie.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public SerieLinkControlledSerie SerieLinkControlledSerie.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        SerieLinkControlledSerie merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
