package com.revature.group2.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.group2.aspects.Authorized;
import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardPrimaryKey;
import com.revature.group2.beans.CardType;
import com.revature.group2.services.CardService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/cards")
public class CardController {
	CardService cardService;
	@Autowired
	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}
	
	//add a dummy card, is a test to make sure add works
	@PostMapping("/test")
	public Mono<Card> addDummyCard() {
		Card myCard = new Card();
		CardPrimaryKey myKey = new CardPrimaryKey();
		myKey.setArchetype(Archetype.FIRE);
		myKey.setIsBanned(false);
		//myKey.setEffects(null);
		myKey.setUuid(UUID.randomUUID());
		myKey.setType(CardType.MONSTER);
		myKey.setRarity(5);
		myCard.setCardPrimaryKey(myKey);
		myCard.setIsUnique(false);
		myCard.setAttackValue(5);
		myCard.setDefenseValue(5);
		myCard.setDamageValue(0);
		myCard.setName("DummyCard");
		myCard.setBuffValue(0);
		System.out.println(myCard);
		return cardService.addCardToSystem(myCard);
	}
	
	@PostMapping
	public void addCard(@RequestBody Card card) {
		cardService.addCardToSystem(card);
	}
	
	@Authorized
	@GetMapping
	public Flux<Card> getAllCards(
			@CookieValue("token") String string,
			@RequestParam Optional<String> type,
			@RequestParam Optional<String> archetype,
			@RequestParam Optional<Integer> rarity,
			@RequestParam Optional<Boolean> isBanned){
		return cardService.getCardsFromSystemWithArguments(type, archetype, rarity, isBanned);
	}
	
	@GetMapping(path="{name}")
	public Mono<Card> getCard(@PathVariable String name) {
		return cardService.getCardByName(name);
	}
	
	@GetMapping(path="/new/{name}")
	public Mono<Card> addCardToUser(@CookieValue(value = "token") String token, @PathVariable String name) {
		return cardService.addCardToUser(name);
	}
}
