package com.revature.group2.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import com.revature.group2.beans.Deck;
import com.revature.group2.beans.User;
import com.revature.group2.services.DeckService;
import com.revature.group2.services.UserService;
import com.revature.group2.utils.JWTParser;

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
					return;
				}
			}
		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return;
		}
		
	}
}
