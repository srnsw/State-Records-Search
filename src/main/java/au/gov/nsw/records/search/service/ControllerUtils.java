package au.gov.nsw.records.search.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;

public class ControllerUtils {

	private static <T,O> List<T> getRelationList(List<O> original, Class<T> type){
		List<T> result = new ArrayList<T>();
		for (O item:original){
			try {
				result.add(type.cast(item.getClass().getMethod("getRelationship").invoke(item)));
				//item.getClass().getMethod("").invoke(obj, args)
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static void populateRelationshipModel(List<?> objects, String attributeName, Integer page, Integer size, Model uiModel, Class<?> clazz){
		int resultSize = objects.size();
		uiModel.addAttribute(attributeName, ControllerUtils.getRelationList(objects.subList(Math.max((page-1)*size, 0), Math.min(page*size, resultSize)), clazz));
		float nrOfPages = (float) resultSize / size;
		uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		uiModel.addAttribute("page", page);
		uiModel.addAttribute("size", size);
	}
	
	public static void populateRelationshipModel(List<?> objects, String attributeName, Integer page, Integer size, Model uiModel, int resultSize, Class<?> clazz){
		uiModel.addAttribute(attributeName, ControllerUtils.getRelationList(objects, clazz));
		float nrOfPages = (float) resultSize / size;
		uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		uiModel.addAttribute("page", page);
		uiModel.addAttribute("size", size);
	}
}
