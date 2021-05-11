package com.revature.group2.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardPrimaryKey;
import com.revature.group2.beans.User;
import com.revature.group2.beans.CardType;
import com.revature.group2.services.CardService;
import com.revature.group2.utils.JWTParser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/cards")
public class CardController {
	CardService cardService;
	private JWTParser tokenService;
	@Autowired
	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}
	@Autowired
	public void setTokenServicer(JWTParser parser) {
		this.tokenService = parser;
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
	
	@GetMapping
	public Flux<Card> getAllCards(
			@RequestParam Optional<String> type,
			@RequestParam Optional<String> archetype,
			@RequestParam Optional<Integer> rarity,
			@RequestParam Optional<Boolean> isBanned){
		return cardService.getCardsFromSystemWithArguments(type, archetype, rarity, isBanned);
	}
	
	@GetMapping
	public Map<Card, Integer> getUserCards(ServerWebExchange exchange){
		User user = null;
		try {
			if(exchange.getRequest().getCookies().get("token") != null) {
				String token = exchange.getRequest().getCookies().getFirst("token").getValue();
				if(!token.equals("")) {
					user = tokenService.parser(token);
					return user.getCards();
				}
			}
		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return null;
		}
		return null;
	}
	
	//add a card
	public Mono<ResponseEntity<Card>> addCard(@RequestBody Card card) {
		cardService.addCardToSystem(card);
		return cardService.addCardToSystem(card).map(returnCard -> ResponseEntity.status(201).body(returnCard))
				.onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body(null)));
	}
	@GetMapping(path="{name}")
	public Mono<Card> getCard(@PathVariable String name) {
		return cardService.getCardByName(name);
	}
	
	@GetMapping(path="/new/{name}")
	public Mono<Card> addCardToUser(@PathVariable String name) {
		return cardService.addCardToUser(name);
	}
}
