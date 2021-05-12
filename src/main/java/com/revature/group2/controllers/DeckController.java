package com.revature.group2.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.server.ServerWebExchange;

import com.revature.group2.beans.Deck;
import com.revature.group2.beans.User;
import com.revature.group2.services.DeckService;
import com.revature.group2.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.revature.group2.beans.User;
import com.revature.group2.services.DeckService;
import com.revature.group2.utils.JWTParser;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/decks")
public class DeckController {
	private DeckService deckService;
	private UserService userService;
	private JWTParser tokenService;

	@Autowired
	public void setDeckService(DeckService deckService) {
		this.deckService = deckService;
	}

	@Autowired
	public void setTokenServicer(JWTParser parser) {
		this.tokenService = parser;
	}

	public Set<Deck> viewDecks(ServerWebExchange exchange){
		User user = null;
		try {
			if(exchange.getRequest().getCookies().get("token") != null) {
				String token = exchange.getRequest().getCookies().getFirst("token").getValue();
				if(!token.equals("")) {
					user = tokenService.parser(token);
					return user.getDecks();
				}
			}
		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return null;
		}
		return null;
		
	}
	
	public void deleteOwnDeck(ServerWebExchange exchange, Deck deck) {
		User user = null;
		try {
			if(exchange.getRequest().getCookies().get("token") != null) {
				String token = exchange.getRequest().getCookies().getFirst("token").getValue();
				if(!token.equals("")) {
					user = tokenService.parser(token);
					user.getDecks().remove(deck);
					userService.updateUser(user);
					exchange.getResponse().addCookie(ResponseCookie.from("token", "").httpOnly(true).build());
					exchange.getResponse().addCookie(ResponseCookie.from("token", tokenService.makeToken(user)).httpOnly(true).build());
					return;
				}
			}
		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return;
		}
	}
		
	@PostMapping
	public Mono<ResponseEntity<Object>> addDeckToUser(@CookieValue String token) {
		try {
			User user = tokenService.parser(token);
			return deckService.addDeckToUser(user).map(u -> ResponseEntity.status(201).body(u));
		} catch (JsonMappingException e) {
			return Mono.just(ResponseEntity.status(500).body("No valid token"));
		} catch (JsonProcessingException e) {
			return Mono.just(ResponseEntity.status(500).body("No valid token"));
		}
	}
}
