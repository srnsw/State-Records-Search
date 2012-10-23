// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.PortfolioLinkPreceding;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect PortfolioLinkPreceding_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager PortfolioLinkPreceding.entityManager;
    
    public static final EntityManager PortfolioLinkPreceding.entityManager() {
        EntityManager em = new PortfolioLinkPreceding().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long PortfolioLinkPreceding.countPortfolioLinkPrecedings() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PortfolioLinkPreceding o", Long.class).getSingleResult();
    }
    
    public static List<PortfolioLinkPreceding> PortfolioLinkPreceding.findAllPortfolioLinkPrecedings() {
        return entityManager().createQuery("SELECT o FROM PortfolioLinkPreceding o", PortfolioLinkPreceding.class).getResultList();
    }
    
    public static PortfolioLinkPreceding PortfolioLinkPreceding.findPortfolioLinkPreceding(int id) {
        return entityManager().find(PortfolioLinkPreceding.class, id);
    }
    
    public static List<PortfolioLinkPreceding> PortfolioLinkPreceding.findPortfolioLinkPrecedingEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PortfolioLinkPreceding o", PortfolioLinkPreceding.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void PortfolioLinkPreceding.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void PortfolioLinkPreceding.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            PortfolioLinkPreceding attached = PortfolioLinkPreceding.findPortfolioLinkPreceding(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void PortfolioLinkPreceding.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void PortfolioLinkPreceding.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public PortfolioLinkPreceding PortfolioLinkPreceding.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PortfolioLinkPreceding merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
