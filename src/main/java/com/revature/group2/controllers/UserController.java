package com.revature.group2.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.group2.beans.User;
import com.revature.group2.beans.UserRole;
import com.revature.group2.services.UserService;
import com.revature.group2.utils.JWTParser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="users")
public class UserController {
	
	private UserService userService;
	private JWTParser tokenService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setTokenServicer(JWTParser parser) {
		this.tokenService = parser;
	}
	
	@GetMapping
	public Flux<User> getAll(
			@CookieValue(value = "token", defaultValue = "")String token, 
			ServerWebExchange exchange,
			@RequestParam Optional<UUID> card,
			@RequestParam Optional<UserRole> role) {
		exchange.getResponse().setStatusCode(HttpStatus.OK);
		return userService.getAll(
				card,
				role);
	}
	
	@GetMapping(value="/meta")
	public Flux<Map<String, Integer>> getMetaCards(ServerWebExchange exchange){
		exchange.getResponse().setStatusCode(HttpStatus.OK);
		return userService.metaCard();
	}
	
	
	@GetMapping("/{uuid}")
	public Mono<ResponseEntity<Object>> getUser() {
		return null;
	}
	
	@PostMapping(value="/test")
	public void addDummyUser() {
		User myUser = new User();
		Map<String, Integer> myCards = new HashMap<>();
		Set<String> myDecks = new HashSet<>();
		myUser.setName("DummyUser");
		myUser.setDecks(myDecks);
		myUser.setCards(myCards);
		myUser.setPass("Tom");
		myUser.setRole(UserRole.MEMBER);
		userService.addUser(myUser);
	}
	
	@GetMapping(value="/test/check")
	public Flux<User> checkUsers() {
		return userService.getUsers();
	}
	
//	@GetMapping(value="/test/check")
//	public Mono<User> addCardToUser() {
//		return userService.
//	}

	@PostMapping("/register")
	public Mono<ResponseEntity<User>> registerUser(@RequestBody User user){
		System.out.println(user);
		return userService.addUser(user).map(userVar -> ResponseEntity.status(201).body(userVar)).onErrorStop();
	}

	@PostMapping(value="login", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<User> login(@CookieValue(value = "token", defaultValue = "")String token, ServerWebExchange exchange, @RequestBody User user) {
		
		return userService.getUserByNameAndPass(user.getName(), user.getPass()).doOnNext(u -> {
			try {
				exchange.getResponse()
				.addCookie(ResponseCookie
						.from("token", tokenService.makeToken(u))
						.httpOnly(true).path("/").build());
			} catch (JsonProcessingException e) {
				exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		});
		
//		return userService.getUserByNameAndPass(user.getName(), user.getPass())
//				.delayElement(Duration.ofSeconds(2)).doOnNext(nextUser -> {
//			try {
//				System.out.println(nextUser);
//				System.out.println(tokenService.makeToken(nextUser));
//				exchange.getResponse()
//				.addCookie(ResponseCookie
//						.from("token", tokenService.makeToken(nextUser))
//						.httpOnly(true).path("/").build());
//			} catch (JsonProcessingException e) {
//				exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		});
	}
	
	@DeleteMapping("logout")
	public ResponseEntity<Void> logout(ServerWebExchange exchange) {
		exchange.getResponse().addCookie(ResponseCookie.from("token", "").httpOnly(true).path("/").build());
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{uuid}")
	public Flux<User> ban(ServerWebExchange exchange, @PathVariable Optional<UUID> uuid) {
		exchange.getResponse().setStatusCode(HttpStatus.OK);
		return userService.banUser(uuid);
	}
	
	@PostMapping("/parsertest")
	public void parsertest(){
		User u = new User();
		u.setName("bob");
		u.setPass("bubba");
		u.setRole(UserRole.ADMIN);
		u.setUuid(UUID.randomUUID());
		User u2 = new User();
		String token ;
		try {
			token = tokenService.makeToken(u);
			u2 = tokenService.parser(token);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("u" + u);
		System.out.println("us" + u2);
		System.out.println(u.equals(u2));
	}
}
