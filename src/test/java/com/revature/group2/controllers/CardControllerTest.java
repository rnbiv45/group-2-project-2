package com.revature.group2.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.User;
import com.revature.group2.services.CardService;
import com.revature.group2.utils.JWTParser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class CardControllerTest {
	@TestConfiguration
	static class Configuration {
		@Bean
		public CardController getCardController(CardService cardService, JWTParser parser) {
			CardController cardController = new CardController();
			cardController.setCardService(cardService);
			cardController.setTokenServicer(parser);
			return cardController;
		}

		@Bean
		public CardService getService() {
			return Mockito.mock(CardService.class);
		}
		@Bean
		public JWTParser getParser() {
			return Mockito.mock(JWTParser.class);
		}
		@Bean
		public ObjectMapper getMapper() {
			return Mockito.mock(ObjectMapper.class);
		}
	}
	
	@Autowired
	private CardController cardController;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private JWTParser parser;
	
	
	@Test
	void testGetAllCards() {
		ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class, Mockito.RETURNS_DEEP_STUBS);
		
		Mockito.when(cardService.getCardsFromSystemWithArguments(null, null, null, null)).thenReturn(Flux.just(new Card(), new Card()));
		
		StepVerifier.create(cardController.getAllCards(exchange, null, null, null, null))
			.expectNext(new Card())
			.expectNext(new Card())
			.expectComplete().verify();
		
	}
	
	@Test
	void testChangeStat() {
		ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class, Mockito.RETURNS_DEEP_STUBS);
		
		Mockito.when(cardService.changeCardInSystemWithArguments(null, null, null, null, null, null, null)).thenReturn(Flux.just(new Card()));
		
		StepVerifier.create(cardController.changeStat(exchange, null, null, null, null, null, null, null))
			.expectNext(new Card())
			.expectComplete().verify();
	}
	
	@Test
	void testGetUserCards() {
		User user = new User();
		user.setName("1");
		user.setUuid(UUID.randomUUID());
		HttpCookie cookie = Mockito.mock(HttpCookie.class);
		List<HttpCookie> cookies = new ArrayList<HttpCookie>();
		cookies.add(cookie);
		String token = "hello";
		ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class, Mockito.RETURNS_DEEP_STUBS);
		when(exchange.getRequest().getCookies().get("token")).thenReturn(cookies);
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
		
		Mockito.when(cardService.getCardsFromUser(user)).thenReturn(Flux.just(new Card(), new Card(), new Card()));
		
		StepVerifier.create(cardController.getUserCards(exchange))
			.expectNext(new Card())
			.expectNext(new Card())
			.expectNext(new Card())
			.expectComplete().verify();
	}
	
	
	@Test 
	public void testAddCard() {
		Card card = new Card();
		User user = new User();
		user.setName("1");
		user.setUuid(UUID.randomUUID());
		HttpCookie cookie = Mockito.mock(HttpCookie.class);
		List<HttpCookie> cookies = new ArrayList<HttpCookie>();
		cookies.add(cookie);
		String token = "hello";
		ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class, Mockito.RETURNS_DEEP_STUBS);
		when(exchange.getRequest().getCookies().get("token")).thenReturn(cookies);
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
		
		Mockito.when(cardService.addCardToSystem(card)).thenReturn(Mono.just(new Card()));
		
		StepVerifier.create(cardController.addCard(exchange, card))
			.expectNext(ResponseEntity.status(201).body(new Card()))
			.expectComplete().verify();
		
	}
	
	@Test
	void testGetCard() {
		
		ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class, Mockito.RETURNS_DEEP_STUBS);
		
		Mockito.when(cardService.getCardByName("9")).thenReturn(Mono.just(new Card()));
		
		StepVerifier.create(cardController.getCard(exchange, "9"))
			.expectNext(ResponseEntity.ok().body(new Card()))
			.expectComplete().verify();
	}
	/*
	@Test
	void testAddCardToUser() {
		Card card = new Card();
		User user = new User();
		user.setName("1");
		user.setUuid(UUID.randomUUID());
		HttpCookie cookie = Mockito.mock(HttpCookie.class);
		List<HttpCookie> cookies = new ArrayList<HttpCookie>();
		cookies.add(cookie);
		String token = "hello";
		ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class, Mockito.RETURNS_DEEP_STUBS);
		when(exchange.getRequest().getCookies().get("token")).thenReturn(cookies);
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
		
		Mockito.when(cardService.addCardToUser("9", user)).thenReturn(Mono.just(user));
		
		StepVerifier.create(cardController.addCard(exchange, card))
			.expectNext(ResponseEntity.status(201).body(new Card()))
			.expectComplete().verify();
	}
	*/
	@Test
	void testBanCard() {
		ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class, Mockito.RETURNS_DEEP_STUBS);
		
		Mockito.when(cardService.banCardFromSystem("9")).thenReturn(Mono.just(new Card()));
		
		StepVerifier.create(cardController.banCard(exchange, "9"))
			.expectNext(ResponseEntity.ok().body(new Card()))
			.expectComplete().verify();
	}
	/*
	@Test
	void TestUpdateCard() {
		UUID id = UUID.randomUUID();
		Card card = new Card();
		Mono<Card> cardMono = Mono.just(card);
		ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class, Mockito.RETURNS_DEEP_STUBS);
		
		Mockito.when(cardService.updateCard(Mono.just(card))).thenReturn(Flux.just(new Card()));
		
		StepVerifier.create(cardController.updateCard(exchange, card, id))
			.expectNext(new Card())
			.expectComplete().verify();
	}
	*/
}
