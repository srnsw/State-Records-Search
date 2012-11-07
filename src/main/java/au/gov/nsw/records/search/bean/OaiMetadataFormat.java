package au.gov.nsw.records.search.bean;

public class OaiMetadataFormat {

	private String metadataPrefix;
	private String schema;
	private String metadataNamespace;
	
	public OaiMetadataFormat(String metadataPrefix, String schema, String metadataNamespace) {
		super();
		this.metadataPrefix = metadataPrefix;
		this.schema = schema;
		this.metadataNamespace = metadataNamespace;
	}
	
	public String getMetadataPrefix() {
		return metadataPrefix;
	}
	public void setMetadataPrefix(String metadataPrefix) {
		this.metadataPrefix = metadataPrefix;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getMetadataNamespace() {
		return metadataNamespace;
	}
	public void setMetadataNamespace(String metadataNamespace) {
		this.metadataNamespace = metadataNamespace;
	}
	
}
