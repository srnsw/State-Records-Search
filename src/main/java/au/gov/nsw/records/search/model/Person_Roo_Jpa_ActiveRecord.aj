// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Person_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Person.entityManager;
    
    public static final EntityManager Person.entityManager() {
        EntityManager em = new Person().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Person.countPeople() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Person o", Long.class).getSingleResult();
    }
    
    public static List<Person> Person.findAllPeople() {
        return entityManager().createQuery("SELECT o FROM Person o", Person.class).getResultList();
    }
    
    public static Person Person.findPerson(int personNumber) {
        return entityManager().find(Person.class, personNumber);
    }
    
    public static List<Person> Person.findPersonEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Person o", Person.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Person.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Person.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Person attached = Person.findPerson(this.personNumber);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Person.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Person.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Person Person.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Person merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
