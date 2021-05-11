package com.revature.group2.controllers;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.Deck;
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

	@PostMapping(value="/test")
	public void addDummyUser() {
		User myUser = new User();
		Map<Card, Integer> myCards = new HashMap<Card, Integer>();
		Set<Deck> myDecks = new HashSet<>();
		myUser.setName("DummyUser");
		myUser.setDecks(myDecks);
		myUser.setCards(myCards);
		myUser.setPass("Tom");
		myUser.setRole(UserRole.MEMBER);
		userService.addUser(myUser);
		
	}
	
	@GetMapping(value="/test")
	public Flux<User> checkUsers() {
		return userService.getUsers();
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setTokenServicer(JWTParser parser) {
		this.tokenService = parser;
	}

	@PostMapping("/register")
	public Mono<ResponseEntity<User>> registerUser(@RequestBody User user){
		return userService.addUser(user).map(userVar -> ResponseEntity.ok().body(userVar)).onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body(user)));
	}

	@PostMapping(value="login", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Publisher<User> login(ServerWebExchange exchange, @RequestBody User user) {

		return userService.getUser(user.getName()).delayElement(Duration.ofSeconds(2)).doOnNext(nextUser -> {
			try {
				exchange.getResponse()
				.addCookie(ResponseCookie
						.from("token", tokenService.makeToken(nextUser))
						.httpOnly(true).build());
			} catch (JsonProcessingException e) {
				exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		});
	}
	
	@DeleteMapping("logout")
	public ResponseEntity<Void> logout(ServerWebExchange exchange) {
		exchange.getResponse().addCookie(ResponseCookie.from("token", "").httpOnly(true).build());
		return ResponseEntity.noContent().build();
	}
}
