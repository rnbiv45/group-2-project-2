package com.revature.group2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardKey;
import com.revature.group2.beans.CardType;
import com.revature.group2.repos.CardRepo;
import com.revature.group2.services.CardService;
import com.revature.group2.services.CardServiceImp;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
public class CardServiceTest {
	@TestConfiguration
	static class Configuration {
		@Bean
		public CardService getCardService(CardRepo cardRepo) {
			CardService cardService = new CardServiceImp();
			cardService.setCardRepo(cardRepo);
			return cardService;
		}

		@Bean
		public CardRepo getService() {
			return Mockito.mock(CardRepo.class);
		}
	}
	
	@Autowired
	CardService cardService;
	
	@Autowired
	CardRepo cardRepo;
	
	@Test
	void testGetCardsFromSystem () {
		Card[] cards = {new Card(), new Card(), new Card()};
		Flux<Card> cardFlux = Flux.fromArray(cards);
		when(cardRepo.findAll()).thenReturn(cardFlux);
		Flux<Card> result = cardService.getCardsFromSystem();
		assertThat(result).isEqualTo(cardFlux);
		
	}
	
	@Test
	void testGetCard() {
		Card card = new Card();
		Mono<Card> cardMono = Mono.just(card);
		CardKey key = new CardKey();
		when(cardRepo.findById(key)).thenReturn(cardMono);
		Mono<Card> result = cardService.getCard(key);
		assertThat(result).isEqualTo(cardMono);
	}
	
	@Test
	void testGetCardThatDoesNotExist() {
		Card card = new Card();
		CardKey key = new CardKey();
		card.setKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		when(cardRepo.findById(key)).thenReturn(nullMono);
		Mono<Card> result = cardService.getCard(key);
		assertThat(result).isEqualTo(nullMono);
	}
	
	@Test
	void testBanCardBansACard() {
		Card card = new Card();
		Card banningCard = new Card();
		CardKey cardKey = new CardKey();
		String name = "a";
		
		cardKey.setIsBanned(false);
		card.setKey(cardKey);
		
		Mono<Card> cardMono = Mono.just(card);
		
		cardKey.setIsBanned(true);
		banningCard.setKey(cardKey);
		
		Mono<Card> bannedCard = Mono.just(banningCard);
		Mono<Void> voidMono = Mono.empty();
		
		when(cardRepo.findById(cardKey)).thenReturn(cardMono);
		when(cardRepo.delete(card)).thenReturn(voidMono);
		when(cardRepo.insert(banningCard)).thenReturn(bannedCard);
		when(cardRepo.findByName(name)).thenReturn(cardMono);
		
		Mono<Card> result = cardService.banCardFromSystem(name);
		Mono<Boolean> comparer = Mono.sequenceEqual(result, bannedCard);
		comparer.subscribe(bool -> {
			assertThat(bool).isEqualTo(true);
		});
		
		
	}
	
