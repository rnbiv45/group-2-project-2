package com.revature.group2.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.revature.group2.beans.Card;
import com.revature.group2.beans.User;
import com.revature.group2.services.CardService;
import com.revature.group2.utils.JWTParser;

import reactor.core.publisher.Flux;
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
	}
	
	@Autowired
	private CardController cardController;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private JWTParser parser;
	
	@Test
	public void testGetAllCards() {
		Card[] cards = {(new Card()),(new Card())};
		Flux<Card> cardFlux = Flux.fromArray(cards);
		
		Mockito.when(cardService.getCardsFromSystem()).thenReturn(cardFlux);
		
		Flux<Card> result = cardController.getAllCards(null, null, null, null);
		
		assertThat(result).isEqualTo(cardFlux);
		
	}
	
	@Test 
	public void testAddCard() {
		/*
		Card card = new Card();
		ArgumentCaptor<Card> captor = ArgumentCaptor.forClass(Card.class);
		//when(cardService.getUser(user.getUsername())).thenReturn(null);
		cardController.addCard(card);
		verify(cardService).addCardToSystem(captor.capture());
		*/
		
	}
	
	@Test
	public void testGetUserCards() {
		ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class,  Mockito.RETURNS_DEEP_STUBS);
		HttpCookie cookie = Mockito.mock(HttpCookie.class);
		List<HttpCookie> cookies = new ArrayList<HttpCookie>();
		cookies.add(cookie);
		User user = new User();
		Card card1 = new Card();
		Card card2 = new Card();
		Card card3 = new Card();
		card1.setName("t1");
		card2.setName("t2");
		card3.setName("t3");
		Map<Card, Integer> cards = new HashMap <Card, Integer>();
		cards.put(card1, 3);
		cards.put(card2, 5);
		cards.put(card3, 1);
		user.setCards(cards);
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
		Map<Card, Integer> result = cardController.getUserCards(exchange);
		assertThat(result).isEqualTo(cards);
		//StepVerifier.create(result).expectNext(ResponseEntity.status(200).body(user.getCards())).expectComplete().verify();
	}

}
