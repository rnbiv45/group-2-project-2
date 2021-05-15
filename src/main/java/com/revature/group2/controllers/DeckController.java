package com.revature.group2.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.revature.group2.aspects.Authorized;
import com.revature.group2.aspects.OwnerAndAdmin;
import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.Deck;
import com.revature.group2.beans.User;
import com.revature.group2.services.DeckService;
import com.revature.group2.services.UserService;
import com.revature.group2.utils.JWTParser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/decks")
public class DeckController {
	private DeckService deckService;
	private UserService userService;
	private JWTParser tokenService;
	private String tokenString = "token";

	@Autowired
	public void setDeckService(DeckService deckService) {
		this.deckService = deckService;
	}

	@Autowired
	public void setTokenServicer(JWTParser parser) {
		this.tokenService = parser;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Authorized
	@GetMapping
	public Flux<Deck> viewDecks(ServerWebExchange exchange){
		User user = null;
		try {
			if(exchange.getRequest().getCookies().get(tokenString) != null) {
				String token = exchange.getRequest().getCookies().getFirst(tokenString).getValue();
				if(!token.equals("")) {
					user = tokenService.parser(token);
					return deckService.getUserDecks(user);
				}
			}
		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return null;
		}
		return null;
		
	}
	
	@Authorized
	@DeleteMapping()
	public void deleteOwnDeck(ServerWebExchange exchange, Deck deck) {
		User user = null;
		try {
			if(exchange.getRequest().getCookies().get(tokenString) != null) {
				String token = exchange.getRequest().getCookies().getFirst(tokenString).getValue();
				if(!token.equals("")) {
					user = tokenService.parser(token);
					//user.getDecks().remove(deck);
//					//userService.updateUser(user);
					//exchange.getResponse().addCookie(ResponseCookie.from("token", "").httpOnly(true).build());
					//exchange.getResponse().addCookie(ResponseCookie.from("token", tokenService.makeToken(user)).httpOnly(true).build());
					user.getDecks().remove(deck.getKey().getUuid().toString());
					userService.updateUser(Mono.just(user));
					exchange.getResponse().addCookie(ResponseCookie.from(tokenString, "").httpOnly(true).build());
					exchange.getResponse().addCookie(ResponseCookie.from(tokenString, tokenService.makeToken(user)).httpOnly(true).build());
					return;
				}
			}
		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return;
		}
	}
		
	@Authorized
	@OwnerAndAdmin
	@PostMapping
	public Mono<ResponseEntity<Object>> addDeckToUser(
			@CookieValue(value="token") String token,
			@RequestParam String primaryArchetype,
			@RequestParam String secondaryArchetype) {
		try {
			Archetype primary = Archetype.valueOf(primaryArchetype);
			Archetype secondary = Archetype.valueOf(secondaryArchetype);
			User user = tokenService.parser(token);
			return deckService.addDeckToUser(user, primary, secondary).map(u -> ResponseEntity.status(201).body(u));
		} catch (Exception e) {
			return Mono.just(ResponseEntity.status(500).body(e));
		}
	}
	
	@Authorized
	@OwnerAndAdmin
	@PostMapping("/card")
	public Mono<ResponseEntity<Object>> addCardToDeck(ServerWebExchange exchange, Deck deck, Card card) {
		User user = null;
		try {
			if(exchange.getRequest().getCookies().get(tokenString) != null) {
				String token = exchange.getRequest().getCookies().getFirst(tokenString).getValue();
				if(!token.equals("")) {
					user = tokenService.parser(token);
					return null;
				}
			}
		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return null;
		}
		return null;
	}
	
	@Authorized
	@DeleteMapping("/card")
	public Mono<ResponseEntity<Object>> removeCardFromDeck(ServerWebExchange exchange, Deck deck, Card card) {
		return null;
	}
	
	@Authorized
	@PutMapping("/{uuid}")
	public Flux<Deck> updateDeck(ServerWebExchange exchange, @RequestBody Deck deck, @PathVariable UUID uuid) {
		deck.getKey().setUuid(uuid);
		return deckService.updateDeck(Mono.just(deck));
	}
}
