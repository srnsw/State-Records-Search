package au.gov.nsw.records.search.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.facet.search.params.FacetSearchParams;

public class LuceneSearchParams {

	private String query;
	private FacetSearchParams facetParams;
	private String from;
	private String to;
	private String series;
	private String location;
	private int size;
	private int page;
	private List<Class<?>> clazz;
	
	public List<Class<?>> getClazz() {
		return clazz;
	}
	public LuceneSearchParams setClazz(Class<?>... clazz) {
		this.clazz = new ArrayList<Class<?>>(Arrays.asList(clazz));
		return this;
	}
	public String getQuery() {
		return query;
	}
	public LuceneSearchParams setQuery(String query) {
		this.query = query;
		return this;
	}
	public FacetSearchParams getFacetParams() {
		return facetParams;
	}
	public LuceneSearchParams setFacetParams(FacetSearchParams facetParams) {
		this.facetParams = facetParams;
		return this;
	}
	public String getFrom() {
		return from;
	}
	public LuceneSearchParams setFrom(String from) {
		this.from = from;
		return this;
	}
	public String getTo() {
		return to;
	}
	public LuceneSearchParams setTo(String to) {
		this.to = to;
		return this;
	}
	public String getSeries() {
		return series;
	}
	public LuceneSearchParams setSeries(String series) {
		this.series = series;
		return this;
	}
	public String getLocation() {
		return location;
	}
	public LuceneSearchParams setLocation(String location) {
		this.location = location;
		return this;
	}
	public int getSize() {
		return size;
	}
	public LuceneSearchParams setSize(int size) {
		this.size = size;
		return this;
	}
	public int getPage() {
		return page;
	}
	public LuceneSearchParams setPage(int page) {
		this.page = page;
		return this;
	}
	
	
	
}
