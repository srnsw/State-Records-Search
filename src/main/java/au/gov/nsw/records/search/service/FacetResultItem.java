package au.gov.nsw.records.search.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class FacetResultItem {

	private String label;
	private String category;
	private int count;
	private List<FacetResultItem> items;
	
	public FacetResultItem(String label, String category, int count, List<FacetResultItem> items) {
		super();
		this.label = label;
		this.category = category;
		this.count = count;
		this.items = items;
	}

	public List<FacetResultItem> getItems() {
		return items;
	}
	public void setItems(List<FacetResultItem> items) {
		this.items = items;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getUrl() {
		try {
			return URLEncoder.encode(String.format("?%s=%s", category, label), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return String.format("?%s=%s", category, label);
		}
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
