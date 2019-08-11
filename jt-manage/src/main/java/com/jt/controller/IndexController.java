package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	//RESTFul风格
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		
		return moduleName;
	}
	
}
