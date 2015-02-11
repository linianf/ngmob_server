package com.ngmob.pay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang.StringEscapeUtils;


public class BeanMapUtil {
	
	@SuppressWarnings("rawtypes")
	public static String createPayForm(Object object, String formElementId, String actionUrl, String method) {
		BeanMap params = new BeanMap(object);
		StringBuilder formBuilder = new StringBuilder();
		formBuilder.append("<form id=\"" + formElementId + "\" action=\"" + actionUrl + "\" method=\"" + method + "\">\r\n");
		Iterator keyIterator = params.keyIterator();
		while (keyIterator.hasNext()) {
			String propertyName = (String) keyIterator.next();
			Object value = params.get(propertyName);
			if (value != null && value instanceof String) {
				formBuilder.append("<input type=\"hidden\" name=\"").append(propertyName).append("\" value=\"");
				formBuilder.append(StringEscapeUtils.escapeHtml((String) value)).append("\" />\r\n");
			}
		}
		formBuilder.append("</form>");
		return formBuilder.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public static String createPayFirstLetterCapitalizeForm(Object object, String formElementId, String actionUrl, String method) {
		BeanMap params = new BeanMap(object);
		StringBuilder formBuilder = new StringBuilder();
		formBuilder.append("<form id=\"" + formElementId + "\" action=\"" + actionUrl + "\" method=\"" + method + "\">\r\n");
		Iterator keyIterator = params.keyIterator();
		while (keyIterator.hasNext()) {
			String propertyName = (String) keyIterator.next();
			Object value = params.get(propertyName);
			
			byte[] paramItems = propertyName.getBytes();  
			paramItems[0] = (byte)((char)paramItems[0] - ( 'a' - 'A')); 
			propertyName = new String(paramItems);
			
			if (value != null && value instanceof String) {
				formBuilder.append("<input type=\"hidden\" name=\"").append(propertyName).append("\" value=\"");
				formBuilder.append(StringEscapeUtils.escapeHtml((String) value)).append("\" />\r\n");
			}
		}
		formBuilder.append("</form>");
		return formBuilder.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public static String generateSignContentOrderByParamsASCSort(Object object, String... ignoredParameter) {
		BeanMap params = new BeanMap(object);
		StringBuilder formBuilder = new StringBuilder();
		
		List<String> ignoredParameterList = new ArrayList<String>();
		ignoredParameterList.add("class");
		if (null != ignoredParameter && ignoredParameter.length != 0) {
			ignoredParameterList.addAll(Arrays.asList(ignoredParameter));
		}
		
		List<String> paramList = new ArrayList<String>();
		
		Iterator keyIterator = params.keyIterator();
		while (keyIterator.hasNext()) {
			paramList.add((String)keyIterator.next());
		}
		
		Collections.sort(paramList);
		
		for (String proName : paramList) {
			Object value = params.get(proName);
			if(null != value
					&& !"".equals(value)
					&& !ignoredParameterList.contains(proName)) {
				formBuilder.append(proName).append("=").append(value).append("&");
			}
		}
		return formBuilder.deleteCharAt(formBuilder.length() - 1).toString();
	}
	
}