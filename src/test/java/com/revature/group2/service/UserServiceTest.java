package com.revature.group2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.group2.beans.User;
import com.revature.group2.repos.UserRepo;
import com.revature.group2.services.UserService;
import com.revature.group2.services.UserServiceImp;

import reactor.core.publisher.Flux;

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
	
	@Test
	public void testGetUsers () {
		User[] users = {new User(), new User(), new User()};
		Flux<User> userFlux = Flux.fromArray(users);
		when(userRepo.findAll()).thenReturn(userFlux);
		Flux<User> result = userService.getUsers();
		//assertEquals("userService should return that is passed to it by the userRepo", userFlux, result);
		assertThat(result).isEqualTo(userFlux);
		
	}
}
