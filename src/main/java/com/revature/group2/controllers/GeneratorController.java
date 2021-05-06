package com.revature.group2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.Deck;
import com.revature.group2.beans.Trade;
import com.revature.group2.beans.User;
import com.revature.group2.services.CardService;
import com.revature.group2.services.DeckService;
import com.revature.group2.services.TradeService;
import com.revature.group2.services.UserService;

@RestController
@RequestMapping(path="/generators")
public class GeneratorController {
	private CardService cardService;
	private UserService userService;
	private DeckService deckService;
	private TradeService tradeService;
	
	@Autowired
	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setDeckService(DeckService deckService) {
		this.deckService = deckService;
	}
	
	@Autowired
	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	
	@PostMapping(path="/cards")
	public String generateCards(@RequestBody List<Card> cards) {
		for (Card card : cards) {
			cardService.addCardToSystem(card);
		}
		return "Cards generated";
	}
	
	@PostMapping(path="/users")
	public String generateUsers(@RequestBody List<User> users) {
		for (User user : users) {
			userService.addUser(user);
		}
		return "Users generated";
	}
	
	@PostMapping(path="/decks")
	public String generateDecks(@RequestBody List<Deck> decks) {
		for (Deck deck : decks) {
			// TODO deckService.addDeck(deck);
		}
		return "Decks generated";
	}
	
	@PostMapping(path="/trades")
	public String generateTrades(@RequestBody List<Trade> trades) {
		for (Trade trade : trades) {
			// TODO tradeService.addTrade(trade);
		}
		return "Trades generated";
	}
}
