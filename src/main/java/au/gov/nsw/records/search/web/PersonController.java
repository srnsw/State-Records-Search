package au.gov.nsw.records.search.web;

import au.gov.nsw.records.search.model.Person;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/persons")
@Controller
@RooWebScaffold(path = "people", formBackingObject = Person.class)
public class PersonController {


	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Person person, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, person);
            return "people/update";
        }
        uiModel.asMap().clear();
        person.merge();
        return "redirect:/people/" + encodeUrlPathSegment(String.valueOf(person.getPersonNumber()), httpServletRequest);
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Person person, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, person);
            return "people/create";
        }
        uiModel.asMap().clear();
        person.persist();
        return "redirect:/people/" + encodeUrlPathSegment(String.valueOf(person.getPersonNumber()), httpServletRequest);
    }
}
