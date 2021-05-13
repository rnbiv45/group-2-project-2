package com.revature.group2.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.group2.beans.Deck;
import com.revature.group2.services.DeckService;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
public class DeckControllerTest {
	@TestConfiguration
	static class Configuration {
		@Bean
		public DeckController getDeckController(DeckService deckService) {
			DeckController deckController = new DeckController();
			deckController.setDeckService(deckService);
			return deckController;
			
		}
		@Bean
		public DeckService getDeckService(DeckService deckService) {
			return Mockito.mock(DeckService.class);
			
		}
	}
	@Autowired
	DeckController deckController;
	
	@Autowired
	DeckService deckService;
	
	@Test
	void addDeckToUserReturnsResponse() {
		Mono<ResponseEntity<Object>> expected = Mono.just(new Deck())
				.map(u -> ResponseEntity.status(201).body(u));
		String token = "";
		Mockito.when(deckService.addDeckToUser(Mockito.any(), Mockito.any(), Mockito.any()));	
		Mono<ResponseEntity<Object>> result = deckController.addDeckToUser(token, "WATER", "WIND");
		assertThat(result).isEqualTo(expected);
	}
	
}
