package com.rev.init;

import java.util.EnumSet;

import javax.servlet.ServletContext;

import org.springframework.boot.web.servlet.server.Session.SessionTrackingMode;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SpringSecurityInit extends AbstractSecurityWebApplicationInitializer{

	
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		//servletContext.getSessionCookieConfig().setMaxAge(1800); //em segundos
		
		servletContext.setSessionTrackingModes(EnumSet.of(javax.servlet.SessionTrackingMode.COOKIE));
	}
}
