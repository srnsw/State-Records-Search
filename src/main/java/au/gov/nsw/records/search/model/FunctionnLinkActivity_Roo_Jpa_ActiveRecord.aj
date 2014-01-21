// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.FunctionnLinkActivity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect FunctionnLinkActivity_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager FunctionnLinkActivity.entityManager;
    
    public static final List<String> FunctionnLinkActivity.fieldNames4OrderClauseFilter = java.util.Arrays.asList("id", "functionId", "activityId", "startDate", "startDateQualifier", "endDate", "endDateQualifier");
    
    public static final EntityManager FunctionnLinkActivity.entityManager() {
        EntityManager em = new FunctionnLinkActivity().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long FunctionnLinkActivity.countFunctionnLinkActivitys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM FunctionnLinkActivity o", Long.class).getSingleResult();
    }
    
    public static List<FunctionnLinkActivity> FunctionnLinkActivity.findAllFunctionnLinkActivitys() {
        return entityManager().createQuery("SELECT o FROM FunctionnLinkActivity o", FunctionnLinkActivity.class).getResultList();
    }
    
    public static List<FunctionnLinkActivity> FunctionnLinkActivity.findAllFunctionnLinkActivitys(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM FunctionnLinkActivity o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, FunctionnLinkActivity.class).getResultList();
    }
    
    public static FunctionnLinkActivity FunctionnLinkActivity.findFunctionnLinkActivity(int id) {
        return entityManager().find(FunctionnLinkActivity.class, id);
    }
    
    public static List<FunctionnLinkActivity> FunctionnLinkActivity.findFunctionnLinkActivityEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM FunctionnLinkActivity o", FunctionnLinkActivity.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<FunctionnLinkActivity> FunctionnLinkActivity.findFunctionnLinkActivityEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM FunctionnLinkActivity o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, FunctionnLinkActivity.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void FunctionnLinkActivity.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void FunctionnLinkActivity.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            FunctionnLinkActivity attached = FunctionnLinkActivity.findFunctionnLinkActivity(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void FunctionnLinkActivity.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void FunctionnLinkActivity.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public FunctionnLinkActivity FunctionnLinkActivity.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        FunctionnLinkActivity merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
