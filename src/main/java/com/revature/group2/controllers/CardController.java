package com.revature.group2.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.tinkerpop.shaded.minlog.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.datastax.oss.driver.api.querybuilder.update.Update;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.group2.aspects.Admin;
import com.revature.group2.aspects.Authorized;
import com.revature.group2.aspects.OwnerAndAdmin;
import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardKey;
import com.revature.group2.beans.CardType;
import com.revature.group2.beans.User;
import com.revature.group2.services.CardService;
import com.revature.group2.utils.JWTParser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class CardController {
	private CardService cardService;
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
		CardKey myKey = new CardKey();
		myKey.setArchetype(Archetype.FIRE);
		myKey.setIsBanned(false);
		//myKey.setEffects(null);
		myKey.setUuid(UUID.randomUUID());
		myKey.setType(CardType.MONSTER);
		myKey.setRarity(5);
		myCard.setKey(myKey);
		myCard.setIsUnique(false);
		myCard.setAttackValue(5);
		myCard.setDefenseValue(5);
		myCard.setDamageValue(0);
		myCard.setName("DummyCard");
		myCard.setBuffValue(0);

		System.out.println(myCard);
		return cardService.addCardToSystem(myCard);
	}
	

	@Authorized
	@Admin
	@GetMapping(path="/cards")
	public Flux<Card> getAllCards(
			ServerWebExchange exchange,
			@RequestParam Optional<String> type,
			@RequestParam Optional<String> archetype,
			@RequestParam Optional<Integer> rarity,
			@RequestParam Optional<Boolean> isBanned){
		return cardService.getCardsFromSystemWithArguments(type, archetype, rarity, isBanned);
	}

	
	@PostMapping(path="/users/{user}/cards")
	public Flux<User> addCardToUser(@CookieValue(value="token") String token, ServerWebExchange exchange, @PathVariable UUID uuid) {
		
		return null;
	}

	@Authorized
	@Admin
	@PutMapping(path="/cards")
	public Flux<Card> changeStat(
			ServerWebExchange exchange,
			@RequestParam Optional<UUID> uuid,
			@RequestParam Optional<String> name,
			@RequestParam Optional<Boolean> isUnique,
			@RequestParam Optional<Integer> attackValue,
			@RequestParam Optional<Integer> defenseValue,
			@RequestParam Optional<Integer> damageValue,
			@RequestParam Optional<Integer> buffValue){
		return cardService.changeCardInSystemWithArguments(uuid, name, isUnique, attackValue, defenseValue, damageValue, buffValue);
	}


	@Authorized	
	@OwnerAndAdmin
	@GetMapping(value="/users/{pathUser}/cards")
	public Map<String, Integer> getUserCards(ServerWebExchange exchange, @PathVariable String pathUser){
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
	@Authorized
	@Admin
	@PostMapping(path="/cards")
	public Mono<ResponseEntity<Card>> addCard(ServerWebExchange exchange, @RequestBody Card card) {
		System.out.println(card);
		cardService.addCardToSystem(card);
		return cardService.addCardToSystem(card).map(returnCard -> ResponseEntity.status(201).body(returnCard))
				.onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body(null)));
	}
	
	@Authorized
	@GetMapping(path="/cards/{name}")
	public Mono<ResponseEntity<Card>> getCard(ServerWebExchange exchange, @PathVariable String name) {
		return cardService.getCardByName(name).map(card ->{
			return ResponseEntity.ok().body(card);
		});
	}
	
	//Add Card to User
	@Authorized
	@GetMapping(path="/cards/new/{name}")
	public Mono<ResponseEntity<Object>> addCardToUser(
			ServerWebExchange exchange, //exchange for authorization
			@CookieValue(value="token") String token, //cookie to parse user value to get cards
			@PathVariable String name) { //path variable to get card name
		
		try {
			User user = tokenService.parser(token);// get user from token
			System.out.println(user);
			return cardService.addCardToUser(name.replace("_", " "), user)
					.doOnNext(update -> {
						try {
							exchange.getResponse().addCookie(ResponseCookie
									.from("token", tokenService.makeToken(update))
									.httpOnly(true).path("/").build());
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}})
					.map(card -> ResponseEntity.status(201).body(card));
			
			//return cardService.addCardToUser(name, user).map(card -> ResponseEntity.status(201).body(card));
		} catch (Exception e) {
			return Mono.just(ResponseEntity.status(500).body(e));//if there is a problem
		}
	}
	
	@Authorized
	@Admin
	@DeleteMapping(path = "/cards/{name}")
	public Mono<ResponseEntity<Card>> banCard(ServerWebExchange exchange, @PathVariable String name){
		return cardService.banCardFromSystem(name).map(card ->  ResponseEntity.ok().body(card));
	}
	
	@Authorized
	@Admin
	@PutMapping(path="/{uuid}")
	public Flux<Card> updateCard(ServerWebExchange exchange, @RequestBody Card card, @PathVariable UUID uuid) {
		return cardService.updateCard(Mono.just(card));
	}
}
