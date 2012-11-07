package au.gov.nsw.records.search.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.gov.nsw.records.search.model.Activity;
import au.gov.nsw.records.search.model.Agency;
import au.gov.nsw.records.search.model.Organisation;
import au.gov.nsw.records.search.service.ControllerUtils;

@RequestMapping("/organisations")
@Controller
@RooWebScaffold(path = "organisations", formBackingObject = Organisation.class, update=false, create=false, delete=false)
public class OrganisationController {
	
	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("organisations", Organisation.findOrganisationEntries(firstResult, sizeNo));
            float nrOfPages = (float) Organisation.countOrganisations() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
            uiModel.addAttribute("page", page);
            uiModel.addAttribute("size", size);
        } else {
            uiModel.addAttribute("organisations", Organisation.findAllOrganisations());
        }
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("view", "organisations/list");
        uiModel.addAttribute("count", Organisation.countOrganisations());
        return "organisations/list";
    }

	@RequestMapping(value = "/{organisationNumber}", produces = "text/html")
    public String show(@PathVariable("organisationNumber") int organisationNumber, Model uiModel,
    		@RequestParam(value = "preceding_page", required = false, defaultValue="1") Integer preceding_page,
    		@RequestParam(value = "succeeding_page", required = false, defaultValue="1") Integer succeeding_page,
    		@RequestParam(value = "agencies_page", required = false, defaultValue="1") Integer agencies_page) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("organisation", Organisation.findOrganisation(organisationNumber));
        uiModel.addAttribute("itemId", organisationNumber);
        
        if (Organisation.findOrganisation(organisationNumber)!=null){
	        int size = 5;
	        int arraySize =  Organisation.findOrganisation(organisationNumber).getPreceding().size();
	        uiModel.addAttribute("rel_preceding",   Organisation.findOrganisation(organisationNumber).getPreceding().subList(Math.max((preceding_page-1)*size, 0), Math.min(preceding_page*size, arraySize)));
	        uiModel.addAttribute("rel_preceding_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_preceding_page", preceding_page);
	        
	        arraySize =  Organisation.findOrganisation(organisationNumber).getSucceeding().size();
	        uiModel.addAttribute("rel_succeeding",  Organisation.findOrganisation(organisationNumber).getSucceeding().subList(Math.max((succeeding_page-1)*size, 0), Math.min(succeeding_page*size, arraySize)));
	        uiModel.addAttribute("rel_succeeding_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_succeeding_page", succeeding_page);
	        
	        arraySize =  Organisation.findOrganisation(organisationNumber).getAgencies().size();
	        uiModel.addAttribute("rel_agencies",  Organisation.findOrganisation(organisationNumber).getAgencies().subList(Math.max((agencies_page-1)*size, 0), Math.min(agencies_page*size, arraySize)));
	        uiModel.addAttribute("rel_agencies_size", Double.valueOf(Math.ceil(arraySize/(float)size)).intValue());
	        uiModel.addAttribute("rel_agencies_page", agencies_page);
        }
        uiModel.addAttribute("view", "organisations/show");
        return "organisations/show";
    }
	
	@RequestMapping(value="/{organisationNumber}/preceding", produces = "text/html")
	public String listPreceding(@PathVariable("organisationNumber") int organisationNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Organisation og = Organisation.findOrganisation(organisationNumber);
		if (og!=null){
			ControllerUtils.populateRelationshipModel(og.getPreceding(), "organisations", page, size, uiModel, Organisation.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "organisations/list");
		return "organisations/list";
	}
	
	@RequestMapping(value="/{organisationNumber}/succeeding", produces = "text/html")
	public String listSucceeding(@PathVariable("organisationNumber") int organisationNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Organisation og = Organisation.findOrganisation(organisationNumber);
		if (og!=null){
			ControllerUtils.populateRelationshipModel(og.getSucceeding(), "organisations", page, size, uiModel, Organisation.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "organisations/list");
		return "organisations/list";
	}
	
	@RequestMapping(value="/{organisationNumber}/agencies", produces = "text/html")
	public String listAgencies(@PathVariable("organisationNumber") int organisationNumber, @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @RequestParam(value = "size", required = false, defaultValue="30") Integer size, Model uiModel) {

		Organisation og = Organisation.findOrganisation(organisationNumber);
		if (og!=null){
			ControllerUtils.populateRelationshipModel(og.getAgencies(), "agencys", page, size, uiModel, Agency.class);
			addDateTimeFormatPatterns(uiModel);
		}
		uiModel.addAttribute("view", "agencies/list");
		return "agencies/list";
	}
}
