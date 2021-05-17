package com.revature.group2.services;


import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return userRepo.findById(user.getUuid())
				.flatMapMany(u -> {
					return cardRepo.findAll()
							.filter(card -> 
							u.getCards().keySet().contains(card.getKey().getUuid().toString()));
				});
		
//		return cardRepo.findAll().filter(c -> {
//			for (String result : user.getCards().keySet()) {
//				if (c.equals(result)) {
//					return true;
//				}
//			}
//			return false;
//		});
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
		//int number = cardRepo.findAll().flatMap(c -> c.getAttackValue());
		return null;
	}

	@Override
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
	}

	@Override
	public Mono<User> addCardToUser(String name, User user) {//name of card, user to add
		
		return userRepo.findByName(user.getName())//get User by name
				.flatMap(u-> cardRepo.findByName(name)//get card by name
				.map(card -> {
							u.addCard(card); //add card to user
							return u;
							})
				.doOnNext(update -> {
					System.out.println(update);
					userRepo.save(update).subscribe();
				}));
//		cardRepo.findByName(name).doOnNext(card ->{//find card
//			user.addCard(card);//add card
//		});
//		return Mono.just(user);
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
	public Mono<Card> banCardFromSystem(String name) {
		
		Mono<Card> oldCard = cardRepo.findByName(name);
		
			oldCard.doOnNext(card -> {
				cardRepo.delete(card).subscribe();
			});
		
		Mono<Card> bannedCard = oldCard.map(card ->{
			Card newCard = new Card();
			CardKey cardKey = card.getKey();
			cardKey.setIsBanned(true);
			newCard.setKey(cardKey);
			newCard.setAttackValue(card.getAttackValue());
			newCard.setDamageValue(card.getDamageValue());
			newCard.setDefenseValue(card.getDefenseValue());
			newCard.setIsUnique(card.getIsUnique());
			newCard.setName(card.getName());
			addCardToSystem(newCard);
			
			return newCard;
		});
		
		return bannedCard;
		
//		Card card;
//		try {
//			card = cardRepo.findByName(name).toFuture().get();
//		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();
//			return null;
//		} catch (ExecutionException e) {
//			return null;
//		}
//		Card bannedCard = new Card();
//		CardKey cardKey = card.getKey();
//		cardKey.setIsBanned(true);
//		cardRepo.delete(card);
//		bannedCard.setKey(cardKey);
//		bannedCard.setAttackValue(card.getAttackValue());
//		bannedCard.setDamageValue(card.getDamageValue());
//		bannedCard.setDefenseValue(card.getDefenseValue());
//		bannedCard.setIsUnique(card.getIsUnique());
//		bannedCard.setName(card.getName());
//		addCardToSystem(bannedCard);
//		
//		return Mono.just(bannedCard);
	}
	
	public Flux<Card> updateCard(Mono<Card> card) {
		return cardRepo.saveAll(card);
	}
}
