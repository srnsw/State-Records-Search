package au.gov.nsw.records.search.bean;

public class RelationshipResultItem {
	private String title;
	private String link;
	private String type;
	
	public RelationshipResultItem(String title, String link, String type) {
		super();
		this.title = title;
		this.link = link;
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}
	public String getLink() {
		return link;
	}
	public String getType() {
		return type;
	}
	
}
