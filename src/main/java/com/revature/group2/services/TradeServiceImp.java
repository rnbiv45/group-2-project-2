package com.revature.group2.services;

import org.springframework.stereotype.Service;

import com.revature.group2.beans.Trade;
import com.revature.group2.beans.User;
import com.revature.group2.repos.UserRepo;

import reactor.core.publisher.Mono;

@Service
public class TradeServiceImp implements TradeService {
	private UserRepo userRepo;
	
	
	
	public Mono<User> requestTrade(User user){
		return userRepo.findByUuid(user.getUuid()).
		
		return null;
	}

}
