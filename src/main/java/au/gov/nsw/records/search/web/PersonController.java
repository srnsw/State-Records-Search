package au.gov.nsw.records.search.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.gov.nsw.records.search.model.Agency;
import au.gov.nsw.records.search.model.Functionn;
import au.gov.nsw.records.search.model.Ministry;
import au.gov.nsw.records.search.model.Person;
import au.gov.nsw.records.search.model.Portfolio;
import au.gov.nsw.records.search.model.Serie;
import au.gov.nsw.records.search.service.ControllerUtils;

@RequestMapping("/persons")
@Controller
@RooWebScaffold(path = "people", formBackingObject = Person.class, update=false, create=false, delete=false)
public class PersonController {

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("people", Person.findPersonEntries(firstResult, sizeNo));
            float nrOfPages = (float) Person.countPeople() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
            uiModel.addAttribute("page", page);
            uiModel.addAttribute("size", size);
        } else {
            uiModel.addAttribute("people", Person.findAllPeople());
        }
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("view", "people/list");
        return "people/list";
    }

	  @RequestMapping(value = "/{personNumber}", produces = "text/html")
    public String show(@PathVariable("personNumber") int personNumber, Model uiModel,
    		@RequestParam(value = "agencies_page", required = false, defaultValue="1") Integer agencies_page,
    		@RequestParam(value = "ministries_page", required = false, defaultValue="1") Integer ministries_page,
    		@RequestParam(value = "portfolios_page", required = false, defaultValue="1") Integer portfolios_page,
    		@RequestParam(value = "functions_page", required = false, defaultValue="1") Integer functions_page,
    		@RequestParam(value = "series_page", required = false, defaultValue="1") Integer series_page) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("person", Person.findPerson(personNumber));
        uiModel.addAttribute("itemId", personNumber);
        
        if (Person.findPerson(personNumber)!=null){
	        int size = 5;
	        int arraySize =  Person.findPerson(personNumber).getAgencies().size();
	        uiModel.addAttribute("rel_agencies",   Person.findPerson(personNumber).getAgencies().subList(Math.max((agencies_page-1)*size, 0), Math.min(agencies_page*size, arraySize)));
	        uiModel.addAttribute("rel_agencies_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_agencies_page", agencies_page);
	        
	        arraySize =  Person.findPerson(personNumber).getMinistries().size();
	        uiModel.addAttribute("rel_ministries",   Person.findPerson(personNumber).getMinistries().subList(Math.max((ministries_page-1)*size, 0), Math.min(ministries_page*size, arraySize)));
	        uiModel.addAttribute("rel_ministries_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_ministries_page", ministries_page);
	        
	        arraySize =  Person.findPerson(personNumber).getPortfolios().size();
	        uiModel.addAttribute("rel_portfolios",   Person.findPerson(personNumber).getPortfolios().subList(Math.max((portfolios_page-1)*size, 0), Math.min(portfolios_page*size, arraySize)));
	        uiModel.addAttribute("rel_portfolios_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_portfolios_page", portfolios_page);
	        
	        arraySize =  Person.findPerson(personNumber).getFunctions().size();
	        uiModel.addAttribute("rel_functions",   Person.findPerson(personNumber).getFunctions().subList(Math.max((functions_page-1)*size, 0), Math.min(functions_page*size, arraySize)));
	        uiModel.addAttribute("rel_functions_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_functions_page", functions_page);
	        
	        arraySize =  Person.findPerson(personNumber).getSeries().size();
	        uiModel.addAttribute("rel_series",   Person.findPerson(personNumber).getSeries().subList(Math.max((series_page-1)*size, 0), Math.min(series_page*size, arraySize)));
	        uiModel.addAttribute("rel_series_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_series_page", series_page);
        }
        uiModel.addAttribute("view", "people/show");
        return "people/show";
    }
	  
		@RequestMapping(value="/{personNumber}/agencies", produces = "text/html")
		public String listAgencies(@PathVariable("personNumber") int personNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

			Person ps = Person.findPerson(personNumber);
			if (ps!=null){
				ControllerUtils.populateRelationshipModel(ps.getAgencies(), "agencys", page, size, uiModel, Agency.class);
				addDateTimeFormatPatterns(uiModel);
			}
			 uiModel.addAttribute("view", "agencies/list");
			return "agencies/list";
		}
		
		@RequestMapping(value="/{personNumber}/ministries", produces = "text/html")
		public String listMinistries(@PathVariable("personNumber") int personNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

			Person ps = Person.findPerson(personNumber);
			if (ps!=null){
				ControllerUtils.populateRelationshipModel(ps.getMinistries(), "ministrys", page, size, uiModel, Ministry.class);
				addDateTimeFormatPatterns(uiModel);
			}
			 uiModel.addAttribute("view", "ministries/list");
			return "ministries/list";
		}
		
		@RequestMapping(value="/{personNumber}/portfolios", produces = "text/html")
		public String listPortfolios(@PathVariable("personNumber") int personNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

			Person ps = Person.findPerson(personNumber);
			if (ps!=null){
				ControllerUtils.populateRelationshipModel(ps.getPortfolios(), "portfolios", page, size, uiModel, Portfolio.class);
				addDateTimeFormatPatterns(uiModel);
			}
			 uiModel.addAttribute("view", "portfolios/list");
			return "portfolios/list";
		}
		
		@RequestMapping(value="/{personNumber}/functions", produces = "text/html")
		public String listFunctions(@PathVariable("personNumber") int personNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

			Person ps = Person.findPerson(personNumber);
			if (ps!=null){
				ControllerUtils.populateRelationshipModel(ps.getFunctions(), "functionns", page, size, uiModel, Functionn.class);
				addDateTimeFormatPatterns(uiModel);
			}
			 uiModel.addAttribute("view", "functions/list");
			return "functions/list";
		}
		
		@RequestMapping(value="/{personNumber}/series", produces = "text/html")
		public String listSeries(@PathVariable("personNumber") int personNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

			Person ps = Person.findPerson(personNumber);
			if (ps!=null){
				ControllerUtils.populateRelationshipModel(ps.getSeries(), "series", page, size, uiModel, Serie.class);
				addDateTimeFormatPatterns(uiModel);
			}
			 uiModel.addAttribute("view", "series/list");
			return "series/list";
		}
}
