package au.gov.nsw.records.search.view;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

public class CustomViewResolver extends AbstractCachingViewResolver {

	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		// new custom view depending on the viewName
		return null;
	}
	
}
