// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.Agency;
import au.gov.nsw.records.search.model.Serie;
import au.gov.nsw.records.search.model.SerieLinkCreatingAgency;

privileged aspect SerieLinkCreatingAgency_Roo_JavaBean {
    
    public int SerieLinkCreatingAgency.getId() {
        return this.id;
    }
    
    public void SerieLinkCreatingAgency.setId(int id) {
        this.id = id;
    }
    
    public Serie SerieLinkCreatingAgency.getSerieId() {
        return this.serieId;
    }
    
    public void SerieLinkCreatingAgency.setSerieId(Serie serieId) {
        this.serieId = serieId;
    }
    
    public Agency SerieLinkCreatingAgency.getAgencyId() {
        return this.agencyId;
    }
    
    public void SerieLinkCreatingAgency.setAgencyId(Agency agencyId) {
        this.agencyId = agencyId;
    }
    
    public String SerieLinkCreatingAgency.getStartDate() {
        return this.startDate;
    }
    
    public void SerieLinkCreatingAgency.setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String SerieLinkCreatingAgency.getStartDateQualifier() {
        return this.startDateQualifier;
    }
    
    public void SerieLinkCreatingAgency.setStartDateQualifier(String startDateQualifier) {
        this.startDateQualifier = startDateQualifier;
    }
    
    public String SerieLinkCreatingAgency.getEndDate() {
        return this.endDate;
    }
    
    public void SerieLinkCreatingAgency.setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public String SerieLinkCreatingAgency.getEndDateQualifier() {
        return this.endDateQualifier;
    }
    
    public void SerieLinkCreatingAgency.setEndDateQualifier(String endDateQualifier) {
        this.endDateQualifier = endDateQualifier;
    }
    
}
