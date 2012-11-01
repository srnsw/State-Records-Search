package au.gov.nsw.records.search.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.JstlView;

public class SimpleMediaTypeView extends AbstractView {

	private static Logger logger = Logger.getLogger(SimpleMediaTypeView.class); 
	private String extension;
	
	public void setMediaType(String mediaType){
		setContentType(mediaType);
		extension = mediaType.substring(mediaType.indexOf("/")+1);
		if (extension.contains("+")){
			extension = extension.substring(extension.indexOf("+")+1);
		}
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Object viewpath = model.get("view");
		if (viewpath!=null){
			String targetJsp = String.format("/WEB-INF/views/%s.%s.jspx", viewpath, extension);
			logger.info("Using:" + targetJsp);
			final JstlView view = new JstlView(targetJsp);
			view.setApplicationContext(getApplicationContext());
			view.render(model, request, response);
		}else{
			logger.warn("No viewpath object (view) configured");
		}
	}
	
}
