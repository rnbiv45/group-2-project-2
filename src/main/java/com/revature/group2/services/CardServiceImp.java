package com.revature.group2.services;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.User;
import com.revature.group2.repos.CardRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CardServiceImp implements CardService {
	private Random random = new Random();
	private CardRepo cardRepo;
	
	@Autowired
	public void setCardRepo(CardRepo cardRepo) {
		this.cardRepo = cardRepo;
	}

	@Override
	public Flux<Card> getCardsFromSystem() {
		//return null;
		return cardRepo.findAll();
		//return Flux.empty();
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
}
