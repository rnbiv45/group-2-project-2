package com.revature.group2.services;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardKey;
import com.revature.group2.beans.CardType;
import com.revature.group2.beans.User;
import com.revature.group2.repos.CardRepo;
import com.revature.group2.repos.UserRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CardServiceImp implements CardService {
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
	public Mono<Card> getCard(CardKey key){
		return cardRepo.findById(key);
	}

	@Override
	public Flux<Card> getCardsFromUser(User user) {
		return cardRepo.findAll().filter(c -> {
			for (String result : user.getCards().keySet()) {
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
			for (String result : user.getCards().keySet()) {
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

	
	

	public Mono<Card> addCardToSystem(Card card) {
		return cardRepo.save(card);

	}

//
//	public Mono<Card> collectCard(UUID cardUuid) {
//		// TODO add card to logged in player
//		return cardRepo.findByUuid(cardUuid);
////		return Mono.empty();
////		return cardRepo.insert(cardUuid);
//	}

	@Override
	public Mono<Void> removeCardFromSystem(Card card) {
		return cardRepo.delete(card);
	}

	@Override
	public Mono<Card> setCard(Card card) {
		return cardRepo.save(card);
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
	public Flux<Card> changeCardInSystemWithArguments(
			Optional<UUID> uuid,
			Optional<String> name,
			Optional<Boolean> isUnique,
			Optional<Integer> attackValue,
			Optional<Integer> defenseValue,
			Optional<Integer> damageValue,
			Optional<Integer> buffValue){
		return cardRepo.saveAll(cardRepo.findAll()
				.filter(c -> c.getKey().getUuid().equals(uuid.get()))
				.map(c -> {
			if (name.isPresent()) {
				c.setName(name.get());
			}
			if (isUnique.isPresent()) {
				c.setIsUnique(isUnique.get());
			}
			if (attackValue.isPresent()) {
				c.setAttackValue(attackValue.get());
			}
			if (defenseValue.isPresent()) {
				c.setDefenseValue(defenseValue.get());
			}
			if (damageValue.isPresent()) {
				c.setDamageValue(damageValue.get());
			}
			if (buffValue.isPresent()) {
				c.setBuffValue(buffValue.get());
			}

			return c;
		}));
	}

	@Override
	public Flux<Card> updateCard(Mono<Card> card) {
		return cardRepo.saveAll(card);
	}
}
