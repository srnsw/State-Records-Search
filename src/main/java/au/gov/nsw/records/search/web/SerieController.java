package au.gov.nsw.records.search.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import au.gov.nsw.records.search.model.Serie;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/series")
@Controller
@RooWebScaffold(path = "series", formBackingObject = Serie.class)
public class SerieController {
	
  @RequestMapping(method = RequestMethod.POST, produces = "text/html")
  public String create(@Valid Serie serie, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
      if (bindingResult.hasErrors()) {
          populateEditForm(uiModel, serie);
          return "series/create";
      }
      uiModel.asMap().clear();
      serie.persist();
      return "redirect:/series/" + encodeUrlPathSegment(String.valueOf(serie.getSeriesNumber()), httpServletRequest);
  }
  
  @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
  public String update(@Valid Serie serie, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
      if (bindingResult.hasErrors()) {
          populateEditForm(uiModel, serie);
          return "series/update";
      }
      uiModel.asMap().clear();
      serie.merge();
      return "redirect:/series/" + encodeUrlPathSegment(String.valueOf(serie.getSeriesNumber()), httpServletRequest);
  }
}
