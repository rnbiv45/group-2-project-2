package com.revature.group2.service;

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
import reactor.core.publisher.Mono;

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
	void testGetUsers () {
		User[] users = {new User(), new User(), new User()};
		Flux<User> userFlux = Flux.fromArray(users);
		when(userRepo.findAll()).thenReturn(userFlux);
		Flux<User> result = userService.getUsers();
		//assertEquals("userService should return that is passed to it by the userRepo", userFlux, result);
		assertThat(result).isEqualTo(userFlux);
		
	}
	
	@Test
	void testGetUser() {
		User user = new User();
		Mono<User> userMono = Mono.just(user);
		when(userRepo.findById("thomas15399")).thenReturn(userMono);
		Mono<User> result = userService.getUser("thomas15399");
		assertThat(result).isEqualTo(userMono);
	}
	@Test
	void testAddUserThatExists() {
		User user = new User();
		user.setName("thomas15399");
		user.setPass("38831");
		Mono<User> userMono = Mono.just(user);
		when(userRepo.findById("thomas15399")).thenReturn(userMono);
		when(userRepo.insert(user)).thenReturn(userMono);
		Mono<User> result = userService.addUser(user);
		assertThat(result).isNull();
		//assertThat(result).isEqualTo(userMono);
	}
	@Test
	void testAddUserThatDoesNotExist() {
		Mono<User> nullMono = Mono.empty();
		User user = new User();
		user.setName("thomas15399");
		user.setPass("38831");
		Mono<User> userMono = Mono.just(user);
		when(userRepo.findById("thomas15399")).thenReturn(nullMono);
		when(userRepo.insert(user)).thenReturn(userMono);
		Mono<User> result = userService.addUser(user);
		assertThat(result).isNotNull();
		//assertThat(result).isEqualTo(userMono);
	}
	@Test
	void testUpdateUserThatExists() {
		User user = new User();
		user.setName("thomas15399");
		user.setPass("38831");
		Mono<User> userMono = Mono.just(user);
		when(userRepo.findById("thomas15399")).thenReturn(userMono);
		when(userRepo.save(user)).thenReturn(userMono);
		Mono<User> result = userService.updateUser(user);
		assertThat(result).isNotNull();
		//assertThat(result).isEqualTo(userMono);
	}
	@Test
	void testUpdateUserThatDoesNotExist() {
		Mono<User> nullMono = Mono.empty();
		User user = new User();
		user.setName("thomas15399");
		user.setPass("38831");
		Mono<User> userMono = Mono.just(user);
		when(userRepo.findById("thomas15399")).thenReturn(nullMono);
		when(userRepo.save(user)).thenReturn(userMono);
		Mono<User> result = userService.updateUser(user);
		assertThat(result).isNull();
		//assertThat(result).isEqualTo(userMono);
	}
}
