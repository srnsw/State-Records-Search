// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.Item;
import au.gov.nsw.records.search.model.Serie;
import au.gov.nsw.records.search.model.SerieLinkItem;

privileged aspect SerieLinkItem_Roo_JavaBean {
    
    public Serie SerieLinkItem.getSeriesNumber() {
        return this.seriesNumber;
    }
    
    public void SerieLinkItem.setSeriesNumber(Serie seriesNumber) {
        this.seriesNumber = seriesNumber;
    }
    
    public Item SerieLinkItem.getId() {
        return this.id;
    }
    
    public void SerieLinkItem.setId(Item id) {
        this.id = id;
    }
    
}
