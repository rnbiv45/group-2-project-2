package com.revature.group2.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.Deck;
import com.revature.group2.beans.User;
import com.revature.group2.repos.CardRepo;
import com.revature.group2.repos.DeckRepo;
import com.revature.group2.repos.UserRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeckServiceImp implements DeckService {

	private DeckRepo deckRepo;
	//private UserService userService;
	private UserRepo userRepo;
	private CardRepo cardRepo;
	
	@Autowired
	public void setCardRepo(CardRepo cardRepo) {
		this.cardRepo = cardRepo;
	}


	@Autowired
	public void setDeckRepo(DeckRepo deckRepo) {
		this.deckRepo = deckRepo;
	}

	@Autowired
	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	
	//@Autowired
	//public void setUserService(UserService userService) {
	//	this.userService = userService;
	//}
	

	public Mono<Deck> createDeck(Deck deck) {
		return deckRepo.insert(deck);
	}

	@Override
	public Flux<Deck> getUserDecks(User user) {
		Flux<Deck> decks = deckRepo.findAll();
		return decks.filter(deck -> user.getDecks().contains(deck.getKey().getUuid().toString()));
	}
	
	@Override
	public Flux<User> removeDeck(User user, Deck deck) {
		if(user.getDecks().remove(deck.getKey().getUuid().toString()));
		return userRepo.saveAll(Mono.just(user));
	}



	@Override
	public Mono<Deck> addCardToDeck(User user, String deckUuid, String cardUuid) {// user to save to, uuid of deck // uuid of card
		
//		return userRepo.findByName(user.getName())//get User by name
//				.flatMap(u-> cardRepo.findByName(name)//get card by name
//				.map(card -> {
//							u.addCard(card); //add card to user
//							return u;
//							})
//				.doOnNext(update -> {
//					System.out.println(update);
//					userRepo.save(update).subscribe();
//				}));
		
		return userRepo.findById(user.getUuid())//getUser by name
				.flatMap(u -> {	
					return (Mono<Deck>) cardRepo.findByKeyUuid(UUID.fromString(cardUuid)) //find card
					.flatMap(card -> { 
						return deckRepo.findByKeyUuid(UUID.fromString(deckUuid))
								.flatMap(deck -> {
									u.removeDeck(deck);
									deck.addCard(card);
									u.addDeck(deck);
									return deckRepo.save(deck);
											});
								});
		});
		
//		Flux<Deck> newDeck = deckRepo.saveAll(Mono.just(deck));
//		System.out.println(newDeck);
//		return newDeck;
	}

	@Override
	public Flux<Deck> removeCardFromDeck(User user, Deck deck, Card card) {
		if(deck.getCards().containsKey(card.getKey().getUuid().toString())) {
			if(deck.getCards().get(card.getKey().getUuid().toString()) > 1) {
				deck.getCards().replace(card.getKey().getUuid().toString(), deck.getCards().get(card.getKey().getUuid().toString())-1);
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
				
		return userRepo.findByName(user.getName())
				.flatMap(filteredUser ->{
					return deckRepo.save(new Deck(user.getName(), primaryArchetype, secondaryArchetype))
							.flatMap(deck -> {
								filteredUser.addDeck(deck);
								return userRepo.save(filteredUser);
						});
			});

//		deckRepo.save(new Deck(user.getName(), primaryArchetype, secondaryArchetype))
//				.doOnNext(deck -> {
//					user.addDeck(deck);
//		});
//		userRepo.saveAll(Mono.just(user));
//		return Mono.just(user);

	}

	@Override
	public Flux<Deck> updateDeck(Mono<Deck> deck) {
		return deckRepo.saveAll(deck);
	}


	@Override
	public Flux<Deck> getAll() {
		return deckRepo.findAll();
	}
}
