package com.revature.group2.services;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.revature.group2.beans.Trade;
import com.revature.group2.beans.User;
import com.revature.group2.repos.UserRepo;


import com.revature.group2.beans.Card;
import com.revature.group2.beans.Trade;
import com.revature.group2.beans.TradeStatus;
import com.revature.group2.beans.User;
import com.revature.group2.repos.TradeRepo;
import com.revature.group2.repos.UserRepo;

import reactor.core.publisher.Flux;

import reactor.core.publisher.Mono;

@Service
public class TradeServiceImp implements TradeService {

	private UserRepo userRepo;
	private TradeRepo tradeRepo;
	
	public Mono<User> requestTrade(User user){
		return userRepo.findByUuid(user.getUuid());
	}

	@Autowired
	public void setTradeRepo(TradeRepo tradeRepo) {
		this.tradeRepo = tradeRepo;
	}
	
	@Autowired
	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public Flux<Trade> viewTradesForCard(Card card) {
		return tradeRepo.findAll().filter(trade -> trade.getCard1().equals(card.getKey().getUuid().toString()));
	}

	@Override
	public Flux<Trade> viewTradesByUser(User user) {
		//get all trades  with user as poster
		Flux<Trade> postedTrades = tradeRepo.findAll().filter(trade -> trade.getPosterId().equals(user.getUuid()));
		//get all trades with user as acceptor
		Flux<Trade> acceptedTrades = tradeRepo.findAll().filter(trade -> trade.getAcceptorId() != null);
		acceptedTrades = acceptedTrades.filter(trade -> trade.getAcceptorId().equals(user.getUuid()));
		return Flux.merge(postedTrades, acceptedTrades);
		
	}
	
	@Override
	public Flux<Trade> viewPendingTrades(){
		return tradeRepo.findAll().filter(trade -> trade.getTradeStatus().equals(TradeStatus.PENDING));
		
	}

	@Override
	public Mono<Trade> submitTrade(Trade trade) {
		if(trade.getTradeId() == null) {
			trade.setTradeId(UUID.randomUUID());
		}
		if(trade.getCard1() == null || trade.getCard2() == null || trade.getPosterId() == null) {
			return null;
		}
		return tradeRepo.save(trade);
	}

	@Override
	public Mono<Trade> acceptTrade(UUID tradeId, User user) {
		Trade tradeInSystem = tradeRepo.findById(tradeId).block();
		if(tradeInSystem == null) {
			return Mono.empty();
		}
		User poster = userRepo.findByUuid(tradeInSystem.getPosterId()).block();
		if(poster == null) {
			return Mono.empty();
		}
		Map<String, Integer> cards1 = poster.getCards();
		Map<String, Integer> cards2 = user.getCards();
		//check if both parties have their cards
		if (cards1.containsKey(tradeInSystem.getCard1()) && cards2.containsKey(tradeInSystem.getCard2())){
			//remove poster's giving card to poster
			if(cards1.get(tradeInSystem.getCard1()) > 1) {
				cards1.replace(tradeInSystem.getCard1(), cards1.get(tradeInSystem.getCard1())-1);
			} else {
				cards1.remove(tradeInSystem.getCard1());
			}
			//add poster's recieving card to poster
			if(cards1.containsKey(tradeInSystem.getCard2())) {
				cards1.replace(tradeInSystem.getCard2(), cards1.get(tradeInSystem.getCard2())+1);
			} else {
				cards1.put(tradeInSystem.getCard2(), 1);
			}
			//remove posters recieving card from acceptor
			if(cards2.get(tradeInSystem.getCard2()) > 1) {
				cards2.replace(tradeInSystem.getCard2(), cards2.get(tradeInSystem.getCard2())-1);
			} else {
				cards2.remove(tradeInSystem.getCard2());
			}
			//add poster's giving card to acceptor
			if(cards2.containsKey(tradeInSystem.getCard1())) {
				cards2.replace(tradeInSystem.getCard1(), cards2.get(tradeInSystem.getCard1())+1);
			} else {
				cards2.put(tradeInSystem.getCard1(), 1);
			}
			user.setCards(cards2);
			poster.setCards(cards1);
			tradeInSystem.setAcceptor(user.getName());
			tradeInSystem.setTradeStatus(TradeStatus.ACCEPTED);
		}
		userRepo.save(user);
		userRepo.save(poster);
		return tradeRepo.save(tradeInSystem);

	}

	@Override
	public Mono<Trade> reverseTrade(UUID tradeId) {
		Trade tradeInSystem = tradeRepo.findById(tradeId).block();
		if(tradeInSystem == null) {
			return Mono.empty();
		}
		User poster = userRepo.findByUuid(tradeInSystem.getPosterId()).block();
		if(poster == null) {
			return Mono.empty();
		}
		User acceptor = userRepo.findByUuid(tradeInSystem.getAcceptorId()).block();
		if(acceptor == null) {
			return Mono.empty();
		}
		Map<String, Integer> cards1 = acceptor.getCards();
		Map<String, Integer> cards2 = poster.getCards();
		//remove poster's recieving card to poster
		if(cards1.get(tradeInSystem.getCard1()) > 1) {
			cards1.replace(tradeInSystem.getCard1(), cards1.get(tradeInSystem.getCard1())-1);
		} else {
			cards1.remove(tradeInSystem.getCard1());
		}
		//add poster's giving card to poster
		if(cards1.containsKey(tradeInSystem.getCard2())) {
			cards1.replace(tradeInSystem.getCard2(), cards1.get(tradeInSystem.getCard2())+1);
		} else {
			cards1.put(tradeInSystem.getCard2(), 1);
		}
		//remove posters giving card from acceptor
		if(cards2.get(tradeInSystem.getCard2()) > 1) {
			cards2.replace(tradeInSystem.getCard2(), cards2.get(tradeInSystem.getCard2())-1);
		} else {
			cards2.remove(tradeInSystem.getCard2());
		}
		//add poster's recieving card to acceptor
		if(cards2.containsKey(tradeInSystem.getCard1())) {
			cards2.replace(tradeInSystem.getCard1(), cards2.get(tradeInSystem.getCard1())+1);
		} else {
			cards2.put(tradeInSystem.getCard1(), 1);
		}
		acceptor.setCards(cards1);
		poster.setCards(cards2);
		tradeInSystem.setTradeStatus(TradeStatus.REVERSED);
		userRepo.save(acceptor);
		userRepo.save(poster);
		return tradeRepo.save(tradeInSystem);
	}

}
