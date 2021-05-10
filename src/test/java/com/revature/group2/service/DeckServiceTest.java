package com.revature.group2.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.group2.repos.DeckRepo;
import com.revature.group2.services.DeckService;
import com.revature.group2.services.DeckServiceImp;

@ExtendWith(SpringExtension.class)
public class DeckServiceTest {
	@TestConfiguration
	static class Configuration {
		@Bean
		public DeckService getDeckService(DeckRepo deckRepo) {
			DeckService deckService = new DeckServiceImp();
			deckService.setDeckRepo(deckRepo);
			return deckService;
		}

		@Bean
		public DeckRepo getService() {
			return Mockito.mock(DeckRepo.class);
		}
	}
	
	@Autowired
	DeckServiceTest deckService;
	
	@Autowired
	DeckRepo deckrepo;

}
