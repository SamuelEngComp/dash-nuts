package com.rev.config.init;

import javax.servlet.Filter;

import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.rev.config.WebConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
	protected Filter[] getServletFilters() {
		
		@SuppressWarnings("deprecation") 
		HttpPutFormContentFilter putContentFilter = new HttpPutFormContentFilter();
		 
		
		FormContentFilter contentFilter = new FormContentFilter();
        return new Filter[] { contentFilter, putContentFilter};
	}

}
