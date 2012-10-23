package au.gov.nsw.records.search.bean;

public class SearchResultItem {

	private String type;
	private String title;
	private String content;
	private String id;
	private String url;
	private boolean isTitleEqualContent;


	public SearchResultItem(String type, String title, String content, String id, String url, boolean isTitleEqualContent) {
		super();
		this.type = type;
		this.title = title;
		this.content = content;
		this.id = id;
		this.url = url;
		this.isTitleEqualContent = isTitleEqualContent;
	}
	
	public boolean isTitleEqualContent() {
		return isTitleEqualContent;
	}

	public String getType() {
		return type;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public String getId() {
		return id;
	}
	public String getUrl() {
		return url;
	}
	
	
}
