package au.gov.nsw.records.search.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.gov.nsw.records.search.model.AccessDirection;

@RequestMapping("/accessdirections")
@Controller
@RooWebScaffold(path = "accessdirections", formBackingObject = AccessDirection.class)
public class AccessDirectionController {

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {
        
		    int sizeNo = size == null ? 10 : size.intValue();
        final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
        uiModel.addAttribute("accessdirections", AccessDirection.findAccessDirectionEntries(firstResult, sizeNo));
        float nrOfPages = (float) AccessDirection.countAccessDirections() / sizeNo;
        uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        uiModel.addAttribute("page", page);
        uiModel.addAttribute("size", size);
        
        uiModel.addAttribute("count", AccessDirection.countAccessDirections());
        uiModel.addAttribute("view", "accessdirections/list");
        
        addDateTimeFormatPatterns(uiModel);
        return "accessdirections/list";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("accessdirection", AccessDirection.findAccessDirection(id));
        uiModel.addAttribute("itemId", id);
        uiModel.addAttribute("view", "accessdirections/show"); 
        return "accessdirections/show";
    }
}
