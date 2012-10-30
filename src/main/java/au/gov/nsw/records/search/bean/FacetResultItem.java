package au.gov.nsw.records.search.bean;

import java.util.List;

public class FacetResultItem {

	private String value;
	private String label;

	private int count;
	private List<FacetResultItem> items;
	
	public FacetResultItem(String value, int count, List<FacetResultItem> items) {
		super();
		this.value = value;
		this.count = count;
		this.items = items;
	}

	public List<FacetResultItem> getItems() {
		return items;
	}
	public void setItems(List<FacetResultItem> items) {
		this.items = items;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setLabel(String label){
		this.label = label;
	}
	public String getLabel(){
		if (label==null){
			return value;
		}
		return label;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
