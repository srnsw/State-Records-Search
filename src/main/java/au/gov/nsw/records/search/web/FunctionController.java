package au.gov.nsw.records.search.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.gov.nsw.records.search.model.Activity;
import au.gov.nsw.records.search.model.Agency;
import au.gov.nsw.records.search.model.Functionn;
import au.gov.nsw.records.search.model.Person;
import au.gov.nsw.records.search.service.ControllerUtils;

@RequestMapping("/functions")
@Controller
@RooWebScaffold(path = "functions", formBackingObject = Functionn.class, update=false, create=false, delete=false)
public class FunctionController {
	
	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("functionns", Functionn.findFunctionnEntries(firstResult, sizeNo));
            float nrOfPages = (float) Functionn.countFunctionns() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
            uiModel.addAttribute("page", page);
            uiModel.addAttribute("size", size);
        } else {
            uiModel.addAttribute("functionns", Functionn.findAllFunctionns());
        }
        addDateTimeFormatPatterns(uiModel);
      	uiModel.addAttribute("view", "functions/list");
        return "functions/list";
    }

	@RequestMapping(value = "/{functionNumber}", produces = "text/html")
    public String show(@PathVariable("functionNumber") int functionNumber, Model uiModel,
    		@RequestParam(value = "activities_page", required = false, defaultValue="1") Integer activities_page,
    		@RequestParam(value = "agencies_page", required = false, defaultValue="1") Integer agencies_page,
    		@RequestParam(value = "persons_page", required = false, defaultValue="1") Integer persons_page) {
		
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("functionn", Functionn.findFunctionn(functionNumber));
        uiModel.addAttribute("itemId", functionNumber);
        
        if (Functionn.findFunctionn(functionNumber)!=null){
	        int size = 5;
	        int arraySize =  Functionn.findFunctionn(functionNumber).getActivities().size();
	        uiModel.addAttribute("rel_activities",  Functionn.findFunctionn(functionNumber).getActivities().subList(Math.max((activities_page-1)*size, 0), Math.min(activities_page*size, arraySize)));
	        uiModel.addAttribute("rel_activities_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_activities_page", activities_page);
	        
	        arraySize =  Functionn.findFunctionn(functionNumber).getAgencies().size();
	        uiModel.addAttribute("rel_agencies",  Functionn.findFunctionn(functionNumber).getAgencies().subList(Math.max((agencies_page-1)*size, 0), Math.min(agencies_page*size, arraySize)));
	        uiModel.addAttribute("rel_agencies_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_agencies_page", agencies_page);
	        
	        arraySize =  Functionn.findFunctionn(functionNumber).getPersons().size();
	        uiModel.addAttribute("rel_persons",  Functionn.findFunctionn(functionNumber).getPersons().subList(Math.max((persons_page-1)*size, 0), Math.min(persons_page*size, arraySize)));
	        uiModel.addAttribute("rel_persons_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_persons_page", persons_page);
        }
      	uiModel.addAttribute("view", "functions/show");
        return "functions/show";
    }
	
	@RequestMapping(value="/{functionNumber}/agencies", produces = "text/html")
	public String listAgencies(@PathVariable("functionNumber") int functionNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Functionn fn = Functionn.findFunctionn(functionNumber);
		if (fn!=null){
			ControllerUtils.populateRelationshipModel(fn.getAgencies(), "agencys", page, size, uiModel, Agency.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "agencies/list");
		return "agencies/list";
	}
	
	@RequestMapping(value="/{functionNumber}/activities", produces = "text/html")
	public String listActivities(@PathVariable("functionNumber") int functionNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Functionn fn = Functionn.findFunctionn(functionNumber);
		if (fn!=null){
			ControllerUtils.populateRelationshipModel(fn.getActivities(), "activitys", page, size, uiModel, Activity.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "activities/list");
		return "activities/list";
	}
	
	@RequestMapping(value="/{functionNumber}/persons", produces = "text/html")
	public String listPersons(@PathVariable("functionNumber") int functionNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Functionn fn = Functionn.findFunctionn(functionNumber);
		if (fn!=null){
			ControllerUtils.populateRelationshipModel(fn.getPersons(), "people", page, size, uiModel, Person.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "people/list");
		return "people/list";
	}
}
