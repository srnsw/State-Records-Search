// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.OrganisationLinkPreceding;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect OrganisationLinkPreceding_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager OrganisationLinkPreceding.entityManager;
    
    public static final EntityManager OrganisationLinkPreceding.entityManager() {
        EntityManager em = new OrganisationLinkPreceding().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long OrganisationLinkPreceding.countOrganisationLinkPrecedings() {
        return entityManager().createQuery("SELECT COUNT(o) FROM OrganisationLinkPreceding o", Long.class).getSingleResult();
    }
    
    public static List<OrganisationLinkPreceding> OrganisationLinkPreceding.findAllOrganisationLinkPrecedings() {
        return entityManager().createQuery("SELECT o FROM OrganisationLinkPreceding o", OrganisationLinkPreceding.class).getResultList();
    }
    
    public static OrganisationLinkPreceding OrganisationLinkPreceding.findOrganisationLinkPreceding(int id) {
        return entityManager().find(OrganisationLinkPreceding.class, id);
    }
    
    public static List<OrganisationLinkPreceding> OrganisationLinkPreceding.findOrganisationLinkPrecedingEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM OrganisationLinkPreceding o", OrganisationLinkPreceding.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void OrganisationLinkPreceding.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void OrganisationLinkPreceding.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            OrganisationLinkPreceding attached = OrganisationLinkPreceding.findOrganisationLinkPreceding(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void OrganisationLinkPreceding.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void OrganisationLinkPreceding.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public OrganisationLinkPreceding OrganisationLinkPreceding.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        OrganisationLinkPreceding merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
