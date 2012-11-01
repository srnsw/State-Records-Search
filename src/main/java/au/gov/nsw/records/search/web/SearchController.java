package au.gov.nsw.records.search.web;

import java.io.IOException;
import java.util.List;
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

import au.gov.nsw.records.search.bean.FacetResultItem;
import au.gov.nsw.records.search.bean.SearchResult;
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

@RequestMapping("/search/**")
@Controller
public class SearchController {

		private static final Logger logger = Logger.getLogger(SearchController.class);
		
	  private static LuceneService lucene = new LuceneService();
	  private static boolean isIndexing = false;
	
	  @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "search/index";
    }
    
    @RequestMapping(value = "/createindex", method = RequestMethod.POST)
    public void startIndex(HttpServletRequest request, HttpServletResponse response) {
     	ExecutorService indexWorker = Executors.newSingleThreadExecutor();
     	if (!isIndexing){
     		indexWorker.execute(new Runnable() {
  				@Override
  				public void run() {
  		    	try {
  		    		
  		    		isIndexing = true;
  		    		
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
  						long itemCount = Item.countItems();
  						int pageSize = 15000;
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
  					}finally{
  						isIndexing = false;
  					}
  				}
  			});

  			logger.info("Indexing started");
     	}else{
     		logger.warn("Indexing already in progress");
     	}
     	
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
  		
  		if (page==null){
  			page=1;
  		}
  		if (pageSize==null){
  			pageSize=30;
  		}
  		LuceneSearchParams params = new LuceneSearchParams();
  		FacetSearchParams facetParams = new FacetSearchParams();
  		
  		facetParams.addFacetRequest(new CountFacetRequest(new CategoryPath("series_id"), 10));
  		facetParams.addFacetRequest(new CountFacetRequest(new CategoryPath("series"), 10));
  		facetParams.addFacetRequest(new CountFacetRequest(new CategoryPath("location"), 10));
  		//facetParams.addFacetRequest(new CountFacetRequest(new CategoryPath("startyear", "endyear"), 10));
  		int asize = 15;
  		int fsize = 5;
  		
  		SearchResult activitiesFunctions = lucene.search(params.setQuery(queryText).setFacetParams(facetParams).setPage(fpage).setSize(fsize).setClazz(Activity.class, Functionn.class));
  		SearchResult agenciesPeople = lucene.search(params.setQuery(queryText).setFacetParams(facetParams).setPage(apage).setSize(asize).setClazz(Agency.class, Person.class));
  		try{
  		SearchResult seriesItems = lucene.search(params.setQuery(queryText).setSeries(series).setLocation(location).setFrom(from).setTo(to).setFacetParams(facetParams).setPage(page).setSize(pageSize).setClazz(Serie.class, Item.class));
      
  		String nonPageParams = "&q=" + queryText;
      if (location!=null){ nonPageParams += "&location=" + location; }
      if (series!=null){ nonPageParams += "&series=" + series; }
      if (from!=null){ nonPageParams += "&from=" + from; }
      if (to!=null){ nonPageParams += "&to=" + to; }
      
      List<FacetResultItem> facets = seriesItems.getFacets();
      for (FacetResultItem fri:facets){
      	if (fri.getLabel().equals("series")){
      		for (FacetResultItem subFri:fri.getItems()){
      			subFri.setLabel(Serie.findSerie(Integer.valueOf(subFri.getLabel())).getTitle());
      		}
      	}
      }
  	
      
      model.addAttribute("q", queryText);
      
      model.addAttribute("page", page);
      model.addAttribute("fpage", fpage);
      model.addAttribute("apage", apage);
      model.addAttribute("size", pageSize);
      model.addAttribute("asize", asize);
      model.addAttribute("fsize", fsize);
      
      model.addAttribute("location", location);
      model.addAttribute("series", series);
      model.addAttribute("from", from);
      model.addAttribute("to", to);
    
      model.addAttribute("nonPageParams", nonPageParams);
      
      model.addAttribute("activitiesfunctions", activitiesFunctions.getResults());
      model.addAttribute("activitiesfunctions_count", Math.ceil(activitiesFunctions.getResultCount()/Double.valueOf(fsize)));
      
      model.addAttribute("seriesitems", seriesItems.getResults());
      model.addAttribute("seriesitems_count", Math.ceil(seriesItems.getResultCount()/Double.valueOf(pageSize)));
      
      model.addAttribute("agenciespeoples", agenciesPeople.getResults());
      model.addAttribute("agenciespeoples_count", Math.ceil(agenciesPeople.getResultCount()/Double.valueOf(asize)));
      
      model.addAttribute("facets", facets);
      model.addAttribute("baseurl", request.getRequestURL() + String.format("?q=%s", queryText));
  		}catch(Exception e){
  			e.printStackTrace();
  		}
      return "search/list";
    }
}

