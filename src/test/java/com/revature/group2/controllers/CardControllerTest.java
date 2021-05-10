package com.revature.group2.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.group2.beans.Card;
import com.revature.group2.services.CardService;

import reactor.core.publisher.Flux;

@ExtendWith(SpringExtension.class)
public class CardControllerTest {
	@TestConfiguration
	static class Configuration {
		@Bean
		public CardController getCardController(CardService cardService) {
			CardController cardController = new CardController();
			cardController.setCardService(cardService);
			return cardController;
		}

		@Bean
		public CardService getService() {
			return Mockito.mock(CardService.class);
		}
	}
	
	@Autowired
	private CardController cardController;
	
	@Autowired
	private CardService cardService;
	
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
		Card card = new Card();
		ArgumentCaptor<Card> captor = ArgumentCaptor.forClass(Card.class);
		//when(cardService.getUser(user.getUsername())).thenReturn(null);
		cardController.addCard(card);
		verify(cardService).addCardToSystem(captor.capture());
		
	}

}
