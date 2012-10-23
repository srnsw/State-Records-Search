package au.gov.nsw.records.search.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.gov.nsw.records.search.model.Activity;
import au.gov.nsw.records.search.model.Serie;

@RequestMapping("/activities")
@Controller
@RooWebScaffold(path = "activities", formBackingObject = Activity.class, update=false, create=false, delete=false)
public class ActivityController {

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("activitys", Activity.findActivityEntries(firstResult, sizeNo));
            float nrOfPages = (float) Activity.countActivitys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
            uiModel.addAttribute("page", page);
            uiModel.addAttribute("size", size);
        } else {
            uiModel.addAttribute("activitys", Activity.findAllActivitys());
        }
        addDateTimeFormatPatterns(uiModel);
        return "activities/list";
    }

	@RequestMapping(value = "/{activityNumber}", produces = "text/html")
    public String show(@RequestParam(value = "series_page", required = false, defaultValue="1") Integer series_page, @RequestParam(value = "functions_page", required = false, defaultValue="1") Integer functions_page, @PathVariable("activityNumber") int activityNumber, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("activity", Activity.findActivity(activityNumber));
        uiModel.addAttribute("itemId", activityNumber);
        
        if (Activity.findActivity(activityNumber)!=null){
	        int size = 5;
	        int arraySize = Activity.findActivity(activityNumber).getSeries().size();
	        uiModel.addAttribute("rel_series", Activity.findActivity(activityNumber).getSeries().subList(Math.max((series_page-1)*size, 0), Math.min(series_page*size, arraySize)));
	        uiModel.addAttribute("rel_series_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_series_page", series_page);
	        
	        arraySize = Activity.findActivity(activityNumber).getFunctions().size();
	        uiModel.addAttribute("rel_functions", Activity.findActivity(activityNumber).getFunctions().subList(Math.max((functions_page-1)*size, 0), Math.min(functions_page*size, arraySize)));
	        uiModel.addAttribute("rel_functions_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_functions_page", functions_page);
        }
        return "activities/show";
    }
	
	@RequestMapping(value="/{activityNumber}/series", produces = "text/html")
	public String listSeries(@PathVariable("activityNumber") int activityNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Activity ac = Activity.findActivity(activityNumber);
		if (ac!=null){
			int resultSize = ac.getSeries().size();
			uiModel.addAttribute("series", ac.getSeries().subList(Math.max((page-1)*size, 0), Math.min(page*size, resultSize)));
			float nrOfPages = (float) resultSize / size;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
			uiModel.addAttribute("page", page);
			uiModel.addAttribute("size", size);

			addDateTimeFormatPatterns(uiModel);
			
		}
		return "series/list";
	}
}
