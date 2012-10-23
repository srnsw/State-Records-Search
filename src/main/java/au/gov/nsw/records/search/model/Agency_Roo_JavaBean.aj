// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.Agency;
import au.gov.nsw.records.search.model.AgencyLinkFunction;
import au.gov.nsw.records.search.model.AgencyLinkOrganisation;
import au.gov.nsw.records.search.model.AgencyLinkPerson;
import au.gov.nsw.records.search.model.AgencyLinkPreceding;
import au.gov.nsw.records.search.model.AgencyLinkRelated;
import au.gov.nsw.records.search.model.AgencyLinkSeriesControlled;
import au.gov.nsw.records.search.model.AgencyLinkSeriesCreated;
import au.gov.nsw.records.search.model.AgencyLinkSubordinate;
import au.gov.nsw.records.search.model.AgencyLinkSucceeding;
import au.gov.nsw.records.search.model.AgencyLinkSuperior;
import java.util.Date;
import java.util.List;

privileged aspect Agency_Roo_JavaBean {
    
    public int Agency.getAgencyNumber() {
        return this.agencyNumber;
    }
    
    public void Agency.setAgencyNumber(int agencyNumber) {
        this.agencyNumber = agencyNumber;
    }
    
    public String Agency.getTitle() {
        return this.title;
    }
    
    public void Agency.setTitle(String title) {
        this.title = title;
    }
    
    public String Agency.getCategory() {
        return this.category;
    }
    
    public void Agency.setCategory(String category) {
        this.category = category;
    }
    
    public String Agency.getCreation() {
        return this.creation;
    }
    
    public void Agency.setCreation(String creation) {
        this.creation = creation;
    }
    
    public String Agency.getAbolition() {
        return this.abolition;
    }
    
    public void Agency.setAbolition(String abolition) {
        this.abolition = abolition;
    }
    
    public String Agency.getAdministrativeHistoryNote() {
        return this.administrativeHistoryNote;
    }
    
    public void Agency.setAdministrativeHistoryNote(String administrativeHistoryNote) {
        this.administrativeHistoryNote = administrativeHistoryNote;
    }
    
    public Date Agency.getStartDate() {
        return this.startDate;
    }
    
    public void Agency.setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public String Agency.getStartDateQualifier() {
        return this.startDateQualifier;
    }
    
    public void Agency.setStartDateQualifier(String startDateQualifier) {
        this.startDateQualifier = startDateQualifier;
    }
    
    public Date Agency.getEndDate() {
        return this.endDate;
    }
    
    public void Agency.setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String Agency.getEndDateQualifier() {
        return this.endDateQualifier;
    }
    
    public void Agency.setEndDateQualifier(String endDateQualifier) {
        this.endDateQualifier = endDateQualifier;
    }
    
    public List<AgencyLinkFunction> Agency.getFunctions() {
        return this.functions;
    }
    
    public void Agency.setFunctions(List<AgencyLinkFunction> functions) {
        this.functions = functions;
    }
    
    public List<AgencyLinkOrganisation> Agency.getOrganisations() {
        return this.organisations;
    }
    
    public void Agency.setOrganisations(List<AgencyLinkOrganisation> organisations) {
        this.organisations = organisations;
    }
    
    public List<AgencyLinkPerson> Agency.getPersons() {
        return this.persons;
    }
    
    public void Agency.setPersons(List<AgencyLinkPerson> persons) {
        this.persons = persons;
    }
    
    public List<AgencyLinkSeriesControlled> Agency.getSeriesControlled() {
        return this.seriesControlled;
    }
    
    public void Agency.setSeriesControlled(List<AgencyLinkSeriesControlled> seriesControlled) {
        this.seriesControlled = seriesControlled;
    }
    
    public List<AgencyLinkSeriesCreated> Agency.getSeriesCreated() {
        return this.seriesCreated;
    }
    
    public void Agency.setSeriesCreated(List<AgencyLinkSeriesCreated> seriesCreated) {
        this.seriesCreated = seriesCreated;
    }
    
    public List<AgencyLinkPreceding> Agency.getPreceding() {
        return this.preceding;
    }
    
    public void Agency.setPreceding(List<AgencyLinkPreceding> preceding) {
        this.preceding = preceding;
    }
    
    public List<AgencyLinkRelated> Agency.getRelated() {
        return this.related;
    }
    
    public void Agency.setRelated(List<AgencyLinkRelated> related) {
        this.related = related;
    }
    
    public List<AgencyLinkSubordinate> Agency.getSubordinates() {
        return this.subordinates;
    }
    
    public void Agency.setSubordinates(List<AgencyLinkSubordinate> subordinates) {
        this.subordinates = subordinates;
    }
    
    public List<AgencyLinkSucceeding> Agency.getSucceeding() {
        return this.succeeding;
    }
    
    public void Agency.setSucceeding(List<AgencyLinkSucceeding> succeeding) {
        this.succeeding = succeeding;
    }
    
    public List<AgencyLinkSuperior> Agency.getSuperiors() {
        return this.superiors;
    }
    
    public void Agency.setSuperiors(List<AgencyLinkSuperior> superiors) {
        this.superiors = superiors;
    }
    
}
