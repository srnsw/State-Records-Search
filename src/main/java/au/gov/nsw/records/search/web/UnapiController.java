package au.gov.nsw.records.search.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/unapi/**")
@Controller
public class UnapiController {

    @RequestMapping(produces="application/xml")
    public String index( @RequestParam(value = "id", required = false) String id, @RequestParam(value = "format", required = false) String format, Model uiModel, HttpServletRequest httpServletRequest) {
    	String host = httpServletRequest.getRequestURL().toString().replace("unapi", "");
    	if (id!=null){
  	  	if (format!=null){
  	  		//redirect_to
  	  		return String.format("redirect:/%s.%s", id.replace(host, "") ,format);
  	  	}
  	  	uiModel.addAttribute("id", String.format("%s",id));
  	  }
    	uiModel.addAttribute("view", "unapi/index");
    	return "unapi/index";
    }
}
