package com.revature.group2.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.User;
import com.revature.group2.repos.CardRepo;
import com.revature.group2.repos.UserRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CardServiceImp implements CardService {
	private Random random = new Random();
	private CardRepo cardRepo;
	private UserRepo userRepo;
	
	@Autowired
	public void setCardRepo(CardRepo cardRepo) {
		this.cardRepo = cardRepo;
	}
	@Autowired
	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public Flux<Card> getCardsFromSystem() {
		//return null;
		return cardRepo.findAll();
		//return Flux.empty();
	}

	@Override
	public Mono<Object> getCardsFromUser(User userF) {
//		Mono<User> user = userRepo.findById(userF.getName());
//		return user.map(u -> u.getCards());
		return null;
	}

	@Override
	public Flux<Card> getCardsMissingFromSystem(User user) {
//		Map<Card, Integer> userCards = new User().getCards();
//		Map<Card, Integer> systemCards = new HashMap<Card, Integer>();
//		for (Map.Entry<Card, Integer> userC : userCards.entrySet()) {
//			for (Map.Entry<Card, Integer> systemC : systemCards.entrySet()) {
//				if (userC.getValue() == systemC.getValue()) {
//					systemCards.remove(systemC.getKey());
//				}
//			}
//		}
		return null;
	}

	@Override
	public Flux<Card> getMetaCards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCardToSystem(Card card) {
		System.out.println(card);
		cardRepo.insert(card);
		System.out.println("9900");
		return;
		
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
	public Mono<Card> collectCard(UUID cardUuid) {
		// TODO add card to logged in player
		//return cardRepo.findByUuid(cardUuid);
		return Mono.empty();
	}
}
