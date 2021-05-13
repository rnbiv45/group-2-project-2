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
	void addDeckToUserAddsDeck() {
//		Mono<ResponseEntity<Object>> 
//		String token = "";
//		
//		Mockito.when(deckService.addDeckToUser(null, null, null))
//		
//		Mono<ResponseEntity<Object>> result = deckController.addDeckToUser(token, "WATER", "WIND");
//		assertThat(result).isEqualTo();
	}
	
	@Test
	void addDeckToUserHasDefaults() {
		
	}
	
	
}
