package au.gov.nsw.records.search.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
@Table(name = "accessdirections_view")
public class AccessDirection {

	private String scope;
	private String furtherDetails;
	private String agencyTitle;
	private String typeAccess;
	private String effect;
	private String cpaDuration;
	private String cpaReasons;
	private String location;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date authorisationDate;
}
