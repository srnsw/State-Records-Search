// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.Portfolio;
import au.gov.nsw.records.search.model.PortfolioLinkAgency;
import au.gov.nsw.records.search.model.PortfolioLinkMinistry;
import au.gov.nsw.records.search.model.PortfolioLinkPerson;
import au.gov.nsw.records.search.model.PortfolioLinkPreceding;
import au.gov.nsw.records.search.model.PortfolioLinkSucceeding;
import java.util.Date;
import java.util.List;

privileged aspect Portfolio_Roo_JavaBean {
    
    public int Portfolio.getPortfolioNumber() {
        return this.portfolioNumber;
    }
    
    public void Portfolio.setPortfolioNumber(int portfolioNumber) {
        this.portfolioNumber = portfolioNumber;
    }
    
    public String Portfolio.getTitle() {
        return this.title;
    }
    
    public void Portfolio.setTitle(String title) {
        this.title = title;
    }
    
    public String Portfolio.getDescriptiveNote() {
        return this.descriptiveNote;
    }
    
    public void Portfolio.setDescriptiveNote(String descriptiveNote) {
        this.descriptiveNote = descriptiveNote;
    }
    
    public Date Portfolio.getRegisteredDate() {
        return this.registeredDate;
    }
    
    public void Portfolio.setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }
    
    public Date Portfolio.getStartDate() {
        return this.startDate;
    }
    
    public void Portfolio.setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public String Portfolio.getStartDateQualifier() {
        return this.startDateQualifier;
    }
    
    public void Portfolio.setStartDateQualifier(String startDateQualifier) {
        this.startDateQualifier = startDateQualifier;
    }
    
    public Date Portfolio.getEndDate() {
        return this.endDate;
    }
    
    public void Portfolio.setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String Portfolio.getEndDateQualifier() {
        return this.endDateQualifier;
    }
    
    public void Portfolio.setEndDateQualifier(String endDateQualifier) {
        this.endDateQualifier = endDateQualifier;
    }
    
    public List<PortfolioLinkMinistry> Portfolio.getMinistries() {
        return this.ministries;
    }
    
    public void Portfolio.setMinistries(List<PortfolioLinkMinistry> ministries) {
        this.ministries = ministries;
    }
    
    public List<PortfolioLinkPerson> Portfolio.getPersons() {
        return this.persons;
    }
    
    public void Portfolio.setPersons(List<PortfolioLinkPerson> persons) {
        this.persons = persons;
    }
    
    public List<PortfolioLinkAgency> Portfolio.getAgencies() {
        return this.agencies;
    }
    
    public void Portfolio.setAgencies(List<PortfolioLinkAgency> agencies) {
        this.agencies = agencies;
    }
    
    public List<PortfolioLinkPreceding> Portfolio.getPreceding() {
        return this.preceding;
    }
    
    public void Portfolio.setPreceding(List<PortfolioLinkPreceding> preceding) {
        this.preceding = preceding;
    }
    
    public List<PortfolioLinkSucceeding> Portfolio.getSucceeding() {
        return this.succeeding;
    }
    
    public void Portfolio.setSucceeding(List<PortfolioLinkSucceeding> succeeding) {
        this.succeeding = succeeding;
    }
    
}
