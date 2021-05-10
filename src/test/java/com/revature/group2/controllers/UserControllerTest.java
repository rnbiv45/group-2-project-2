package com.revature.group2.controllers;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.group2.beans.User;
import com.revature.group2.services.UserService;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

	@TestConfiguration
	static class Config{
		
		@Bean
		public UserController getUserService(UserService userService) {
			UserController userController = new UserController();
			userController.setUserService(userService);
			return userController;
		}
		
		@Bean
		public UserService getService() {
			return Mockito.mock(UserService.class);
		}

	}
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testRegisterUser() {
		User user = new User();
		user.setName("test");
		user.setPass("pass");
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		userController.registerUser(user);
		verify(userService).addUser(captor.capture());
	}
}
