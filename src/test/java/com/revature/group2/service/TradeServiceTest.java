package com.revature.group2.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardKey;
import com.revature.group2.beans.Trade;
import com.revature.group2.beans.TradeStatus;
import com.revature.group2.beans.User;
import com.revature.group2.repos.TradeRepo;
import com.revature.group2.repos.UserRepo;
import com.revature.group2.services.TradeService;
import com.revature.group2.services.TradeServiceImp;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
public class TradeServiceTest {
	UUID id1;
	UUID id2;
	UUID id3;
	String cardId1;
	String cardId2;
	String cardId3;
	String cardId4;
	Trade trades[];
	@TestConfiguration
	static class Configuration {
		
		@Bean
		public TradeService getTradeService(TradeRepo tradeRepo, UserRepo userRepo) {
			TradeService tradeService = new TradeServiceImp();
			tradeService.setTradeRepo(tradeRepo);
			tradeService.setUserRepo(userRepo);
			return tradeService;
		}
		@Bean
		public TradeRepo getTradeRepo() {
			return Mockito.mock(TradeRepo.class);
		}
		@Bean
		public UserRepo getUserRepo() {
			return Mockito.mock(UserRepo.class);
		}
		
	}
	@Autowired
	TradeService tradeService;
	@Autowired
	TradeRepo tradeRepo;
	@Autowired
	UserRepo userRepo;
	
	@BeforeEach
	void Setup() {
		id1 = UUID.randomUUID();
		id2 = UUID.randomUUID();
		id3 = UUID.randomUUID();
		cardId1 = UUID.randomUUID().toString();
		cardId2 = UUID.randomUUID().toString();
		cardId3 = UUID.randomUUID().toString();
		cardId4 = UUID.randomUUID().toString();
		trades = new Trade[5];
		for(int i = 0; i < trades.length; i++) {
			trades[i] = new Trade();
			trades[i].setPoster("Tom");
			trades[i].setPosterId(id1);
			trades[i].setTradeStatus(TradeStatus.PENDING);
			trades[i].setTradeId(UUID.randomUUID());
		}
		trades[3].setTradeStatus(TradeStatus.ACCEPTED);
		trades[3].setAcceptor("Bill");
		trades[3].setAcceptorId(id3);
		trades[2].setPoster("Bob");
		trades[2].setPosterId(id2);
		trades[0].setCard1(cardId1);
		trades[1].setCard1(cardId4);
		trades[2].setCard1(cardId3);
		trades[3].setCard1(cardId3);
		trades[4].setCard1(cardId2);
		trades[0].setCard2(cardId2);
		trades[1].setCard2(cardId1);
		trades[2].setCard2(cardId1);
		trades[3].setCard2(cardId4);
		trades[4].setCard2(cardId3);
	}
	
	@AfterEach
	void teardown() {
		id1 = null;
		id2 = null;
		id3 = null;
		cardId1 = null;
		cardId2 = null;
		cardId3 = null;
		cardId4 = null;
		trades = null;
	}
	
	@Test
	void SanityCheck() {
		assertTrue(true);
	}
	
	@Test
	void testViewPendingTrades() {
		Flux<Trade> tradesFlux = Flux.fromArray(trades);
		when(tradeRepo.findAll()).thenReturn(tradesFlux);
		Flux<Trade> result = tradeService.viewPendingTrades();
		assertTrue(result.hasElement(trades[0]).block());
		assertTrue(result.hasElement(trades[1]).block());
		assertTrue(result.hasElement(trades[2]).block());
		assertFalse(result.hasElement(trades[3]).block());
		assertTrue(result.hasElement(trades[4]).block());
	}
	
	@Test
	void testViewTradesForCard() {
		Card card = new Card();
		CardKey key = new CardKey();
		key.setUuid(UUID.fromString(cardId3));card.setKey(key);
		Flux<Trade> tradesFlux = Flux.fromArray(trades);
		when(tradeRepo.findAll()).thenReturn(tradesFlux);
		Flux<Trade> result = tradeService.viewTradesForCard(card);
		assertFalse(result.hasElement(trades[0]).block());
		assertFalse(result.hasElement(trades[1]).block());
		assertTrue(result.hasElement(trades[2]).block());
		assertTrue(result.hasElement(trades[3]).block());
		assertFalse(result.hasElement(trades[4]).block());
	}
	
	@Test
	void testViewTradesByUser() {
		User user = new User();
		user.setName("Tom");
		user.setUuid(id1);
		Flux<Trade> tradesFlux = Flux.fromArray(trades);
		when(tradeRepo.findAll()).thenReturn(tradesFlux);
		Flux<Trade> result = tradeService.viewTradesByUser(user);
		assertTrue(result.hasElement(trades[0]).block());
		assertTrue(result.hasElement(trades[1]).block());
		assertFalse(result.hasElement(trades[2]).block());
		assertTrue(result.hasElement(trades[3]).block());
		assertTrue(result.hasElement(trades[4]).block());
	}
	
	@Test
	void testSubmitTrade() {
		Trade trade = new Trade();
		Mono<Trade> tradeMono = Mono.just(trade);
		trade.setCard1(cardId1);
		trade.setCard2(cardId3);
		trade.setPosterId(id1);
		when(tradeRepo.save(trade)).thenReturn(tradeMono);
		ArgumentCaptor<Trade> captor = ArgumentCaptor.forClass(Trade.class);
		tradeService.submitTrade(trade);
		verify(tradeRepo).save(captor.capture());
	}
	
	@Test
	void testSubmitInvalidTrade() {
		Trade trade = new Trade();
		Mono<Trade> tradeMono = Mono.just(trade);
		trade.setCard1(cardId1);
		trade.setCard2(cardId3);
		when(tradeRepo.save(trade)).thenReturn(tradeMono);
		Mono<Trade> result = tradeService.submitTrade(trade);
		assertNull(result);
	}
	/*
	@Test
	void testAcceptTrade() {
	
	}
	
	@Test
	void testReverseTrade() {
	
	}
*/
}
