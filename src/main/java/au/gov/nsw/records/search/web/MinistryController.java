package au.gov.nsw.records.search.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import au.gov.nsw.records.search.model.Ministry;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/ministries")
@Controller
@RooWebScaffold(path = "ministries", formBackingObject = Ministry.class)
public class MinistryController {
	
  @RequestMapping(method = RequestMethod.POST, produces = "text/html")
  public String create(@Valid Ministry ministry, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
      if (bindingResult.hasErrors()) {
          populateEditForm(uiModel, ministry);
          return "ministrys/create";
      }
      uiModel.asMap().clear();
      ministry.persist();
      return "redirect:/ministrys/" + encodeUrlPathSegment(String.valueOf(ministry.getMinistryNumber()), httpServletRequest);
  }
  
  @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
  public String update(@Valid Ministry ministry, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
      if (bindingResult.hasErrors()) {
          populateEditForm(uiModel, ministry);
          return "ministrys/update";
      }
      uiModel.asMap().clear();
      ministry.merge();
      return "redirect:/ministrys/" + encodeUrlPathSegment(String.valueOf(ministry.getMinistryNumber()), httpServletRequest);
  }
}
