package com.freshvotes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
	
	@GetMapping({"/","","/index"})
	public String indexView() {
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String dashboardView() {
		return "dashboard";
	}

}
