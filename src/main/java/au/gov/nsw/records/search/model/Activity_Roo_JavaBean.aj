// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.Activity;
import au.gov.nsw.records.search.model.ActivityLinkFunction;
import au.gov.nsw.records.search.model.ActivityLinkSerie;
import java.util.Date;
import java.util.List;

privileged aspect Activity_Roo_JavaBean {
    
    public int Activity.getActivityNumber() {
        return this.activityNumber;
    }
    
    public void Activity.setActivityNumber(int activityNumber) {
        this.activityNumber = activityNumber;
    }
    
    public String Activity.getTitle() {
        return this.title;
    }
    
    public void Activity.setTitle(String title) {
        this.title = title;
    }
    
    public String Activity.getDescriptiveNote() {
        return this.descriptiveNote;
    }
    
    public void Activity.setDescriptiveNote(String descriptiveNote) {
        this.descriptiveNote = descriptiveNote;
    }
    
    public Date Activity.getStartDate() {
        return this.startDate;
    }
    
    public void Activity.setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public String Activity.getStartDateQualifier() {
        return this.startDateQualifier;
    }
    
    public void Activity.setStartDateQualifier(String startDateQualifier) {
        this.startDateQualifier = startDateQualifier;
    }
    
    public Date Activity.getEndDate() {
        return this.endDate;
    }
    
    public void Activity.setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String Activity.getEndDateQualifier() {
        return this.endDateQualifier;
    }
    
    public void Activity.setEndDateQualifier(String endDateQualifier) {
        this.endDateQualifier = endDateQualifier;
    }
    
    public List<ActivityLinkSerie> Activity.getSeries() {
        return this.series;
    }
    
    public void Activity.setSeries(List<ActivityLinkSerie> series) {
        this.series = series;
    }
    
    public List<ActivityLinkFunction> Activity.getFunctions() {
        return this.functions;
    }
    
    public void Activity.setFunctions(List<ActivityLinkFunction> functions) {
        this.functions = functions;
    }
    
}
