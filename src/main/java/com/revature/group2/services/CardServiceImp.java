package com.revature.group2.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardPrimaryKey;
import com.revature.group2.beans.CardType;
import com.revature.group2.beans.User;
import com.revature.group2.repos.CardRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sun.net.www.protocol.http.AuthCacheValue.Type;

@Service
public class CardServiceImp implements CardService {
	private CardRepo cardRepo;
	
	@Autowired
	public void setCardRepo(CardRepo cardRepo) {
		this.cardRepo = cardRepo;
	}

	@Override
	public Flux<Card> getCardsFromSystem() {
		return cardRepo.findAll();
	}
	
	@Override
	public Mono<Card> getCard(CardPrimaryKey key){
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
		return cardRepo.save(card);
		
	}

	@Override
	public void removeCardFromSystem(Card card) {
		cardRepo.delete(card);
		return;
		
	}

	@Override
	public void setCard(Card card) {
		cardRepo.save(card);
		return;
		
	}

	@Override
	public Mono<Card> addCardToUser(String name) {
		// TODO add card to logged in player
		Mono<Card> card = cardRepo.findByName(name);
		///loggedInUser.addCard(card);
		return card;
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
			cards = cards.filter((card) -> {
				return card.getCardPrimaryKey().getIsBanned().equals(isBanned.get());
			});
		}
		
		if (type.isPresent()) {
			cards = cards.filter((card) -> {
				return card.getCardPrimaryKey().getType().equals(CardType.valueOf(type.get()));
			});
		}
		
		if (archetype.isPresent()) {
			cards = cards.filter((card) -> {
				return card.getCardPrimaryKey().getArchetype().equals(Archetype.valueOf(archetype.get()));
			});
		}
		
		if (rarity.isPresent()) {
			cards = cards.filter((card) -> {
				return card.getCardPrimaryKey().getRarity().equals(rarity.get());
			});
		}
		
		return cards;
	}
}
