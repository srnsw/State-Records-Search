// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.model.Agency;
import au.gov.nsw.records.search.model.Serie;
import au.gov.nsw.records.search.model.SerieCreatingAgency;
import java.util.List;

privileged aspect SerieCreatingAgency_Roo_JavaBean {
    
    public List<Agency> SerieCreatingAgency.getAgencyId() {
        return this.agencyId;
    }
    
    public void SerieCreatingAgency.setAgencyId(List<Agency> agencyId) {
        this.agencyId = agencyId;
    }
    
    public Serie SerieCreatingAgency.getSerie() {
        return this.serie;
    }
    
    public void SerieCreatingAgency.setSerie(Serie serie) {
        this.serie = serie;
    }
    
}
