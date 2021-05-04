package com.revature.group2.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.group2.beans.User;
import com.revature.group2.services.UserService;

@RestController
public class UserController {
	private UserService userService;
	
	@PostMapping("users")
	public ResponseEntity<User> registerUser(@RequestBody User user){
		userService.addUser(user);
		return ResponseEntity.status(201).body(user);
	}
}
