package com.revature.group2.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.group2.controllers.CardController;
import com.revature.group2.repos.CardRepo;
import com.revature.group2.services.CardService;
import com.revature.group2.services.CardServiceImp;

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

}
