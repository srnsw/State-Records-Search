// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.Agency;
import au.gov.nsw.records.search.model.Person;
import au.gov.nsw.records.search.model.PersonLinkAgency;

privileged aspect PersonLinkAgency_Roo_JavaBean {
    
    public int PersonLinkAgency.getId() {
        return this.id;
    }
    
    public void PersonLinkAgency.setId(int id) {
        this.id = id;
    }
    
    public Person PersonLinkAgency.getPersonId() {
        return this.personId;
    }
    
    public void PersonLinkAgency.setPersonId(Person personId) {
        this.personId = personId;
    }
    
    public Agency PersonLinkAgency.getAgencyId() {
        return this.agencyId;
    }
    
    public void PersonLinkAgency.setAgencyId(Agency agencyId) {
        this.agencyId = agencyId;
    }
    
    public String PersonLinkAgency.getStartDate() {
        return this.startDate;
    }
    
    public void PersonLinkAgency.setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String PersonLinkAgency.getStartDateQualifier() {
        return this.startDateQualifier;
    }
    
    public void PersonLinkAgency.setStartDateQualifier(String startDateQualifier) {
        this.startDateQualifier = startDateQualifier;
    }
    
    public String PersonLinkAgency.getEndDate() {
        return this.endDate;
    }
    
    public void PersonLinkAgency.setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public String PersonLinkAgency.getEndDateQualifier() {
        return this.endDateQualifier;
    }
    
    public void PersonLinkAgency.setEndDateQualifier(String endDateQualifier) {
        this.endDateQualifier = endDateQualifier;
    }
    
}
