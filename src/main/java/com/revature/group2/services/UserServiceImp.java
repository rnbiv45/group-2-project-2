package com.revature.group2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.User;
import com.revature.group2.repos.CardRepo;
import com.revature.group2.repos.UserRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImp implements UserService {
	Mono<User> resultsMono;
	Flux<User> resultsFlux;

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
			resultsMono = null;
			userRepo.findById(user.getName()).hasElement().doOnNext(result -> {
				if(!result) {
					resultsMono = userRepo.insert(user);
				} else {
					resultsMono = null;
				}
			}).subscribe();
			return resultsMono;
	}

	@Override
	public Mono<User> updateUser(User user) {
		resultsMono = null;
		userRepo.findById(user.getName()).hasElement().doOnNext(result -> {
			if(result) {
				resultsMono = userRepo.save(user);
			} else {
				resultsMono = null;
			}
		}).subscribe();
		return resultsMono;
	}

	@Override
	public Flux<User> getUsers() {
		return userRepo.findAll();
	}

	@Override
	public Mono<Card> collectCard() {
		return null;
	}
	


}
