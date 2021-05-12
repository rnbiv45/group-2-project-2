package com.revature.group2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.Deck;
import com.revature.group2.beans.User;
import com.revature.group2.repos.DeckRepo;

import reactor.core.publisher.Mono;

@Service
public class DeckServiceImp implements DeckService {

	private DeckRepo deckRepo;
	
	@Autowired
	public void setDeckRepo(DeckRepo deckRepo) {
		this.deckRepo = deckRepo;
	}
	
	@Override
	public void createDeck() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Deck getUserDecks(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeDeck(User user, Deck deck) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCardToDeck(User user, Deck deck, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCardFromDeck(User user, Deck deck, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mono<User> addDeckToUser(User user) {
		Mono<Deck> deck = Mono.just(new Deck());
		// TODO
		return null;
	}

}
