package com.revature.group2.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardKey;
import com.revature.group2.beans.CardType;
import com.revature.group2.beans.User;
import com.revature.group2.repos.CardRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CardServiceImp implements CardService {
	private CardRepo cardRepo;
	Mono<Card> resultMono;
	Mono<Void> deleteMono;
	
	@Autowired
	public void setCardRepo(CardRepo cardRepo) {
		this.cardRepo = cardRepo;
	}

	@Override
	public Flux<Card> getCardsFromSystem() {
		return cardRepo.findAll();
	}
	
	@Override
	public Mono<Card> getCard(CardKey key){
		return cardRepo.findById(key);
	}

	@Override
	public Flux<Card> getCardsFromUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Card> getCardsMissingFromSystem(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Card> getMetaCards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override

	public Mono<Card> addCardToSystem(Card card) {
		return cardRepo.insert(card);
		/*
		resultMono = null;
		System.out.println(card.getCardPrimaryKey());
		Mono<Card> cardResult = cardRepo.findById(card.getCardPrimaryKey());
		System.out.println(cardResult);
		cardRepo.findById(card.getCardPrimaryKey()).hasElement().doOnNext(result -> {
			System.out.println("0");
			if(!result) {
				System.out.println("5");
				resultMono = cardRepo.insert(card);
			} else {
				System.out.println("6");
				resultMono = null;
			}
		});
		return resultMono;
		*/
		
	}

	@Override
	public Mono<Void> removeCardFromSystem(Card card) {
		return deleteMono = cardRepo.delete(card);
		/*
		resultMono = null;
		cardRepo.findById(card.getCardPrimaryKey()).hasElement().doOnNext(result -> {
			if(result) {
				deleteMono = cardRepo.delete(card);
			} else {
				deleteMono = null;
			}
		});
		return deleteMono;
		*/
	}

	@Override
	public Mono<Card> setCard(Card card) {
		return cardRepo.save(card);
		/*
		resultMono = null;
		cardRepo.findById(card.getCardPrimaryKey()).hasElement().doOnNext(result -> {
			if(result) {
				resultMono = cardRepo.save(card);
			} else {
				resultMono = null;
			}
		});
		return resultMono;
		*/
		
	}

	@Override
	public Mono<User> addCardToUser(String name, User user) {
		cardRepo.findByName(name).subscribe(user::addCard);
		return Mono.just(user);
	}

	@Override
	public Mono<Card> getCardByName(String name) {
		return cardRepo.findByName(name);
	}
	
	@Override
	public Flux<Card> getCardsFromSystemWithArguments(
			Optional<String> type, 
			Optional<String> archetype, 
			Optional<Integer> rarity,
			Optional<Boolean> isBanned) {
		Flux<Card> cards = cardRepo.findAll();
		if (isBanned.isPresent()) {
			cards = cards.filter(card -> card.getKey().getIsBanned().equals(isBanned.get()));
		}
		if (type.isPresent()) {
			cards = cards.filter(card -> card.getKey().getType().equals(CardType.valueOf(type.get())));
		}
		if (archetype.isPresent()) {
			cards = cards.filter(card -> card.getKey().getArchetype().equals(Archetype.valueOf(archetype.get())));
		}
		if (rarity.isPresent()) {
			cards = cards.filter(card -> card.getKey().getRarity().equals(rarity.get()));
		}
		return cards;
	}

	@Override
	public Flux<Card> updateCard(Mono<Card> card) {
		return cardRepo.saveAll(card);
	}
}
