package com.revature.group2.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.group2.services.DeckService;

public class DeckController {
	DeckService deckService;
	@Autowired
	public void setDeckService(DeckService deckService) {
		this.deckService = deckService;
	}
}
