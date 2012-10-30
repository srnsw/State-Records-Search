package au.gov.nsw.records.search.model;

import au.gov.nsw.records.search.service.DateHelper;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
@Table(name = "series_link_controlled_series")
public class SerieLinkControlledSerie implements Serializable {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Serie seriesId;

    @ManyToOne
    @JoinColumn(name = "controlled_id")
    private Serie serieId;

    @Column(name = "SDate")
    private String startDate;

    @Column(name = "SDatequalifier")
    private String startDateQualifier;

    @Column(name = "EDate")
    private String endDate;

    @Column(name = "EDatequalifier")
    private String endDateQualifier;

    public Serie getRelationship() {
        return serieId;
    }

    public String getDateRange() {
        return DateHelper.dateRange(startDateQualifier, startDate, endDateQualifier, endDate);
    }

    public String toString() {
        return String.valueOf(this.getId());
    }
}
