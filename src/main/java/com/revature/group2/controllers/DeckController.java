package com.revature.group2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
	DeckService deckService;
	private JWTParser tokenService;
	
	@Autowired
	public void setDeckService(DeckService deckService) {
		this.deckService = deckService;
	}
	
	@Autowired
	public void setTokenServicer(JWTParser parser) {
		this.tokenService = parser;
	}
	
	@PostMapping
	public Mono<ResponseEntity<Object>> addDeckToUser(@CookieValue String token) {
		try {
			User user = tokenService.parser(token);
			return deckService.addDeckToUser(user).map(u -> ResponseEntity.status(201).body(u));
		} catch (Exception e) {
			return Mono.just(ResponseEntity.status(500).body(e));
		}
	}
}
