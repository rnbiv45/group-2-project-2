package com.revature.group2.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpCookie;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Deck;
import com.revature.group2.beans.User;
import com.revature.group2.services.DeckService;
import com.revature.group2.services.UserService;
import com.revature.group2.utils.JWTParser;

@ExtendWith(SpringExtension.class)
public class DeckControllerTest {
	@TestConfiguration
	static class Configuration {
		@Bean
		public DeckController getDeckController(DeckService deckService, JWTParser parser, UserService userService) {
			DeckController deckController = new DeckController();
			deckController.setDeckService(deckService);
			deckController.setTokenServicer(parser);
			deckController.setUserService(userService);
			return deckController;
			
		}
		@Bean
		public DeckService getDeckService() {
			return Mockito.mock(DeckService.class);
			
		}
		
		@Bean
		public JWTParser getParser() {
			return Mockito.mock(JWTParser.class);
		}
		
		@Bean
		public UserService getUserService() {
			return Mockito.mock(UserService.class);
		}
	}
	@Autowired
	DeckController deckController;
	
	@Autowired
	DeckService deckService;

	@Autowired
	JWTParser parser;
	
	@Autowired
	UserService userService;
	
	@Test
	void sanitcyCheck() {
		assertTrue(true);
	}
	
	
	@Test
	void testViewDeck() {
		User user = new User();
		Deck deck1 = new Deck();
		Deck deck2 = new Deck();
		deck1.setPrimaryArchetype(Archetype.EARTH);
		deck2.setPrimaryArchetype(Archetype.FIRE);
		Set<Deck> decks = new HashSet<Deck>();
		decks.add(deck1);
		decks.add(deck2);
		user.setDecks(decks);
		ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class,  Mockito.RETURNS_DEEP_STUBS);
		HttpCookie cookie = Mockito.mock(HttpCookie.class);
		List<HttpCookie> cookies = new ArrayList<HttpCookie>();
		cookies.add(cookie);
		String token = "hello";
		Mockito.when(exchange.getRequest().getCookies().get("token")).thenReturn(cookies);
		Mockito.when(exchange.getRequest().getCookies().getFirst("token").getValue()).thenReturn(token);
		try {
			Mockito.when(parser.parser(token)).thenReturn(user);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}
		Set<Deck> result = deckController.viewDecks(exchange);
		assertThat(result).isEqualTo(decks);
		
	}
	
	@Test
	public void testDeletedeckExists() {
		User user = new User();
		Deck deck1 = new Deck();
		Deck deck2 = new Deck();
		deck1.setPrimaryArchetype(Archetype.EARTH);
		deck2.setPrimaryArchetype(Archetype.FIRE);
		Set<Deck> decks = new HashSet<Deck>();
		decks.add(deck1);
		decks.add(deck2);
		user.setDecks(decks);
		ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class,  Mockito.RETURNS_DEEP_STUBS);
		HttpCookie cookie = Mockito.mock(HttpCookie.class);
		List<HttpCookie> cookies = new ArrayList<HttpCookie>();
		cookies.add(cookie);
		String token = "hello";
		Mockito.when(exchange.getRequest().getCookies().get("token")).thenReturn(cookies);
		Mockito.when(exchange.getRequest().getCookies().getFirst("token").getValue()).thenReturn(token);
		try {
			Mockito.when(parser.parser(token)).thenReturn(user);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}
		
	}
	@Test
	public void testDeleteDeckNotExist() {
		User user = new User();
		ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class,  Mockito.RETURNS_DEEP_STUBS);
		HttpCookie cookie = Mockito.mock(HttpCookie.class);
		List<HttpCookie> cookies = new ArrayList<HttpCookie>();
		cookies.add(cookie);
		String token = "hello";
		Mockito.when(exchange.getRequest().getCookies().get("token")).thenReturn(cookies);
		Mockito.when(exchange.getRequest().getCookies().getFirst("token").getValue()).thenReturn(token);
		try {
			Mockito.when(parser.parser(token)).thenReturn(user);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}
		
	}

}
