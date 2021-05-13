package com.revature.group2.services;

import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.Deck;
import com.revature.group2.beans.User;
import com.revature.group2.repos.DeckRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeckService {

	// THE PARAMETERS ARE GUESSES OF WHAT YOU NEED, CHANGE THEM IF NEEDED!!

	void setDeckRepo(DeckRepo deckRepo);
	// -As a user, I can create a new deck.
	void createDeck();

	// -As a user, I can view cards in my decks.
	// -As a user, I can view other users’ decks.
	Flux<Deck> getUserDecks(User user);

	// -As a user, I can delete a deck.
	void removeDeck(User user, Deck deck);

	// -As a user, I can add and remove cards from a deck.
	void addCardToDeck(User user, Deck deck, Card card);
	void removeCardFromDeck(User user, Deck deck, Card card);
	Mono<User> addDeckToUser(User user, Archetype primaryArchetype, Archetype secondaryArchetype);

}
