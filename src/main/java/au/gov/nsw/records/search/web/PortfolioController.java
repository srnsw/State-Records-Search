package au.gov.nsw.records.search.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import au.gov.nsw.records.search.model.Portfolio;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/portfolios")
@Controller
@RooWebScaffold(path = "portfolios", formBackingObject = Portfolio.class)
public class PortfolioController {
	
	 @RequestMapping(method = RequestMethod.POST, produces = "text/html")
   public String create(@Valid Portfolio portfolio, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
       if (bindingResult.hasErrors()) {
           populateEditForm(uiModel, portfolio);
           return "portfolios/create";
       }
       uiModel.asMap().clear();
       portfolio.persist();
       return "redirect:/portfolios/" + encodeUrlPathSegment(String.valueOf(portfolio.getPortfolioNumber()), httpServletRequest);
   }
   
	 @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
   public String update(@Valid Portfolio portfolio, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
       if (bindingResult.hasErrors()) {
           populateEditForm(uiModel, portfolio);
           return "portfolios/update";
       }
       uiModel.asMap().clear();
       portfolio.merge();
       return "redirect:/portfolios/" + encodeUrlPathSegment(String.valueOf(portfolio.getPortfolioNumber()), httpServletRequest);
   }
}
