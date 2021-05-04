package com.revature.group2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.group2.beans.User;
import com.revature.group2.services.UserService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/users")
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public Mono<User> registerUser(@RequestBody User user){
		return userService.addUser(user);
	}

}
