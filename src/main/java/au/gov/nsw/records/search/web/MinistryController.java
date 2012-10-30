package au.gov.nsw.records.search.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.gov.nsw.records.search.model.Ministry;
import au.gov.nsw.records.search.model.Portfolio;
import au.gov.nsw.records.search.service.ControllerUtils;

@RequestMapping("/ministries")
@Controller
@RooWebScaffold(path = "ministries", formBackingObject = Ministry.class, update=false, create=false, delete=false)
public class MinistryController {


	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("ministrys", Ministry.findMinistryEntries(firstResult, sizeNo));
            float nrOfPages = (float) Ministry.countMinistrys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
            uiModel.addAttribute("page", page);
            uiModel.addAttribute("size", size);
        } else {
            uiModel.addAttribute("ministrys", Ministry.findAllMinistrys());
        }
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("view", "ministries/list");
        return "ministries/list";
    }

	@RequestMapping(value = "/{ministryNumber}", produces = "text/html")
    public String show(@PathVariable("ministryNumber") int ministryNumber, Model uiModel,
    		@RequestParam(value = "portfolios_page", required = false, defaultValue="1") Integer portfolios_page) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("ministry", Ministry.findMinistry(ministryNumber));
        uiModel.addAttribute("itemId", ministryNumber);
        
        int size = 5;
        int arraySize =  Ministry.findMinistry(ministryNumber).getPortfolios().size();
        uiModel.addAttribute("rel_portfolios",  Ministry.findMinistry(ministryNumber).getPortfolios().subList(Math.max((portfolios_page-1)*size, 0), Math.min(portfolios_page*size, arraySize)));
        uiModel.addAttribute("rel_portfolios_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
        uiModel.addAttribute("rel_portfolios_page", portfolios_page);
        uiModel.addAttribute("view", "ministries/show");
        return "ministries/show";
    }
	
	@RequestMapping(value="/{ministryNumber}/portfolios", produces = "text/html")
	public String listPortfolios(@PathVariable("ministryNumber") int ministryNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Ministry min = Ministry.findMinistry(ministryNumber);
		if (min!=null){
			ControllerUtils.populateRelationshipModel(min.getPortfolios(), "portfolios", page, size, uiModel, Portfolio.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "portfolios/list");
		return "portfolios/list";
	}
}
