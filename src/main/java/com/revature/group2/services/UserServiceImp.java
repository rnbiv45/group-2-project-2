package com.revature.group2.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.Deck;
import com.revature.group2.beans.User;
import com.revature.group2.beans.UserRole;
import com.revature.group2.repos.UserRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImp implements UserService {
	private UserRepo userRepo;
	
	@Autowired
	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public Mono<User> getUser(String username) {
		return userRepo.findByName(username);
	}
	
	@Override
	public Mono<User> getUserByUUID(UUID uuid) {
		return userRepo.findByUuid(uuid);
	}

	@Override
	public Mono<User> addUser(User user) {
		System.out.println(user);
		return userRepo.save(user);
	}

	@Override
	public Flux<User> updateUser(Mono<User> user) {
		return userRepo.saveAll(user);
	}
	
	public Mono<User> addCardToUser(User user){
		return userRepo.findById(user.getUuid()).doOnSuccess(c ->{
			
		});
	}

	@Override
	public Flux<User> getUsers() {
		return userRepo.findAll();
	}

	@Override
	public Mono<Card> collectCard() {
		return null;
	}

	@Override
	public Mono<User> getUserByNameAndPass(String name, String password) {
		
		return userRepo.findByName(name).map(user -> {
			if (user != null && user.getName().equals(name) && user.getPass().equals(password)) {
				return user;
			} else {
				return new User();
			}
		});
//		return userRepo.findByName(name).map(user -> {
//			User resultUser = new User();
//			if (user != null && user.getPass().equals(password)) {
//				resultUser.setUuid(user.getUuid());
//				resultUser.setName(user.getName());
//				resultUser.setPass(user.getPass());
//				resultUser.setRole(user.getRole());
//				resultUser.setCards(user.getCards());
//				resultUser.setDecks(user.getDecks());
//				return resultUser;
//			}
//			return new User();
//		});
	}
	public Mono<User> addCardToUser(Mono<Card> card, Mono<User> user) {
		return null;
	}

	@Override
	public Mono<User> addDeckToUser(Mono<Deck> deck, Mono<User> user) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Flux<User> banUser(Optional<UUID> uuid) {
		return updateUser(userRepo.findByUuid(uuid.get()).map(u -> {
			u.setRole(UserRole.BANNED);
			return u;
		}));
	}

	@Override
	public Flux<User> getAll(Optional<UUID> card, Optional<UserRole> role) {
		Flux<User> users =  userRepo.findAll();
		if (role.isPresent()) {
			users = users.filter(user -> user.getRole().equals(role.get()));
		}
		if (card.isPresent()) {
			users = users.filter(user -> user.getCards().containsKey(card.get().toString()));
		}
		return users;
	}

}
