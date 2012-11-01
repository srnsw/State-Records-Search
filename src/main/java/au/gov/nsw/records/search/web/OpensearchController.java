package au.gov.nsw.records.search.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/opensearch/**")
@Controller
public class OpensearchController {

    @RequestMapping(produces="application/xml")
    public String index(Model uiModel, HttpServletRequest httpServletRequest) {
    	String host = httpServletRequest.getRequestURL().toString().replace("opensearch", "");
  		uiModel.addAttribute("view", "opensearch/index");
  		uiModel.addAttribute("searchUrl", String.format("%ssearch?q={searchTerms}&amp;page={startPage?}&amp;size={count?}", host));
  		uiModel.addAttribute("templateUrl", String.format("%ssearch?q={searchTerms}&amp;page={startPage?}&amp;size={count?}&amp;format=xml", host));
      return "opensearch/index";
    }
}
