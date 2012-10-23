package au.gov.nsw.records.search.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.gov.nsw.records.search.model.Portfolio;
import au.gov.nsw.records.search.model.Serie;

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
        }
        return "series/show";
    }
}
