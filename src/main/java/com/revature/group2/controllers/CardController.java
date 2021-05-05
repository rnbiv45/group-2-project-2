package com.revature.group2.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardPrimaryKey;
import com.revature.group2.beans.Type;
import com.revature.group2.services.CardService;

public class CardController {
	CardService cardService;
	@Autowired
	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}
	
	public void addDummyCard() {
		Card myCard = new Card();
		CardPrimaryKey myKey = new CardPrimaryKey();
		myKey.setArchetype(Archetype.FIRE);
		myKey.setBanned(false);
		myKey.setEffects(null);
		myKey.setId(UUID.randomUUID());
		myKey.setType(Type.MONSTER);
		myKey.setRarity(5);
		myCard.setCardPrimaryKey(myKey);
		myCard.setUnique(false);
		myCard.setAttackValue(5);
		myCard.setDefenseValue(5);
		myCard.setDamageValue(0);
		myCard.setName("DummyCard");
		cardService.addCardToSystem(myCard);
	}

}
