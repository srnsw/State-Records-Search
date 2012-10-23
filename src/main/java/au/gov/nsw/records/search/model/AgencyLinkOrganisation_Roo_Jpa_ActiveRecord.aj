// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.AgencyLinkOrganisation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect AgencyLinkOrganisation_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager AgencyLinkOrganisation.entityManager;
    
    public static final EntityManager AgencyLinkOrganisation.entityManager() {
        EntityManager em = new AgencyLinkOrganisation().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long AgencyLinkOrganisation.countAgencyLinkOrganisations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AgencyLinkOrganisation o", Long.class).getSingleResult();
    }
    
    public static List<AgencyLinkOrganisation> AgencyLinkOrganisation.findAllAgencyLinkOrganisations() {
        return entityManager().createQuery("SELECT o FROM AgencyLinkOrganisation o", AgencyLinkOrganisation.class).getResultList();
    }
    
    public static AgencyLinkOrganisation AgencyLinkOrganisation.findAgencyLinkOrganisation(int id) {
        return entityManager().find(AgencyLinkOrganisation.class, id);
    }
    
    public static List<AgencyLinkOrganisation> AgencyLinkOrganisation.findAgencyLinkOrganisationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AgencyLinkOrganisation o", AgencyLinkOrganisation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void AgencyLinkOrganisation.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void AgencyLinkOrganisation.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AgencyLinkOrganisation attached = AgencyLinkOrganisation.findAgencyLinkOrganisation(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void AgencyLinkOrganisation.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void AgencyLinkOrganisation.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public AgencyLinkOrganisation AgencyLinkOrganisation.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AgencyLinkOrganisation merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
