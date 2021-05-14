package com.revature.group2.services;

import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.Deck;
import com.revature.group2.beans.User;
import com.revature.group2.repos.DeckRepo;
import com.revature.group2.repos.UserRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeckService {

	// THE PARAMETERS ARE GUESSES OF WHAT YOU NEED, CHANGE THEM IF NEEDED!!

	void setDeckRepo(DeckRepo deckRepo);
	void setUserRepo(UserRepo userRepo);
	// -As a user, I can create a new deck.
	Mono<Deck> createDeck(Deck deck);

	// -As a user, I can view cards in my decks.
	// -As a user, I can view other users’ decks.
	Flux<Deck> getUserDecks(User user);

	// -As a user, I can delete a deck.
	Mono<User> removeDeck(User user, Deck deck);

	// -As a user, I can add and remove cards from a deck.
	User addCardToDeck(User user, Deck deck, Card card);
	User removeCardFromDeck(User user, Deck deck, Card card);
	Mono<User> addDeckToUser(User user, Archetype primaryArchetype, Archetype secondaryArchetype);
	Flux<Deck> updateDeck(Mono<Deck> deck);

}
