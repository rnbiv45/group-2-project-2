package com.revature.group2.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.group2.beans.User;
import com.revature.group2.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserController {
	
	@PostMapping(value="{userId}/cards")
	public Publisher<User> collectCard() {
		return null;
	}
	
	@PostMapping("users")
	public ResponseEntity<User> registerUser(@RequestBody User user){
		userService.addUser(user);
		return ResponseEntity.status(201).body(user);
	}
  
}
