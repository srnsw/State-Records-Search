package au.gov.nsw.records.search.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import au.gov.nsw.records.search.model.Organisation;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/organisations")
@Controller
@RooWebScaffold(path = "organisations", formBackingObject = Organisation.class)
public class OrganisationController {
	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
  public String create(@Valid Organisation organisation, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
      if (bindingResult.hasErrors()) {
          populateEditForm(uiModel, organisation);
          return "organisations/create";
      }
      uiModel.asMap().clear();
      organisation.persist();
      return "redirect:/organisations/" + encodeUrlPathSegment(String.valueOf(organisation.getOrganisationNumber()), httpServletRequest);
  }
	
  @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
  public String update(@Valid Organisation organisation, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
      if (bindingResult.hasErrors()) {
          populateEditForm(uiModel, organisation);
          return "organisations/update";
      }
      uiModel.asMap().clear();
      organisation.merge();
      return "redirect:/organisations/" + encodeUrlPathSegment(String.valueOf(organisation.getOrganisationNumber()), httpServletRequest);
  }
}
