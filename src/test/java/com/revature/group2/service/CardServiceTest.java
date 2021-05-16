package com.revature.group2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardKey;
import com.revature.group2.repos.CardRepo;
import com.revature.group2.services.CardService;
import com.revature.group2.services.CardServiceImp;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
public class CardServiceTest {
	@TestConfiguration
	static class Configuration {
		@Bean
		public CardService getCardService(CardRepo cardRepo) {
			CardService cardService = new CardServiceImp();
			cardService.setCardRepo(cardRepo);
			return cardService;
		}

		@Bean
		public CardRepo getService() {
			return Mockito.mock(CardRepo.class);
		}
	}
	
	@Autowired
	CardService cardService;
	
	@Autowired
	CardRepo cardRepo;
	
	@Test
	void testGetCardsFromSystem () {
		Card[] cards = {new Card(), new Card(), new Card()};
		Flux<Card> cardFlux = Flux.fromArray(cards);
		when(cardRepo.findAll()).thenReturn(cardFlux);
		Flux<Card> result = cardService.getCardsFromSystem();
		assertThat(result).isEqualTo(cardFlux);
		
	}
	
	@Test
	void testGetCard() {
		Card card = new Card();
		Mono<Card> cardMono = Mono.just(card);
		CardKey key = new CardKey();
		when(cardRepo.findById(key)).thenReturn(cardMono);
		Mono<Card> result = cardService.getCard(key);
		assertThat(result).isEqualTo(cardMono);
	}
	@Test
	void testGetCardThatDoesNotExist() {
		Card card = new Card();
		CardKey key = new CardKey();
		card.setKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		when(cardRepo.findById(key)).thenReturn(nullMono);
		Mono<Card> result = cardService.getCard(key);
		assertThat(result).isEqualTo(nullMono);
	}
	
	@Test
	void testBanCardBansACard() {
		Card card = new Card();
		Card banningCard = new Card();
		CardKey cardKey = new CardKey();
		String name = "a";
		
		cardKey.setIsBanned(false);
		card.setKey(cardKey);
		
		Mono<Card> cardMono = Mono.just(card);
		
		cardKey.setIsBanned(true);
		banningCard.setKey(cardKey);
		
		Mono<Card> bannedCard = Mono.just(banningCard);
		Mono<Void> voidMono = Mono.empty();
		
		when(cardRepo.findById(cardKey)).thenReturn(cardMono);
		when(cardRepo.delete(card)).thenReturn(voidMono);
		when(cardRepo.insert(banningCard)).thenReturn(bannedCard);
		when(cardRepo.findByName(name)).thenReturn(cardMono);
		
		Mono<Card> result = cardService.banCardFromSystem(name);
		Mono<Boolean> comparer = Mono.sequenceEqual(result, bannedCard);
		comparer.subscribe(bool -> {
			assertThat(bool).isEqualTo(true);
		});
		
		
	}
	/*
	@Test
	void testAddCardDoesNotExist() {
		Card card = new Card();
		CardPrimaryKey key = new CardPrimaryKey();
		card.setCardPrimaryKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		//System.out.println(cardMono);
		when(cardRepo.findById(card.getCardPrimaryKey())).thenReturn(nullMono);
		when(cardRepo.insert(card)).thenReturn(cardMono);
		Mono<Card> result = cardService.addCardToSystem(card);
		assertThat(result).isNotNull();
	}
	@Test 
	void testAddCardThatExists() {
		Card card = new Card();
		CardPrimaryKey key = new CardPrimaryKey();
		card.setCardPrimaryKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		//System.out.println(card.getCardPrimaryKey());
		when(cardRepo.findById(card.getCardPrimaryKey())).thenReturn(cardMono);
		when(cardRepo.insert(card)).thenReturn(cardMono);
		Mono<Card> result =  cardService.addCardToSystem(card);
		assertThat(result).isNull();
	}
	@Test
	void testUpdateCardDoesNotExist() {
		Card card = new Card();
		CardPrimaryKey key = new CardPrimaryKey();
		card.setCardPrimaryKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		when(cardRepo.findById(key)).thenReturn(nullMono);
		when(cardRepo.save(card)).thenReturn(cardMono);
		Mono<Card> result = cardService.setCard(card);
		assertThat(result).isNull();
	}
	@Test 
	void testUpdateCardThatExists() {
		Card card = new Card();
		CardPrimaryKey key = new CardPrimaryKey();
		card.setCardPrimaryKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		when(cardRepo.findById(key)).thenReturn(cardMono);
		when(cardRepo.save(card)).thenReturn(cardMono);
		Mono<Card> result = cardService.setCard(card);
		assertThat(result).isNotNull();
	}
	@Test
	void testDeleteCardDoesNotExist() {
		Card card = new Card();
		CardPrimaryKey key = new CardPrimaryKey();
		card.setCardPrimaryKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		Mono<Void> voidMono = Mono.empty();
		when(cardRepo.findById(key)).thenReturn(nullMono);
		when(cardRepo.delete(card)).thenReturn(voidMono);
		Mono<Void> result = cardService.removeCardFromSystem(card);
		assertThat(result).isNull();
	}
	@Test 
	void testDeleteCardThatExists() {
		Card card = new Card();
		CardPrimaryKey key = new CardPrimaryKey();
		card.setCardPrimaryKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		Mono<Void> voidMono = Mono.empty();
		when(cardRepo.findById(key)).thenReturn(cardMono);
		when(cardRepo.delete(card)).thenReturn(voidMono);
		Mono<Void> result = cardService.removeCardFromSystem(card);
		assertThat(result).isNotNull();
	}
*/
}
