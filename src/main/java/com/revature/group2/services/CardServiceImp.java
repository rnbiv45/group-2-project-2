package com.revature.group2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.User;
import com.revature.group2.repos.CardRepo;

import reactor.core.publisher.Flux;

@Service
public class CardServiceImp implements CardService {
	private CardRepo cardRepo;
	
	@Autowired
	public void setCardRepo(CardRepo cardRepo) {
		this.cardRepo = cardRepo;
	}

	@Override
	public Flux<Card> getCardsFromSystem() {
		return Flux.empty();
		//return cardRepo.findAll();
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
	public void addCardToSystem(Card card) {
		//cardRepo.insert(card);
		return;
		
	}

	@Override
	public void removeCardFromSystem(Card card) {
		//cardRepo.delete(card);
		return;
		
	}

	@Override
	public void setCard(Card card) {
		//cardRepo.save(card);
		return;
		
	}

}
