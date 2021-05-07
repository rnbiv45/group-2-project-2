package com.revature.group2.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardPrimaryKey;
import com.revature.group2.beans.Type;
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
	public void addDummyCard() {
		Card myCard = new Card();
		CardPrimaryKey myKey = new CardPrimaryKey();
//		myKey.setArchetype(Archetype.FIRE);
//		myKey.setBanned(false);
//		//myKey.setEffects(null);
//		myKey.setUuid(UUID.randomUUID());
//		myKey.setType(Type.MONSTER);
//		myKey.setRarity(5);
//		myCard.setCardPrimaryKey(myKey);
//		myCard.setUnique(false);
//		myCard.setAttackValue(5);
//		myCard.setDefenseValue(5);
//		myCard.setDamageValue(0);
//		myCard.setName("DummyCard");
		System.out.println(myCard);
		cardService.addCardToSystem(myCard);
		System.out.println("999999");
	}
	
	//get all cards
	@GetMapping
	public Flux<Card> getAllCards(){
		return cardService.getCardsFromSystem();
	}
	
	//add a card
	public void addCard(@RequestBody Card card) {
		cardService.addCardToSystem(card);
	}
	
	@GetMapping(path="/new/{cardId}")
	public Mono<Card> collectCard(@PathVariable UUID cardUuid) {
		return cardService.collectCard(cardUuid);
	}
}