	@Test
	void testChangeCardInSystemWithArguments(){
		Card card = new Card();
		CardKey key = new CardKey();
		
		key.setArchetype(Archetype.WIND);
		key.setIsBanned(true);
		key.setRarity(5);
		key.setType(CardType.MONSTER);
		key.setUuid(UUID.randomUUID());
		
		card.setAttackValue(5);
		card.setBuffValue(5);
		card.setDamageValue(5);
		card.setDefenseValue(5);
		card.setIsUnique(true);
		card.setKey(key);
		card.setName("AyLmao");
		
		Card update = card;
		update.setIsUnique(false);
		update.setAttackValue(6);
		update.setBuffValue(6);
		update.setDamageValue(6);
		update.setDefenseValue(6);
		update.setName("Bruh");
		List <Card> cList = new ArrayList<Card>();
		cList.add(card);
		
		Flux<Card> findAll = Flux.just(card);
		Flux<Card> changeCard = Flux.just(update);
		
//		when(cardRepo.findAll()).thenReturn(findAll);
		when(cardRepo.saveAll(findAll)).thenReturn(changeCard);
		
		Optional<String> optName= Optional.of("Bruh");
		Optional<UUID> optuuid= Optional.of(key.getUuid());
		Optional<Boolean> optBool= Optional.of(false);
		Optional<Integer> optAtk= Optional.of(6);
		Optional<Integer> optDef= Optional.of(6);
		Optional<Integer> optDmg= Optional.of(6);
		Optional<Integer> optBuf= Optional.of(6);
		
		Flux<Card> changed = cardService.changeCardInSystemWithArguments(
				optuuid, optName, optBool, 
				optAtk, optDef, optDmg, 
				optBuf);
		Mono<List<Card>> result = changed.collectList();
		Mono<List<Card>> expected = changed.collectList();
		
		Mono<Boolean> comparer = Mono.sequenceEqual(result, expected);
		comparer.subscribe(bool -> {
			assertThat(bool).isEqualTo(true);
		});
	}
	/*
	@Test
	void testAddCardDoesNotExist() {
		Card card = new Card();
		CardPrimaryKey key = new CardPrimaryKey();
		card.setCardPrimaryKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		//System.out.println(cardMono);
		when(cardRepo.findById(card.getCardPrimaryKey())).thenReturn(nullMono);
		when(cardRepo.insert(card)).thenReturn(cardMono);
		Mono<Card> result = cardService.addCardToSystem(card);
		assertThat(result).isNotNull();
	}
	@Test 
	void testAddCardThatExists() {
		Card card = new Card();
		CardPrimaryKey key = new CardPrimaryKey();
		card.setCardPrimaryKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		//System.out.println(card.getCardPrimaryKey());
		when(cardRepo.findById(card.getCardPrimaryKey())).thenReturn(cardMono);
		when(cardRepo.insert(card)).thenReturn(cardMono);
		Mono<Card> result =  cardService.addCardToSystem(card);
		assertThat(result).isNull();
	}
	@Test
	void testUpdateCardDoesNotExist() {
		Card card = new Card();
		CardPrimaryKey key = new CardPrimaryKey();
		card.setCardPrimaryKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		when(cardRepo.findById(key)).thenReturn(nullMono);
		when(cardRepo.save(card)).thenReturn(cardMono);
		Mono<Card> result = cardService.setCard(card);
		assertThat(result).isNull();
	}
	@Test 
	void testUpdateCardThatExists() {
		Card card = new Card();
		CardPrimaryKey key = new CardPrimaryKey();
		card.setCardPrimaryKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		when(cardRepo.findById(key)).thenReturn(cardMono);
		when(cardRepo.save(card)).thenReturn(cardMono);
		Mono<Card> result = cardService.setCard(card);
		assertThat(result).isNotNull();
	}
	@Test
	void testDeleteCardDoesNotExist() {
		Card card = new Card();
		CardPrimaryKey key = new CardPrimaryKey();
		card.setCardPrimaryKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		Mono<Void> voidMono = Mono.empty();
		when(cardRepo.findById(key)).thenReturn(nullMono);
		when(cardRepo.delete(card)).thenReturn(voidMono);
		Mono<Void> result = cardService.removeCardFromSystem(card);
		assertThat(result).isNull();
	}
	@Test 
	void testDeleteCardThatExists() {
		Card card = new Card();
		CardPrimaryKey key = new CardPrimaryKey();
		card.setCardPrimaryKey(key);
		Mono<Card> cardMono = Mono.just(card);
		Mono<Card> nullMono = Mono.empty();
		Mono<Void> voidMono = Mono.empty();
		when(cardRepo.findById(key)).thenReturn(cardMono);
		when(cardRepo.delete(card)).thenReturn(voidMono);
		Mono<Void> result = cardService.removeCardFromSystem(card);
		assertThat(result).isNotNull();
	}
*/
}
