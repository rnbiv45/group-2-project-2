package com.revature.group2.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.group2.repos.UserRepo;
import com.revature.group2.services.UserService;
import com.revature.group2.services.UserServiceImp;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
	@TestConfiguration
	static class Configuration {
		@Bean
		public UserService getUserService(UserRepo userRepo) {
			UserService userService = new UserServiceImp();
			userService.setUserRepo(userRepo);
			return userService;
		}

		@Bean
		public UserRepo getService() {
			return Mockito.mock(UserRepo.class);
		}
	}
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepo userRepo;
}
