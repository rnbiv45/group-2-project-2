package com.revature.group2.services;

import java.util.Set;

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
	private UserRepo userRepo;

	private UserService userService;
	

	@Autowired
	public void setDeckRepo(DeckRepo deckRepo) {
		this.deckRepo = deckRepo;
	}

	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	

	@Override
	public void createDeck() {
		// TODO Auto-generated method stub

	}

	@Override
	public Flux<Deck> getUserDecks(User user) {
//		Set<String> decks = user.getDecks();
//		return deckRepo.findAll().filter(c -> {
//			for (Deck d : decks)
//				if (d.equals(c)) {
//					return true;
//				}
//			return true;
//		});
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
	public Mono<User> addDeckToUser(
			User user, 
			Archetype primaryArchetype, 
			Archetype secondaryArchetype) {
		deckRepo.save(new Deck(user.getName(), primaryArchetype, secondaryArchetype))
				.subscribe(user::addDeck);
		userService.updateUser(user);
		return Mono.just(user);
	}

}
