package au.gov.nsw.records.search.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import au.gov.nsw.records.search.service.DateHelper;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
@Table(name="agencies_link_preceding")
public class AgencyLinkPreceding implements Serializable{

	@Id
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "agency_id")
	private Agency agencyId;
	
	@ManyToOne
	@JoinColumn(name = "preceding_id")
	private Agency preceedingId;
	
	@Column(name = "SDate")
	private String startDate;

	@Column(name = "SDatequalifier")
	private String startDateQualifier;

	@Column(name = "EDate")
	private String endDate;
	
	@Column(name = "EDatequalifier")
	private String endDateQualifier;
	
	public Agency getRelationship(){
		return preceedingId;
	}
	
	public String toString() {
    return String.valueOf(this.getId());
	}
	
	public String getDateRange() {
		return DateHelper.dateRange(startDateQualifier, startDate, endDateQualifier, endDate);
	}	
}
