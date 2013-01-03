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
import au.gov.nsw.records.search.model.Person;
import au.gov.nsw.records.search.model.Serie;
import au.gov.nsw.records.search.service.ControllerUtils;

@RequestMapping("/series")
@Controller
@RooWebScaffold(path = "series", formBackingObject = Serie.class, update=false, create=false, delete=false)
public class SerieController {
	
	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("series", Serie.findSerieEntries(firstResult, sizeNo));
            float nrOfPages = (float) Serie.countSeries() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
            uiModel.addAttribute("page", page);
            uiModel.addAttribute("size", size);
        } else {
            uiModel.addAttribute("series", Serie.findAllSeries());
        }
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("view", "series/list");
        uiModel.addAttribute("count", Serie.countSeries());
        return "series/list";
    }

	@RequestMapping(value = "/{seriesNumber}", produces = "text/html")
    public String show(@PathVariable("seriesNumber") int seriesNumber, Model uiModel,
    		@RequestParam(value = "creating_agencies_page", required = false, defaultValue="1") Integer creating_agencies_page,
    		@RequestParam(value = "persons_page", required = false, defaultValue="1") Integer persons_page,
    		@RequestParam(value = "controlling_agencies_page", required = false, defaultValue="1") Integer controlling_agencies_page,
    		@RequestParam(value = "activities_page", required = false, defaultValue="1") Integer activities_page,
    		@RequestParam(value = "preceding_series_page", required = false, defaultValue="1") Integer preceding_series_page,
    		@RequestParam(value = "succeeding_series_page", required = false, defaultValue="1") Integer succeeding_series_page,
    		@RequestParam(value = "related_series_page", required = false, defaultValue="1") Integer related_series_page,
    		@RequestParam(value = "controlling_series_page", required = false, defaultValue="1") Integer controlling_series_page,
    		@RequestParam(value = "controlled_series_page", required = false, defaultValue="1") Integer controlled_series_page,
    		@RequestParam(value = "items_page", required = false, defaultValue="1") Integer items_page) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("serie", Serie.findSerie(seriesNumber));
        uiModel.addAttribute("itemId", seriesNumber);
        
        if (Serie.findSerie(seriesNumber)!=null){
	        int size = 5;
	        int arraySize =  Serie.findSerie(seriesNumber).getCreatingAgencies().size();
	        uiModel.addAttribute("rel_creating_agencies",  Serie.findSerie(seriesNumber).getCreatingAgencies().subList(Math.max((creating_agencies_page-1)*size, 0), Math.min(creating_agencies_page*size, arraySize)));
	        uiModel.addAttribute("rel_creating_agencies_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_creating_agencies_page", creating_agencies_page);
	        
	        arraySize =  Serie.findSerie(seriesNumber).getPersons().size();
	        uiModel.addAttribute("rel_persons",  Serie.findSerie(seriesNumber).getPersons().subList(Math.max((persons_page-1)*size, 0), Math.min(persons_page*size, arraySize)));
	        uiModel.addAttribute("rel_persons_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_persons_page", persons_page);
	        
	        arraySize =  Serie.findSerie(seriesNumber).getControllingAgencies().size();
	        uiModel.addAttribute("rel_controlling_agencies",  Serie.findSerie(seriesNumber).getControllingAgencies().subList(Math.max((controlling_agencies_page-1)*size, 0), Math.min(controlling_agencies_page*size, arraySize)));
	        uiModel.addAttribute("rel_controlling_agencies_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_controlling_agencies_page", controlling_agencies_page);
	        
	        arraySize =  Serie.findSerie(seriesNumber).getActivities().size();
	        uiModel.addAttribute("rel_activities",  Serie.findSerie(seriesNumber).getActivities().subList(Math.max((activities_page-1)*size, 0), Math.min(activities_page*size, arraySize)));
	        uiModel.addAttribute("rel_activities_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_activities_page", activities_page);
	        
	        arraySize =  Serie.findSerie(seriesNumber).getPrecedingSeries().size();
	        uiModel.addAttribute("rel_preceding_series",  Serie.findSerie(seriesNumber).getPrecedingSeries().subList(Math.max((preceding_series_page-1)*size, 0), Math.min(preceding_series_page*size, arraySize)));
	        uiModel.addAttribute("rel_preceding_series_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_preceding_series_page", preceding_series_page);
	        
	        arraySize =  Serie.findSerie(seriesNumber).getSucceedingSeries().size();
	        uiModel.addAttribute("rel_succeeding_series",  Serie.findSerie(seriesNumber).getSucceedingSeries().subList(Math.max((succeeding_series_page-1)*size, 0), Math.min(succeeding_series_page*size, arraySize)));
	        uiModel.addAttribute("rel_succeeding_series_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_succeeding_series_page", succeeding_series_page);
	        
	        arraySize =  Serie.findSerie(seriesNumber).getRelatedSeries().size();
	        uiModel.addAttribute("rel_related_series",  Serie.findSerie(seriesNumber).getRelatedSeries().subList(Math.max((related_series_page-1)*size, 0), Math.min(related_series_page*size, arraySize)));
	        uiModel.addAttribute("rel_related_series_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_related_series_page", related_series_page);
	        
	        arraySize =  Serie.findSerie(seriesNumber).getControllingSeries().size();
	        uiModel.addAttribute("rel_controlling_series",  Serie.findSerie(seriesNumber).getControllingSeries().subList(Math.max((controlling_series_page-1)*size, 0), Math.min(controlling_series_page*size, arraySize)));
	        uiModel.addAttribute("rel_controlling_series_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_controlling_series_page", controlling_series_page);
	        
	        arraySize =  Serie.findSerie(seriesNumber).getControlledSeries().size();
	        uiModel.addAttribute("rel_controlled_series",  Serie.findSerie(seriesNumber).getControlledSeries().subList(Math.max((controlled_series_page-1)*size, 0), Math.min(controlled_series_page*size, arraySize)));
	        uiModel.addAttribute("rel_controlled_series_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_controlled_series_page", controlled_series_page);
	        
	        arraySize =  Serie.findSerie(seriesNumber).countItems().intValue();
	        uiModel.addAttribute("rel_items",  Serie.findSerie(seriesNumber).getItems(items_page,size));
	        uiModel.addAttribute("rel_items_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_items_page", items_page);
        }
        uiModel.addAttribute("unapi", "true");
        uiModel.addAttribute("view", "series/show");
        return "series/show";
    }
	
	@RequestMapping(value="/{seriesNumber}/items", produces = "text/html")
	public String listItems(@PathVariable("seriesNumber") int seriesNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Serie se = Serie.findSerie(seriesNumber);
		if (se!=null){
			ControllerUtils.populateRelationshipModel(se.getItems(page, size), "items", page, size, uiModel, se.countItems().intValue(), Item.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "items/list");
		return "items/list";
	}
	
	@RequestMapping(value="/{seriesNumber}/agencies_creating", produces = "text/html")
	public String listCreatingAgencies(@PathVariable("seriesNumber") int seriesNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Serie se = Serie.findSerie(seriesNumber);
		if (se!=null){
			ControllerUtils.populateRelationshipModel(se.getCreatingAgencies(), "agencys", page, size, uiModel, Agency.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "agencies/list");
		return "agencies/list";
	}
	
	@RequestMapping(value="/{seriesNumber}/persons", produces = "text/html")
	public String listPersons(@PathVariable("seriesNumber") int seriesNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Serie se = Serie.findSerie(seriesNumber);
		if (se!=null){
			ControllerUtils.populateRelationshipModel(se.getPersons(), "people", page, size, uiModel, Person.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "people/list");
		return "people/list";
	}
	
	@RequestMapping(value="/{seriesNumber}/agencies_controlling", produces = "text/html")
	public String listControllingAgencies(@PathVariable("seriesNumber") int seriesNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Serie se = Serie.findSerie(seriesNumber);
		if (se!=null){
			ControllerUtils.populateRelationshipModel(se.getControllingAgencies(), "agencys", page, size, uiModel, Agency.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "agencies/list");
		return "agencies/list";
	}

	@RequestMapping(value="/{seriesNumber}/activities", produces = "text/html")
	public String listActivities(@PathVariable("seriesNumber") int seriesNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Serie se = Serie.findSerie(seriesNumber);
		if (se!=null){
			ControllerUtils.populateRelationshipModel(se.getActivities(), "activitys", page, size, uiModel, Activity.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "activities/list");
		return "activities/list";
	}
	
	@RequestMapping(value="/{seriesNumber}/preceding", produces = "text/html")
	public String listPrecedingSeries(@PathVariable("seriesNumber") int seriesNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Serie se = Serie.findSerie(seriesNumber);
		if (se!=null){
			ControllerUtils.populateRelationshipModel(se.getPrecedingSeries(), "series", page, size, uiModel, Serie.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "series/list");
		return "series/list";
	}
	
	//succeeding
	@RequestMapping(value="/{seriesNumber}/succeeding", produces = "text/html")
	public String listSucceedingSeries(@PathVariable("seriesNumber") int seriesNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Serie se = Serie.findSerie(seriesNumber);
		if (se!=null){
			ControllerUtils.populateRelationshipModel(se.getSucceedingSeries(), "series", page, size, uiModel, Serie.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "series/list");
		return "series/list";
	}
	//related
	@RequestMapping(value="/{seriesNumber}/related", produces = "text/html")
	public String listRelatedSeries(@PathVariable("seriesNumber") int seriesNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Serie se = Serie.findSerie(seriesNumber);
		if (se!=null){
			ControllerUtils.populateRelationshipModel(se.getRelatedSeries(), "series", page, size, uiModel, Serie.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "series/list");
		return "series/list";
	}	
	//controlling
	@RequestMapping(value="/{seriesNumber}/controlling", produces = "text/html")
	public String listControllingSeries(@PathVariable("seriesNumber") int seriesNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Serie se = Serie.findSerie(seriesNumber);
		if (se!=null){
			ControllerUtils.populateRelationshipModel(se.getControllingSeries(), "series", page, size, uiModel, Serie.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "series/list");
		return "series/list";
	}
	//controlled
	@RequestMapping(value="/{seriesNumber}/controlled", produces = "text/html")
	public String listControlledSeries(@PathVariable("seriesNumber") int seriesNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Serie se = Serie.findSerie(seriesNumber);
		if (se!=null){
			ControllerUtils.populateRelationshipModel(se.getControlledSeries(), "series", page, size, uiModel, Serie.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "series/list");
		return "series/list";
	}
}
