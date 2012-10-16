package au.gov.nsw.records.search.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import au.gov.nsw.records.search.model.Item;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/items")
@Controller
@RooWebScaffold(path = "items", formBackingObject = Item.class)
public class ItemController {
	
  @RequestMapping(method = RequestMethod.POST, produces = "text/html")
  public String create(@Valid Item item, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
      if (bindingResult.hasErrors()) {
          populateEditForm(uiModel, item);
          return "items/create";
      }
      uiModel.asMap().clear();
      item.persist();
      return "redirect:/items/" + encodeUrlPathSegment(String.valueOf(item.getId()), httpServletRequest);
  }
  
  @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
  public String update(@Valid Item item, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
      if (bindingResult.hasErrors()) {
          populateEditForm(uiModel, item);
          return "items/update";
      }
      uiModel.asMap().clear();
      item.merge();
      return "redirect:/items/" + encodeUrlPathSegment(String.valueOf(item.getId()), httpServletRequest);
  }
  
}
