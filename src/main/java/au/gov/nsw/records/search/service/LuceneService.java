package au.gov.nsw.records.search.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.facet.index.CategoryDocumentBuilder;
import org.apache.lucene.facet.search.FacetsCollector;
import org.apache.lucene.facet.search.params.FacetSearchParams;
import org.apache.lucene.facet.search.results.FacetResult;
import org.apache.lucene.facet.search.results.FacetResultNode;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.TaxonomyWriter;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiCollector;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.core.io.ClassPathResource;

import au.gov.nsw.records.search.bean.FacetResultItem;
import au.gov.nsw.records.search.bean.SearchResult;
import au.gov.nsw.records.search.bean.SearchResultItem;

public class LuceneService {

	private static IndexWriter writer;
	private static TaxonomyWriter taxo;
	private static ClassPathResource cpr = new ClassPathResource("lucene.properties");
	
	private static final String INDEX_LOC_PROPERTY = "indexlocation";
	private static final String TEXO_LOC_PROPERTY = "taxolocation";
	
	private static Logger log = Logger.getLogger(LuceneService.class);
	
	public CategoryDocumentBuilder startWriting() throws IOException{
		Properties prop = new Properties();
		prop.load(new FileInputStream(cpr.getFile()));
		
		Directory dir = FSDirectory.open(new File(prop.getProperty(INDEX_LOC_PROPERTY)));
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_31, analyzer);
		// Create a new index in the directory, removing any
		// previously indexed documents:
		iwc.setOpenMode(OpenMode.CREATE);

		// Optional: for better indexing performance, if you
		// are indexing many documents, increase the RAM
		// buffer.  But if you do this, increase the max heap
		// size to the JVM (eg add -Xmx512m or -Xmx1g):
		iwc.setRAMBufferSizeMB(512.0);

		writer = new IndexWriter(dir, iwc);
		
		Directory taxoDir = FSDirectory.open(new File(prop.getProperty(TEXO_LOC_PROPERTY)));
		taxo = new DirectoryTaxonomyWriter(taxoDir, OpenMode.CREATE);
		
		return new CategoryDocumentBuilder(taxo);
	}
	
	public void commit() throws CorruptIndexException, IOException{
		taxo.commit();
		writer.commit();
	}
	
	public void finishWriting() throws CorruptIndexException, IOException{
		taxo.close();
		writer.close();
	}
	
	public void indexDocuments(List<Document> docs, Class<?> clazz){
		try {
			for (Document doc:docs){
				doc.add(new Field("class", clazz.getName(), Field.Store.NO, Field.Index.ANALYZED));
				writer.addDocument(doc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private SearchResult search(Query query, Query hilightQuery, FacetSearchParams facets, Integer page, Integer size){
		List<SearchResultItem> searchResults = new ArrayList<SearchResultItem>(); 
		List<FacetResultItem> facetResults = new ArrayList<FacetResultItem>();
		int numTotalHits = 0;
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(cpr.getFile()));
			
			IndexReader indexReader = IndexReader.open(FSDirectory.open(new File(prop.getProperty(INDEX_LOC_PROPERTY))));
			IndexSearcher searcher = new IndexSearcher(indexReader);
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);

			Directory taxoDir = FSDirectory.open(new File(prop.getProperty(TEXO_LOC_PROPERTY)));
			TaxonomyReader taxoReader = new DirectoryTaxonomyReader(taxoDir);
			FacetsCollector facetsCollector = new FacetsCollector(facets, indexReader, taxoReader);
			
			TopScoreDocCollector docCollector = TopScoreDocCollector.create(page.intValue()*size.intValue(), true);
			
			searcher.search(query, MultiCollector.wrap(docCollector, facetsCollector));
			
			TopDocs docs = docCollector.topDocs((page.intValue()-1)*size.intValue(), size.intValue());
			numTotalHits = docCollector.getTotalHits();
			log.info(numTotalHits + " total matching documents");
			
			for (ScoreDoc hit:docs.scoreDocs){
				Document doc = searcher.doc(hit.doc);
				Formatter formatter = new SimpleHTMLFormatter("<b>","</b>");
				Scorer fragmentScorer = new QueryScorer(hilightQuery);
				Highlighter highlighter = new Highlighter(formatter, fragmentScorer);
				Fragmenter fragmenter = new SimpleFragmenter(150);
				highlighter.setTextFragmenter(fragmenter);
				searchResults.add(new SearchResultItem(doc.get("type"), doc.get("title"), highlighter.getBestFragment(analyzer, "content", doc.get("content")), doc.get("id"), doc.get("url"), doc.get("title").equals(doc.get("content"))));
			}

			for (FacetResult fr:facetsCollector.getFacetResults()){
				List<FacetResultItem> subItems = new ArrayList<FacetResultItem>();
				facetResults.add(new FacetResultItem(fr.getFacetResultNode().getLabel().toString(),0, subItems));
				for (FacetResultNode rn: fr.getFacetResultNode().getSubResults()){
					subItems.add(new FacetResultItem(rn.getLabel().toString().replace(fr.getFacetResultNode().getLabel().toString() + "/", ""), new Double(rn.getValue()).intValue(), null));
				}
			}
			searcher.close();
			indexReader.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (InvalidTokenOffsetsException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new SearchResult(searchResults, facetResults, numTotalHits);
	}

	public SearchResult search(LuceneSearchParams params){
		String classQuery = "";
		// multi-entity search
		boolean first = true;;
		for (Class<?> clazz: params.getClazz()){
			if (first){
				first = false;
			}else{
				classQuery += " OR ";
			}
			classQuery += String.format("class:( +\"%s\")", clazz.getName());
		}
		String facetCondition = "";
		if (params.getLocation()!=null){
			facetCondition += String.format(" AND location:( +\"%s\")", params.getLocation());
		}
		if (params.getSeries()!=null){
			facetCondition += String.format(" AND series:( +\"%s\")", params.getSeries());
		}
		
		if (params.getFrom()!=null && params.getTo()!=null){
			facetCondition += String.format(" AND (endyear:[%s TO 9999] AND startyear:[0000 TO %s])", params.getFrom(), params.getTo());
		}
		
		
		try {
			String queryText = params.getQuery() + String.format(" %s (%s)", params.getQuery().isEmpty()?"":"AND", classQuery) + facetCondition; 
		
			//general query
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
			MultiFieldQueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_31, new String[] {"title", "content"},analyzer);
			Query baseQuery = queryParser.parse(queryText);
			log.info("Query:" + baseQuery.toString());
			Query hilightQuery = queryParser.parse(params.getQuery().isEmpty()?"\"\"":params.getQuery());
			return search(baseQuery, hilightQuery, params.getFacetParams(), params.getPage(), params.getSize());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
    return new SearchResult(new ArrayList<SearchResultItem>(), new ArrayList<FacetResultItem>(), 0); 
	}
}
