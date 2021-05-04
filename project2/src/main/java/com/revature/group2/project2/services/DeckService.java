package com.revature.group2.project2.services;

import com.revature.group2.project2.beans.Card;
import com.revature.group2.project2.beans.Deck;
import com.revature.group2.project2.beans.User;

public interface DeckService {

	// THE PARAMETERS ARE GUESSES OF WHAT YOU NEED, CHANGE THEM IF NEEDED!!

	// -As a user, I can create a new deck.
	void createDeck();

	// -As a user, I can view cards in my decks.
	// -As a user, I can view other users’ decks.
	Deck getUserDecks(User user);

	// -As a user, I can delete a deck.
	void removeDeck(User user, Deck deck);

	// -As a user, I can add and remove cards from a deck.
	void addCardToDeck(User user, Deck deck, Card card);
	void removeCardFromDeck(User user, Deck deck, Card card);

}
