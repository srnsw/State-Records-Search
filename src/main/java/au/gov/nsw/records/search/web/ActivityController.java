package au.gov.nsw.records.search.web;

import au.gov.nsw.records.search.model.Activity;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/activities")
@Controller
@RooWebScaffold(path = "activities", formBackingObject = Activity.class)
public class ActivityController {

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Activity activity, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, activity);
            return "activitys/update";
        }
        uiModel.asMap().clear();
        activity.merge();
        return "redirect:/activities/" + encodeUrlPathSegment(String.valueOf(activity.getActivityNumber()), httpServletRequest);
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Activity activity, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, activity);
            return "activitys/create";
        }
        uiModel.asMap().clear();
        activity.persist();
        return "redirect:/activities/" + encodeUrlPathSegment(String.valueOf(activity.getActivityNumber()), httpServletRequest);
    }
}
