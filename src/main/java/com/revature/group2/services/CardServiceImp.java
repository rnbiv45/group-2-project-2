package com.revature.group2.services;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardPrimaryKey;
import com.revature.group2.beans.User;
import com.revature.group2.repos.CardRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CardServiceImp implements CardService {
	private Random random = new Random();
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
	public Mono<Card> collectCard(UUID cardUuid) {
		// TODO add card to logged in player
		//return cardRepo.findByUuid(cardUuid);
		return Mono.empty();
	}
}
