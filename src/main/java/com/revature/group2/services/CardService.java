package com.revature.group2.services;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.User;

import reactor.core.publisher.Flux;

public interface CardService {
	
	// THE PARAMETERS ARE GUESSES OF WHAT YOU NEED, CHANGE THEM IF NEEDED!!

	//-As a user, I can view all the cards available in the system.
	Flux<Card> getCardsFromSystem();
	
	//-As a user, I can view all the cards I own
	Flux<Card> getCardsFromUser(User user); //this belongs here or in User service?
	
	//-As a user, I can view any missing cards that are in the system I don't have
	Flux<Card> getCardsMissingFromSystem(User user); //this belongs here or in User service?
	
	
	//-As a user, I can see the cards that are meta to the card game.
	Flux<Card> getMetaCards(); //find a better name if need be for this function
	
	
	/* ADMIN SECTION */
	
	//-As an Admin, I can add or remove a card in the database.
	void addCardToSystem(Card card);
	void removeCardFromSystem(Card card);
	
	//-As an Admin, I can change the functionality of the card’s stats, such as archetype, type, if it is unique, if it is banned.
	void setCard(Card card);
	
	
	
}
