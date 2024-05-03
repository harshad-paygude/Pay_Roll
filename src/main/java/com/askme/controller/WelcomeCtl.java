package com.askme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/Welcome")
public class WelcomeCtl {

	@RequestMapping(method = RequestMethod.GET)
	public String display() {
		return "Welcome";
	}

}
