package au.gov.nsw.records.search.bean;

public class OaiSetSpec {

	private String spec;
	private String name;
	private String description;
	
	public OaiSetSpec(String spec, String name, String description) {
		super();
		this.spec = spec;
		this.name = name;
		this.description = description;
	}
	
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
