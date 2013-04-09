package au.gov.nsw.records.search.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.gov.nsw.records.search.model.Activity;
import au.gov.nsw.records.search.model.Agency;
import au.gov.nsw.records.search.model.Item;
import au.gov.nsw.records.search.model.Ministry;
import au.gov.nsw.records.search.model.Person;
import au.gov.nsw.records.search.model.Portfolio;
import au.gov.nsw.records.search.service.ControllerUtils;

@RequestMapping("/portfolios")
@Controller
@RooWebScaffold(path = "portfolios", formBackingObject = Portfolio.class, update=false, create=false, delete=false)
public class PortfolioController {
	
	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("portfolios", Portfolio.findPortfolioEntries(firstResult, sizeNo));
            float nrOfPages = (float) Portfolio.countPortfolios() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
            uiModel.addAttribute("page", page);
            uiModel.addAttribute("size", size);
        } else {
            uiModel.addAttribute("portfolios", Portfolio.findAllPortfolios());
        }
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("view", "portfolios/list");
        uiModel.addAttribute("count", Portfolio.countPortfolios());
        return "portfolios/list";
    }

	@RequestMapping(value = "/{portfolioNumber}", produces = "text/html")
    public String show(@PathVariable("portfolioNumber") int portfolioNumber, Model uiModel,
    		@RequestParam(value = "preceding_page", required = false, defaultValue="1") Integer preceding_page ,
    		@RequestParam(value = "succeeding_page", required = false, defaultValue="1") Integer succeeding_page,
    		@RequestParam(value = "ministries_page", required = false, defaultValue="1") Integer ministries_page,
    		@RequestParam(value = "persons_page", required = false, defaultValue="1") Integer persons_page,
    		@RequestParam(value = "agencies_page", required = false, defaultValue="1") Integer agencies_page) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("portfolio", reformatNewLine(Portfolio.findPortfolio(portfolioNumber)));
        uiModel.addAttribute("itemId", portfolioNumber);
        
        if (Portfolio.findPortfolio(portfolioNumber)!=null){
	        int size = 5;
	        int arraySize =  Portfolio.findPortfolio(portfolioNumber).getPreceding().size();
	        uiModel.addAttribute("rel_preceding",  Portfolio.findPortfolio(portfolioNumber).getPreceding().subList(Math.max((preceding_page-1)*size, 0), Math.min(preceding_page*size, arraySize)));
	        uiModel.addAttribute("rel_preceding_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_preceding_page", preceding_page);
	        
	        arraySize =  Portfolio.findPortfolio(portfolioNumber).getSucceeding().size();
	        uiModel.addAttribute("rel_succeeding",  Portfolio.findPortfolio(portfolioNumber).getSucceeding().subList(Math.max((succeeding_page-1)*size, 0), Math.min(succeeding_page*size, arraySize)));
	        uiModel.addAttribute("rel_succeeding_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_succeeding_page", succeeding_page);
	        
	        arraySize =  Portfolio.findPortfolio(portfolioNumber).getMinistries().size();
	        uiModel.addAttribute("rel_ministries",  Portfolio.findPortfolio(portfolioNumber).getMinistries().subList(Math.max((ministries_page-1)*size, 0), Math.min(ministries_page*size, arraySize)));
	        uiModel.addAttribute("rel_ministries_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_ministries_page", ministries_page);
	        
	        arraySize =  Portfolio.findPortfolio(portfolioNumber).getPersons().size();
	        uiModel.addAttribute("rel_persons",  Portfolio.findPortfolio(portfolioNumber).getPersons().subList(Math.max((persons_page-1)*size, 0), Math.min(persons_page*size, arraySize)));
	        uiModel.addAttribute("rel_persons_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_persons_page", persons_page);
	        
	        arraySize =  Portfolio.findPortfolio(portfolioNumber).getAgencies().size();
	        uiModel.addAttribute("rel_agencies",  Portfolio.findPortfolio(portfolioNumber).getAgencies().subList(Math.max((agencies_page-1)*size, 0), Math.min(agencies_page*size, arraySize)));
	        uiModel.addAttribute("rel_agencies_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_agencies_page", agencies_page);
        }
        uiModel.addAttribute("view", "portfolios/show");
        return "portfolios/show";
    }
	
	private static Portfolio reformatNewLine(Portfolio portf){
		if (portf.getDescriptiveNote()!=null){
			portf.setDescriptiveNote(portf.getDescriptiveNote().replaceAll("\n", "<br/>"));
		}
		return portf;
	}
	@RequestMapping(value="/{portfolioNumber}/preceding", produces = "text/html")
	public String listPreceding(@PathVariable("portfolioNumber") int portfolioNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Portfolio pf = Portfolio.findPortfolio(portfolioNumber);
		if (pf!=null){
			ControllerUtils.populateRelationshipModel(pf.getPreceding(), "portfolios", page, size, uiModel, Portfolio.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "portfolios/list");
		return "portfolios/list";
	}
	
	@RequestMapping(value="/{portfolioNumber}/succeeding", produces = "text/html")
	public String listSucceeding(@PathVariable("portfolioNumber") int portfolioNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Portfolio pf = Portfolio.findPortfolio(portfolioNumber);
		if (pf!=null){
			ControllerUtils.populateRelationshipModel(pf.getSucceeding(), "portfolios", page, size, uiModel, Portfolio.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "portfolios/list");
		return "portfolios/list";
	}
	
	@RequestMapping(value="/{portfolioNumber}/ministries", produces = "text/html")
	public String listMinistries(@PathVariable("portfolioNumber") int portfolioNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Portfolio pf = Portfolio.findPortfolio(portfolioNumber);
		if (pf!=null){
			ControllerUtils.populateRelationshipModel(pf.getMinistries(), "ministrys", page, size, uiModel, Ministry.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "ministries/list");
		return "ministries/list";
	}
	
	@RequestMapping(value="/{portfolioNumber}/persons", produces = "text/html")
	public String listPersons(@PathVariable("portfolioNumber") int portfolioNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Portfolio pf = Portfolio.findPortfolio(portfolioNumber);
		if (pf!=null){
			ControllerUtils.populateRelationshipModel(pf.getPersons(), "people", page, size, uiModel, Person.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "people/list");
		return "people/list";
	}
	
	@RequestMapping(value="/{portfolioNumber}/agencies", produces = "text/html")
	public String listAgencies(@PathVariable("portfolioNumber") int portfolioNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Portfolio pf = Portfolio.findPortfolio(portfolioNumber);
		if (pf!=null){
			ControllerUtils.populateRelationshipModel(pf.getAgencies(), "agencys", page, size, uiModel, Agency.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "agencies/list");
		return "agencies/list";
	}
}
