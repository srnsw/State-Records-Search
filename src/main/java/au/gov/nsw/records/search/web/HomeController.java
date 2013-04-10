package au.gov.nsw.records.search.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class HomeController {

	@RequestMapping(value="/", produces = "text/html")
	public String defaultPage(Model uiModel) {

		return "redirect:/search";
	}
}
