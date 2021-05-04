package com.revature.group2.project2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.revature.group2.project2.services.UserService;


@RestController
public class UserController {
	private UserService userservice;
	
	@Autowired
	public void setPlayerService(UserService userservice) {
		this.userservice = userservice;
	}
	
}
