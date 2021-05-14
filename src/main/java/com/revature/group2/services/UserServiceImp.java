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
		return userRepo.findById(username);
	}

	@Override
	public Mono<User> addUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public Flux<User> updateUser(Mono<User> user) {
		return userRepo.saveAll(user);
	}
	
	public Mono<User> addCardToUser(User user){
		return userRepo.findById(user.getName()).doOnSuccess(c ->{
			
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
		User resultUser = new User();
		
		userRepo.findById(name).subscribe(user -> {
			if(user != null && user.getPass().equals(password)){
				resultUser.setUuid(user.getUuid());
				resultUser.setName(user.getName());
				resultUser.setPass(user.getPass());
				resultUser.setRole(user.getRole());
				resultUser.setCards(user.getCards());
				resultUser.setDecks(user.getDecks());
			}
		});
		
		return  Mono.just(resultUser);
	}
	public Mono<User> addCardToUser(Mono<Card> card, Mono<User> user) {
		// TODO Auto-generated method stub
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
	public Flux<User> getAll() {
		return userRepo.findAll();
	}


}
