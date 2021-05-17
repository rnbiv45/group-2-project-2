package com.revature.group2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
		assertThat(result).isEqualTo(userFlux);
		
	}
	
	@Test
	void testGetUser() {
		User user = new User();
		Mono<User> userMono = Mono.just(user);
		when(userRepo.findByName("thomas15399")).thenReturn(userMono);
		Mono<User> result = userService.getUser("thomas15399");
		assertThat(result).isEqualTo(userMono);
	}
	@Test
	void testGetUserNoUserFound() {
		Mono<User> nullMono = Mono.empty();
		when(userRepo.findByName("thomas15399")).thenReturn(nullMono);
		Mono<User> result = userService.getUser("thomas15399");
		assertThat(result).isEqualTo(nullMono);
	}
	@Test
	void testGetUserByUuid() {
		UUID id = UUID.randomUUID();
		User user = new User();
		Mono<User> userMono = Mono.just(user);
		when(userRepo.findByUuid(id)).thenReturn(userMono);
		Mono<User> result = userService.getUserByUUID(id);
		assertThat(result).isEqualTo(userMono);
	}
	@Test
	void testGetUserBiUuidNoUserFound() {
		UUID id = UUID.randomUUID();
		Mono<User> nullMono = Mono.empty();
		when(userRepo.findByUuid(id)).thenReturn(nullMono);
		Mono<User> result = userService.getUserByUUID(id);
		assertThat(result).isEqualTo(nullMono);
	}
	@Test
	void testAddUser() {
		User user = new User();
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		userService.addUser(user);
		verify(userRepo).save(captor.capture());
	}
	
	@Test
	void testUpdateUser() {
		Mono<User> user = Mono.just(new User());
		ArgumentCaptor<Mono<User>> captor = ArgumentCaptor.forClass(Mono.class);
		userService.updateUser(user);
		verify(userRepo).saveAll(captor.capture());
	}
	/*
	@Test
	void testUpdateUserThatDoesNotExist() {
		
	}
	*/
}
