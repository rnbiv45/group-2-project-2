package com.revature.group2.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.group2.beans.User;
import com.revature.group2.services.UserService;
import com.revature.group2.utils.JWTParser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

	@TestConfiguration
	static class Configuration {
		@Bean
		public UserController getUserController(UserService userService, JWTParser parser) {
			UserController userController = new UserController();
			userController.setUserService(userService);
			userController.setTokenServicer(parser);
			return userController;
			
		}
		@Bean
		public UserService getUserService() {
			return Mockito.mock(UserService.class);
			
		}
		
		@Bean
		public JWTParser getJWTParser() {
			return Mockito.mock(JWTParser.class);
			
		}
		
		@Bean
		public ObjectMapper getMapper() {
			return Mockito.mock(ObjectMapper.class);
		}
	}
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JWTParser tokenService;
	
	@Test
	void testRegisterUser() {
		User user = new User();
		when(userService.addUser(user)).thenReturn(Mono.just(user));
		Mono<ResponseEntity<User>> result = userController.registerUser(user);
		StepVerifier.create(result).expectNext(ResponseEntity.status(201).body(user)).expectComplete().verify();
	}
	
	@Test
	void testLogout() {
		MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.delete("/users/logout"));
		ResponseEntity<Void> result = userController.logout(exchange);
		assertTrue(result.getStatusCodeValue() == 204);
	}
	/*
	@Test
	void testBanUser() {
		ArgumentCaptor<Optional<UUID>> captor = ArgumentCaptor.forClass(Optional.class);
		MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.delete("/users/{uuid}"));
		userController.ban(exchange, Optional.of(UUID.randomUUID()));
		verify(userService.banUser(captor.capture()));
	}
	*/
}
