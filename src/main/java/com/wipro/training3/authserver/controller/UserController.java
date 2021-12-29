package com.wipro.training3.authserver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.training3.authserver.entity.User;
import com.wipro.training3.authserver.service.ApplicationUserService;

@RestController
public class UserController {

	private ApplicationUserService appUserSer;
	
	public UserController(ApplicationUserService appUserSer) {
		this.appUserSer = appUserSer;
	}
	
	@PostMapping("/addUser")
	public void addUser(@RequestBody User user) {
		appUserSer.save(user);
	}
	
	
	
}
