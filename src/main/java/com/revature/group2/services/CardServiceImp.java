package com.revature.group2.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardPrimaryKey;
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
		return cardRepo.findAll();
	}

	@Override
	public Mono<Card> getCard(CardPrimaryKey key) {
		return cardRepo.findById(key);
	}

	@Override
	public Flux<Card> getCardsFromUser(User user) {
		return cardRepo.findAll().filter(c -> {
			for (Card result : user.getCards().keySet()) {
				if (c.equals(result)) {
					return true;
				}
			}
			return false;
		});
	}

	@Override
	public Flux<Card> getCardsMissingFromSystem(User user) {
		return cardRepo.findAll().filter(c -> {
			for (Card result : user.getCards().keySet()) {
				if (c.equals(result)) {
					return false;
				}
			}
			return true;
		});
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
	public Mono<Card> collectCard(UUID cardUuid) {
		// TODO add card to logged in player
		// return cardRepo.findByUuid(cardUuid);
		return Mono.empty();
	}
}
