package au.gov.nsw.records.search.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
@Table(name="series_link_creating_agencies")
public class SerieCreatingAgency implements Serializable{
	
	@OneToMany(mappedBy = "agencyNumber")
//	@Column(name = "agency_id")
	private List<Agency> agencyId;
	
	@ManyToOne
	@JoinColumn(name = "series_id")
	@Id
	private Serie serie;
}
