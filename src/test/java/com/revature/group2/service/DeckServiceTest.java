package com.revature.group2.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.Deck;
import com.revature.group2.beans.User;
import com.revature.group2.repos.DeckRepo;
import com.revature.group2.repos.UserRepo;
import com.revature.group2.services.DeckService;
import com.revature.group2.services.DeckServiceImp;

@ExtendWith(SpringExtension.class)
public class DeckServiceTest {
	@TestConfiguration
	static class Configuration {
		@Bean
		public DeckService getDeckService(DeckRepo deckRepo, UserRepo userRepo) {
			DeckService deckService = new DeckServiceImp();
			deckService.setDeckRepo(deckRepo);
			deckService.setUserRepo(userRepo);
			return deckService;
		}

		@Bean
		public DeckRepo getDeckRepo() {
			return Mockito.mock(DeckRepo.class);
		}
		
		@Bean
		public UserRepo getUserRepo() {
			return Mockito.mock(UserRepo.class);
		}
	}
	
	@Autowired
	DeckService deckService;
	
	@Autowired
	DeckRepo deckRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Test
	void testAddCardThatExists() {
		User user = new User();
		Deck deck = new Deck();
		Deck deck2 = new Deck();
		Card card = new Card();
		Set<Deck> decks = new HashSet<Deck>();
		deck.setCards(new HashMap<Card, Integer>());
		deck.getCards().put(card, 1);
		deck2.setCards(new HashMap<Card, Integer>());
		deck2.getCards().put(card, 2);
		decks.add(deck);
		User result = deckService.addCardToDeck(user, deck, card);
		assertTrue(result.getDecks().contains(deck2));
	}
	@Test
	void testAddCardThatDoesnNotExists() {
		User user = new User();
		Deck deck = new Deck();
		Deck deck2 = new Deck();
		Card card = new Card();
		Set<Deck> decks = new HashSet<Deck>();
		deck.setCards(new HashMap<Card, Integer>());
		deck2.setCards(new HashMap<Card, Integer>());
		deck2.getCards().put(card, 1);
		decks.add(deck);
		User result = deckService.addCardToDeck(user, deck, card);
		assertTrue(result.getDecks().contains(deck2));
	}
	@Test
	void testRemoveCardWithOneCopy() {
		User user = new User();
		Deck deck = new Deck();
		Deck deck2 = new Deck();
		Card card = new Card();
		Set<Deck> decks = new HashSet<Deck>();
		deck.setCards(new HashMap<Card, Integer>());
		deck.getCards().put(card, 1);
		deck2.setCards(new HashMap<Card, Integer>());
		decks.add(deck);
		User result = deckService.removeCardFromDeck(user, deck, card);
		assertTrue(result.getDecks().contains(deck2));
	}
	@Test
	void testRemoveCardWith2Copies() {
		User user = new User();
		Deck deck = new Deck();
		Deck deck2 = new Deck();
		Card card = new Card();
		Set<Deck> decks = new HashSet<Deck>();
		deck.setCards(new HashMap<Card, Integer>());
		deck.getCards().put(card, 2);
		deck2.setCards(new HashMap<Card, Integer>());
		deck2.getCards().put(card, 1);
		decks.add(deck);
		User result = deckService.removeCardFromDeck(user, deck, card);
		assertTrue(result.getDecks().contains(deck2));
	}
	@Test
	void testremoveCardThatDoesNotExist(){
		User user = new User();
		Deck deck = new Deck();
		Deck deck2 = new Deck();
		Card card = new Card();
		Set<Deck> decks = new HashSet<Deck>();
		deck.setCards(new HashMap<Card, Integer>());
		deck2.setCards(new HashMap<Card, Integer>());
		decks.add(deck);
		User result = deckService.removeCardFromDeck(user, deck, card);
		assertTrue(result.getDecks().contains(deck2));
	}
	

}
