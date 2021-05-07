package com.revature.group2.controllers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.group2.services.DeckService;

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
}
