package au.gov.nsw.records.search.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import au.gov.nsw.records.search.model.Agency;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/agencies")
@Controller
@RooWebScaffold(path = "agencies", formBackingObject = Agency.class)
public class AgencyController {
	
  @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
  public String update(@Valid Agency agency, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
      if (bindingResult.hasErrors()) {
          populateEditForm(uiModel, agency);
          return "agencys/update";
      }
      uiModel.asMap().clear();
      agency.merge();
      return "redirect:/agencys/" + encodeUrlPathSegment(String.valueOf(agency.getAgencyNumber()), httpServletRequest);
  }
  
  @RequestMapping(method = RequestMethod.POST, produces = "text/html")
  public String create(@Valid Agency agency, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
      if (bindingResult.hasErrors()) {
          populateEditForm(uiModel, agency);
          return "agencys/create";
      }
      uiModel.asMap().clear();
      agency.persist();
      return "redirect:/agencys/" + encodeUrlPathSegment(String.valueOf(agency.getAgencyNumber()), httpServletRequest);
  }
}
