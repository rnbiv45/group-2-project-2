package com.revature.group2.controllers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.group2.services.UserService;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

	@TestConfiguration
	static class Configuration {
		@Bean
		public UserController getUserController(UserService userService) {
			UserController userController = new UserController();
			userController.setUserService(userService);
			return userController;
			
		}
		@Bean
		public UserService getUserService(UserService userService) {
			return Mockito.mock(UserService.class);
			
		}
	}
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private UserService userService;
}
