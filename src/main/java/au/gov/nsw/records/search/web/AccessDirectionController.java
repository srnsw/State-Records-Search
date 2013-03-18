package au.gov.nsw.records.search.web;

import au.gov.nsw.records.search.model.AccessDirection;

import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        
        addDateTimeFormatPatterns(uiModel);
        return "accessdirections/list";
    }
}
