package com.revature.group2.controllers;

import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.group2.beans.User;

@RestController
@RequestMapping(value="/users")
public class UserController {
	
	@PostMapping(value="{userId}/cards")
	public Publisher<User> collectCard() {
		return null;
	}
	
}
