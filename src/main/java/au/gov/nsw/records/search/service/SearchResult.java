package au.gov.nsw.records.search.service;

import java.util.List;

public class SearchResult {

	private List<SearchResultItem> results;
	private int resultCount;
	private List<FacetResultItem> facets;
	
	public SearchResult(List<SearchResultItem> results, List<FacetResultItem> facets, int resultCount) {
		super();
		this.results = results;
		this.facets = facets;
		this.resultCount = resultCount;
	}
	
	public int getResultCount() {
		return resultCount;
	}
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
	
	public List<SearchResultItem> getResults() {
		return results;
	}
	public void setResults(List<SearchResultItem> results) {
		this.results = results;
	}
	public List<FacetResultItem> getFacets() {
		return facets;
	}
	public void setFacets(List<FacetResultItem> facets) {
		this.facets = facets;
	}
	
}
