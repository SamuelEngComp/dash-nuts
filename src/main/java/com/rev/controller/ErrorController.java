package com.rev.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController{

	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request){
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if(status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			
			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				
				ModelAndView mv = new ModelAndView("404");
				
				return mv;
			}
			else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				ModelAndView mv = new ModelAndView("500");
				
				return mv;
			}
			
			else if(statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
				ModelAndView mv = new ModelAndView("404");
				return mv;
			}
			
		}
		return null;
	}
	
	
	@Override
	public String getErrorPath() {
		return "/error";
	}

}
