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
		//decks = decks.filter(deck -> deck.getKey().getUuid().)
		return null;
	}

	@Override
	public Flux<User> removeDeck(User user, Deck deck) {
		if(user.getDecks().remove(deck.getKey().getUuid().toString()));
		return userRepo.saveAll(Mono.just(user));
		
	}

	@Override
	public Flux<Deck> addCardToDeck(User user, Deck deck, Card card) {
		if(deck.getCards().containsKey(card.getKey().getUuid().toString())) {
			deck.getCards().replace(card.getKey().toString(), deck.getCards().get(card.getKey().toString())+1);
		}
		else {
			deck.getCards().put(card.getKey().toString(), 1);
		}
		return deckRepo.saveAll(Mono.just(deck));
	}

	@Override
	public Flux<Deck> removeCardFromDeck(User user, Deck deck, Card card) {
		if(deck.getCards().containsKey(card.getKey().getUuid().toString())) {
			if(deck.getCards().get(card.getKey().getUuid().toString()) > 1) {
				deck.getCards().replace(card.getKey().toString(), deck.getCards().get(card.getKey().toString())-1);
			} else {
				deck.getCards().remove(card.getKey().toString());
			}
		}
		return deckRepo.saveAll(Mono.just(deck));
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
