package com.revature.group2.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.Trade;
import com.revature.group2.beans.User;
import com.revature.group2.services.TradeService;
import com.revature.group2.utils.JWTParser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class TradeControllerTest {
	@TestConfiguration
	static class Configuration {
		
		@Bean
		public TradeController getTradeController(TradeService tradeService, JWTParser parser) {
			TradeController tradeController = new TradeController();
			tradeController.setTradeService(tradeService);
			tradeController.setTokenService(parser);
			tradeController.setTokenString();
			return tradeController;
		}
		@Bean
		public TradeService getTradeService() {
			return Mockito.mock(TradeService.class);
		}
		@Bean
		public JWTParser getTradeParser() {
			return Mockito.mock(JWTParser.class);
		}
		@Bean
		public ObjectMapper getMapper() {
			return Mockito.mock(ObjectMapper.class);
		}
		
	}
	@Autowired
	TradeController tradeController;
	
	@Autowired
	TradeService tradeService;
	
	@Autowired
	JWTParser parser;
	
	//String tokenString = "token";
	
	@Test
	void sanitcyCheck() {
		assertTrue(true);
	}
	
	 @Test
	 void testViewPendingTrades() {
		 ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class, Mockito.RETURNS_DEEP_STUBS);
		 when(tradeService.viewPendingTrades()).thenReturn(Flux.just(new Trade(), new Trade(), new Trade()));
		 StepVerifier.create(tradeController.getPendingTrades(exchange))
		 	.expectNext(new Trade())
		 	.expectNext(new Trade())
		 	.expectNext(new Trade())
		 	.expectComplete().verify();
	 }
	 
	 @Test
	 void testViewOwnTrades() {
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
		 when(tradeService.viewTradesByUser(user)).thenReturn(Flux.just(new Trade(), new Trade(), new Trade()));
		 StepVerifier.create(tradeController.getTradesByUser(exchange, user))
		 	.expectNext(new Trade())
		 	.expectNext(new Trade())
		 	.expectNext(new Trade())
		 	.expectComplete().verify();
	 }
	 
	 @Test
	 void testViewTradesByCard() {
		 User user = new User();
		 Card card = new Card();
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
		 when(tradeService.viewTradesForCard(card)).thenReturn(Flux.just(new Trade(), new Trade(), new Trade()));
		 StepVerifier.create(tradeController.getTradesByCard(exchange, card))
		 	.expectNext(new Trade())
		 	.expectNext(new Trade())
		 	.expectNext(new Trade())
		 	.expectComplete().verify();
	 }
	 
	 @Test
	 void testSubmitTrade() {
		 User user = new User();
		 Trade trade = new Trade();
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
		 when(tradeService.submitTrade(trade)).thenReturn(Mono.just(new Trade()));
		 StepVerifier.create(tradeController.submitTrade(exchange, trade))
		 	.expectNext(new Trade())
			.expectComplete().verify();

	 }
	 
	 @Test
	 void testAcceptTrade() {
		 User user = new User();
		 Trade trade = new Trade();
		 trade.setTradeId(UUID.randomUUID());
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
		 when(tradeService.acceptTrade(trade.getTradeId(), user)).thenReturn(Mono.just(new Trade()));
		 StepVerifier.create(tradeController.acceptTrade(exchange, trade))
		 	.expectNext(new Trade())
		 	.expectComplete().verify();
	 }
}
