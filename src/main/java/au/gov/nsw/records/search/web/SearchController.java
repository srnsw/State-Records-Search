package au.gov.nsw.records.search.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.lucene.facet.index.CategoryDocumentBuilder;
import org.apache.lucene.facet.search.params.CountFacetRequest;
import org.apache.lucene.facet.search.params.FacetSearchParams;
import org.apache.lucene.facet.taxonomy.CategoryPath;
import org.apache.lucene.index.CorruptIndexException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import au.gov.nsw.records.search.model.Activity;
import au.gov.nsw.records.search.model.Agency;
import au.gov.nsw.records.search.model.Functionn;
import au.gov.nsw.records.search.model.Item;
import au.gov.nsw.records.search.model.Ministry;
import au.gov.nsw.records.search.model.Person;
import au.gov.nsw.records.search.model.Portfolio;
import au.gov.nsw.records.search.model.Serie;
import au.gov.nsw.records.search.service.LuceneSearchParams;
import au.gov.nsw.records.search.service.LuceneService;
import au.gov.nsw.records.search.service.SearchResult;

@RequestMapping("/search/**")
@Controller
public class SearchController {

		private static final Logger logger = Logger.getLogger(SearchController.class);
		
	  private static LuceneService lucene = new LuceneService();
	
    @RequestMapping
    public String index() {
        return "search/index";
    }
    
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public void startIndex(HttpServletRequest request, HttpServletResponse response) {
     	ExecutorService indexWorker = Executors.newSingleThreadExecutor();
     	
     	indexWorker.execute(new Runnable() {
				@Override
				public void run() {
		    	try {
		    		
		    		CategoryDocumentBuilder builder = lucene.startWriting();
						//Activities
						logger.info("Indexing activities");
						lucene.indexDocuments(Activity.getIndexData(Activity.findAllActivitys(), builder), Activity.class);
						lucene.commit();
						
						//Agencies
						logger.info("Indexing agencies");
						lucene.indexDocuments(Agency.getIndexData(Agency.findAllAgencys(), builder), Agency.class); 
						lucene.commit();
						
						logger.info("Indexing Series");
						lucene.indexDocuments(Serie.getIndexData(Serie.findAllSeries(), builder), Serie.class); 
						lucene.commit();
						
						logger.info("Indexing ministries");
						lucene.indexDocuments(Ministry.getIndexData(Ministry.findAllMinistrys(), builder), Ministry.class);
						lucene.commit();
						
						logger.info("Indexing persons");
						lucene.indexDocuments(Person.getIndexData(Person.findAllPeople(), builder), Person.class);
						lucene.commit();
						
						logger.info("Indexing portfolios");
						lucene.indexDocuments(Portfolio.getIndexData(Portfolio.findAllPortfolios(), builder), Portfolio.class);
						lucene.commit();
										
						logger.info("Indexing items");
						//lucene.indexDocuments(Item.getIndexData(Item.findAllItems()), Item.class);
						long itemCount = Item.countItems();
						int pageSize = 10000;
						int pageCount = 0;
						while(pageCount * pageSize < itemCount){
							logger.info("Items page " + pageCount + " of " + itemCount/pageSize);
							lucene.indexDocuments(Item.getIndexData(Item.findItemEntries(pageCount * pageSize, pageSize), builder), Item.class);
							lucene.commit();
							pageCount++;
						}
						lucene.finishWriting();
						
						logger.info("Indexing finished");
		    	} catch (CorruptIndexException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

			logger.info("Indexing started");
    }
    
  	@RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "page", required = false, defaultValue="1") Integer page, 
    		@RequestParam(value = "fpage", required = false, defaultValue="1") Integer fpage,
    		@RequestParam(value = "apage", required = false, defaultValue="1") Integer apage,
    		@RequestParam(value = "size", required = false, defaultValue="30") Integer pageSize,
    		@RequestParam(value = "location", required = false) String location,
    		@RequestParam(value = "series", required = false) String series,
    		@RequestParam(value = "from", required = false) String from,
    		@RequestParam(value = "to", required = false) String to,
    		@RequestParam("q") String queryText,
    		Model model, HttpServletRequest request) {
  		
  		LuceneSearchParams params = new LuceneSearchParams();
  		FacetSearchParams facetParams = new FacetSearchParams();
  		facetParams.addFacetRequest(new CountFacetRequest(new CategoryPath("location"), 10));
  		facetParams.addFacetRequest(new CountFacetRequest(new CategoryPath("series"), 10));
  		facetParams.addFacetRequest(new CountFacetRequest(new CategoryPath("from", "to"), 10));
  		
  		SearchResult activitiesFunctions = lucene.search(params.setQuery(queryText).setFacetParams(facetParams).setPage(fpage).setSize(pageSize).setClazz(Activity.class, Functionn.class));
  		SearchResult seriesItems = lucene.search(params.setQuery(queryText).setFacetParams(facetParams).setPage(page).setSize(pageSize).setClazz(Serie.class, Item.class));
  		SearchResult agenciesPeople = lucene.search(params.setQuery(queryText).setFacetParams(facetParams).setPage(apage).setSize(pageSize).setClazz(Agency.class, Person.class));
  		//SearchResult activitiesFunctions = lucene.search(queryText, facetParams, fpage, pageSize, Activity.class, Functionn.class);
  		//SearchResult seriesItems = lucene.search(queryText, facetParams, page, pageSize, Serie.class, Item.class);
  		//SearchResult agenciesPeople = lucene.search(queryText, facetParams, apage, pageSize, Agency.class, Person.class);
      
      model.addAttribute("q", queryText);
      
      model.addAttribute("activitiesfunctions", activitiesFunctions.getResults());
      model.addAttribute("activitiesfunctions_count", Math.ceil(activitiesFunctions.getResultCount()/Double.valueOf(pageSize)));
      
      model.addAttribute("seriesitems", seriesItems.getResults());
      model.addAttribute("seriesitems_count", Math.ceil(seriesItems.getResultCount()/Double.valueOf(pageSize)));
      
      model.addAttribute("agenciespeoples", agenciesPeople.getResults());
      model.addAttribute("agenciespeoples_count", Math.ceil(agenciesPeople.getResultCount()/Double.valueOf(pageSize)));
      
      model.addAttribute("facets", seriesItems.getFacets());
      model.addAttribute("baseurl", request.getRequestURL() + String.format("?q=%s", queryText));
      return "search/list";
    }
}
