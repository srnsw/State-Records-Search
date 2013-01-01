package au.gov.nsw.records.search.model;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
@Table(name="items_view")
public class SerieLinkItem implements Serializable{

	@ManyToOne
	@JoinColumn(name = "Series_number")
	private Serie seriesNumber;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "ID")
	private Item id;
	
	public Item getRelationship(){
		return id;
	}
	
	public String toString() {
    return String.valueOf(this.getId());
	}
}
