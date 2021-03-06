// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.Agency;
import au.gov.nsw.records.search.model.AgencyLinkSeriesCreated;
import au.gov.nsw.records.search.model.Serie;

privileged aspect AgencyLinkSeriesCreated_Roo_JavaBean {
    
    public int AgencyLinkSeriesCreated.getId() {
        return this.id;
    }
    
    public void AgencyLinkSeriesCreated.setId(int id) {
        this.id = id;
    }
    
    public Agency AgencyLinkSeriesCreated.getAgencyId() {
        return this.agencyId;
    }
    
    public void AgencyLinkSeriesCreated.setAgencyId(Agency agencyId) {
        this.agencyId = agencyId;
    }
    
    public Serie AgencyLinkSeriesCreated.getSeriesId() {
        return this.seriesId;
    }
    
    public void AgencyLinkSeriesCreated.setSeriesId(Serie seriesId) {
        this.seriesId = seriesId;
    }
    
    public String AgencyLinkSeriesCreated.getStartDate() {
        return this.startDate;
    }
    
    public void AgencyLinkSeriesCreated.setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String AgencyLinkSeriesCreated.getStartDateQualifier() {
        return this.startDateQualifier;
    }
    
    public void AgencyLinkSeriesCreated.setStartDateQualifier(String startDateQualifier) {
        this.startDateQualifier = startDateQualifier;
    }
    
    public String AgencyLinkSeriesCreated.getEndDate() {
        return this.endDate;
    }
    
    public void AgencyLinkSeriesCreated.setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public String AgencyLinkSeriesCreated.getEndDateQualifier() {
        return this.endDateQualifier;
    }
    
    public void AgencyLinkSeriesCreated.setEndDateQualifier(String endDateQualifier) {
        this.endDateQualifier = endDateQualifier;
    }
    
}
