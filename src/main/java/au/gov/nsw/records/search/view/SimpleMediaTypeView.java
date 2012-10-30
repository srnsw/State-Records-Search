package au.gov.nsw.records.search.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.JstlView;

public class SimpleMediaTypeView extends AbstractView {

	private String extension;
	public SimpleMediaTypeView(){
		setContentType("application/json");
	}
	
	public void setMediaType(String mediaType){
		setContentType(mediaType);
		extension = mediaType.substring(mediaType.indexOf("/")+1);
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Object viewpath = model.get("view");
		if (viewpath!=null){
			String targetJsp = String.format("/WEB-INF/views/%s.%s.jspx", viewpath, extension);
			System.out.println("Using:" + targetJsp);
			final JstlView view = new JstlView(targetJsp);
			view.setApplicationContext(getApplicationContext());
			view.render(model, request, response);
		}else{
			System.out.println("No view configured");
		}
	}
	
}
