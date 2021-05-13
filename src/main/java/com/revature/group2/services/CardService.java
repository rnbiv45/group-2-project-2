package com.revature.group2.services;

import java.util.Optional;
import java.util.UUID;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardKey;
import com.revature.group2.beans.User;
import com.revature.group2.repos.CardRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CardService {
	
	// THE PARAMETERS ARE GUESSES OF WHAT YOU NEED, CHANGE THEM IF NEEDED!!

	//set card repo, for testing
	public void setCardRepo(CardRepo cardRepo);
	//-As a user, I can view all the cards available in the system.
	Flux<Card> getCardsFromSystem();
	
	//-As a user, I can view all the cards I own
	Flux<Card> getCardsFromUser(User user); //this belongs here or in User service?
	
	//-As a user, I can view any missing cards that are in the system I don't have
	Flux<Card> getCardsMissingFromSystem(User user); //this belongs here or in User service?
	
	
	//-As a user, I can see the cards that are meta to the card game.
	Flux<Card> getMetaCards(); //find a better name if need be for this function
	
	Mono<Card> getCard(CardKey key);
	
	
	/* ADMIN SECTION */
	
	//-As an Admin, I can add or remove a card in the database.
	Mono<Card> addCardToSystem(Card card);
	Mono<Void> removeCardFromSystem(Card card);
	
	//-As an Admin, I can change the functionality of the card’s stats, such as archetype, type, if it is unique, if it is banned.
	Mono<Card> setCard(Card card);

	Mono<User> addCardToUser(String name, User user);

	Mono<Card> getCardByName(String name);
	
	public Flux<Card> getCardsFromSystemWithArguments(Optional<String> type, Optional<String> archetype,
			Optional<Integer> rarity, Optional<Boolean> isBanned);
	
	
	
}
