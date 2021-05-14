package com.revature.group2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.Deck;
import com.revature.group2.beans.User;
import com.revature.group2.repos.DeckRepo;
import com.revature.group2.repos.UserRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeckServiceImp implements DeckService {

	private DeckRepo deckRepo;
	private UserService userService;
	private UserRepo userRepo;
	
	@Autowired
	public void setDeckRepo(DeckRepo deckRepo) {
		this.deckRepo = deckRepo;
	}
	@Autowired
	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public Mono<Deck> createDeck(Deck deck) {
		return deckRepo.insert(deck);
		
	}

	@Override
	public Flux<Deck> getUserDecks(User user) {
		Flux<Deck> decks = deckRepo.findAll();
		//decks = decks.filter(decks ->  == user.getName())
		return null;
	}

	@Override
	public Mono<User> removeDeck(User user, Deck deck) {
		if(user.getDecks().remove(deck.getKey().getUuid().toString()));
		return userRepo.save(user);
		
	}

	@Override
	public User addCardToDeck(User user, Deck deck, Card card) {
		user.getDecks().remove(deck);
		if(deck.getCards().containsKey(card)) {
			//deck.getCards().replace(card, deck.getCards().get(card)+1);
		}
		else {
			//deck.getCards().put(card, 1);
		}
		deckRepo.save(deck);
		//user.getDecks().add(deck);
		userRepo.save(user);
		return user;
	}

	@Override
	public User removeCardFromDeck(User user, Deck deck, Card card) {
		user.getDecks().remove(deck);
		if(deck.getCards().containsKey(card)) {
			if(deck.getCards().get(card) > 1) {
				//deck.getCards().replace(card, deck.getCards().get(card)-1);
			} else {
				//deck.getCards().remove(card);
			}
		}
		deckRepo.save(deck);
		//user.getDecks().add(deck);
		userRepo.save(user);
		return user;
	}

	@Override
	public Mono<User> addDeckToUser(
			User user, 
			Archetype primaryArchetype, 
			Archetype secondaryArchetype) {
		deckRepo.save(new Deck(user.getName(), primaryArchetype, secondaryArchetype))
				.subscribe(user::addDeck);
		userService.updateUser(Mono.just(user));
		return Mono.just(user);
	}

	@Override
	public Flux<Deck> updateDeck(Mono<Deck> deck) {
		return deckRepo.saveAll(deck);
	}

}
