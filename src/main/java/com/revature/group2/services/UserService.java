package com.revature.group2.services;

import com.revature.group2.beans.User;

import reactor.core.publisher.Mono;

import java.util.List;

import com.revature.group2.beans.User;

public interface UserService {
	
	// THE PARAMETERS ARE GUESSES OF WHAT YOU NEED, CHANGE THEM IF NEEDED!!
	
	//-As a user, I can register an account.
	void createUser();
	
	//-As a user, I can login to an account.
	User getUser(String name, String pass);
	
	//-As a user, I can log out from an account. ***most likely don't need services
	
	//-As a user, I can view all users in the system who have cards.
	List<User> getUsersWithCards();
	List<User> getUsers(); //feel like we need to view everyone as well, probably?
	
	
	//-As a user, I can interact with a user for a trade request.
	void requestTrade(User user);
	//-As a user, I can accept or deny the interaction with a user who started the trade request.
	void acceptInteraction();
	//-As a user, I can accept or deny the trade form by another user.
	void acceptTrade();
	void denyTrade();
	
	
	/* ADMIN SECTION */
	
	//-As an Admin, I can remove (ban) a user from the system.
	//--As a Mod, I can remove (ban) a user from the system.
	void removeUser(User u);
	
	
	//-As an Admin, I can change role of user to from a user to a mod, vice versa.
	void changeRole(User user);
}
