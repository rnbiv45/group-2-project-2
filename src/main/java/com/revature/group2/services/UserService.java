package com.revature.group2.services;

import java.util.Optional;
import java.util.UUID;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.Deck;
import com.revature.group2.beans.User;
import com.revature.group2.beans.UserRole;
import com.revature.group2.repos.UserRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
	Mono<User> getUser(String username);
	
	Mono<User> addUser(User user);
	
	Flux<User> updateUser(Mono<User> user);
	
	Flux<User> getUsers();
	
	//Flux<User> getUserByAdmin();
	
	Mono<Card> collectCard();

	void setUserRepo(UserRepo userRepo);

	Mono<User> getUserByNameAndPass(String name, String password);
	
	Mono<User> addCardToUser(Mono<Card> card, Mono<User> user);
	
	Mono<User> addDeckToUser(Mono<Deck> deck, Mono<User> user);

	Flux<User> banUser(Optional<UUID> uuid);

	Flux<User> getAll(Optional<UUID> card, Optional<UserRole> role);

	Mono<User> getUserByUUID(UUID uuid);
}
