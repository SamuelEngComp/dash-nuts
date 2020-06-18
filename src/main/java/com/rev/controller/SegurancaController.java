package com.rev.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SegurancaController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@AuthenticationPrincipal User user) {
		ModelAndView mv = new ModelAndView("login");

		if (user != null) {
			return new ModelAndView("redirect:/home");
		}

		return mv;
	}

	@RequestMapping("/403")
	public ModelAndView acessoNegado() {
		ModelAndView mv = new ModelAndView("403");
		return mv;
	}

}
