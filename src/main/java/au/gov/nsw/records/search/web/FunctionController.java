package au.gov.nsw.records.search.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import au.gov.nsw.records.search.model.Functionn;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/functions")
@Controller
@RooWebScaffold(path = "functions", formBackingObject = Functionn.class)
public class FunctionController {
	
  @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
  public String update(@Valid Functionn functionn, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
      if (bindingResult.hasErrors()) {
          populateEditForm(uiModel, functionn);
          return "functionns/update";
      }
      uiModel.asMap().clear();
      functionn.merge();
      return "redirect:/functionns/" + encodeUrlPathSegment(String.valueOf(functionn.getFunctionNumber()), httpServletRequest);
  }
  
  @RequestMapping(method = RequestMethod.POST, produces = "text/html")
  public String create(@Valid Functionn functionn, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
      if (bindingResult.hasErrors()) {
          populateEditForm(uiModel, functionn);
          return "functionns/create";
      }
      uiModel.asMap().clear();
      functionn.persist();
      return "redirect:/functionns/" + encodeUrlPathSegment(String.valueOf(functionn.getFunctionNumber()), httpServletRequest);
  }
}
