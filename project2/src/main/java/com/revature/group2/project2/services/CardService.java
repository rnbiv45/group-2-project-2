package com.revature.group2.project2.services;

import java.util.List;

import com.revature.group2.project2.beans.Card;

public interface CardService {

	//-As a user, I can view all the cards available in the system.
	List<Card> getCardsFromSystem();
	
}
