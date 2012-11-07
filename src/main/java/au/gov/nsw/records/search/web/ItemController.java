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
import au.gov.nsw.records.search.service.ControllerUtils;

@RequestMapping("/items")
@Controller
@RooWebScaffold(path = "items", formBackingObject = Item.class, update=false, create=false, delete=false)
public class ItemController {
  

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("items", Item.findItemEntries(firstResult, sizeNo));
            float nrOfPages = (float) Item.countItems() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
            uiModel.addAttribute("page", page);
            uiModel.addAttribute("size", size);
        } else {
            uiModel.addAttribute("items", Item.findAllItems());
        }
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("count", Item.countItems());
        uiModel.addAttribute("view", "items/list");
        return "items/list";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") int id, Model uiModel,
    		@RequestParam(value = "persons_page", required = false, defaultValue="1") Integer persons_page,
    		@RequestParam(value = "agencies_page", required = false, defaultValue="1") Integer agencies_page) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("item", Item.findItem(id));
        uiModel.addAttribute("itemId", id);
        
        if (Item.findItem(id)!=null){
	        int size = 5;
	        int arraySize =  Item.findItem(id).getSeriesNumber().getPersons().size();
	        uiModel.addAttribute("rel_persons",  Item.findItem(id).getSeriesNumber().getPersons().subList(Math.max((persons_page-1)*size, 0), Math.min(persons_page*size, arraySize)));
	        uiModel.addAttribute("rel_persons_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_persons_page", persons_page);
	        
	        arraySize =  Item.findItem(id).getSeriesNumber().getCreatingAgencies().size();
	        uiModel.addAttribute("rel_agencies",  Item.findItem(id).getSeriesNumber().getCreatingAgencies().subList(Math.max((agencies_page-1)*size, 0), Math.min(agencies_page*size, arraySize)));
	        uiModel.addAttribute("rel_agencies_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_agencies_page", agencies_page);
        }
        uiModel.addAttribute("unapi", "true");
        uiModel.addAttribute("view", "items/show");
        return "items/show";
    }
	
	@RequestMapping(value="/{id}/persons", produces = "text/html")
	public String listPersons(@PathVariable("id") int id, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Item it = Item.findItem(id);
		if (it!=null){
			ControllerUtils.populateRelationshipModel(it.getSeriesNumber().getPersons(), "people", page, size, uiModel, Person.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "people/list");
		return "people/list";
	}
	
	@RequestMapping(value="/{id}/agencies", produces = "text/html")
	public String listAgencies(@PathVariable("id") int id, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Item it = Item.findItem(id);
		if (it!=null){
			ControllerUtils.populateRelationshipModel(it.getSeriesNumber().getCreatingAgencies(), "agencys", page, size, uiModel, Agency.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "agencies/list");
		return "agencies/list";
	}
}
