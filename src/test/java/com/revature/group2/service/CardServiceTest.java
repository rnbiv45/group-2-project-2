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
import com.revature.group2.beans.CardPrimaryKey;
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
		//assertEquals("userService should return that is passed to it by the userRepo", userFlux, result);
		assertThat(result).isEqualTo(cardFlux);
		
	}
	
	@Test
	void testGetCard() {
		Card card = new Card();
		Mono<Card> cardMono = Mono.just(card);
		CardPrimaryKey key = new CardPrimaryKey();
		when(cardRepo.findById(key)).thenReturn(cardMono);
		Mono<Card> result = cardService.getCard(key);
		assertThat(result).isEqualTo(cardMono);
	}

}
