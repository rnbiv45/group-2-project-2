package com.revature.group2.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	TradeRepo tradeRepo;
	UserRepo userRepo;
	
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
		Flux<Trade> postedTrades = tradeRepo.findAll().filter(trade -> trade.getPoster().equals(user.getName()));
		//get all trades with user as acceptor
		Flux<Trade> Acceptedtrades = tradeRepo.findAll().filter(trade -> trade.getAcceptor().equals(user.getName()));
		//concat the fluxes
		Flux<Trade> trades = Flux.merge(postedTrades, Acceptedtrades);
		return trades;
		
	}

	@Override
	public Mono<Trade> submitTrade(Trade trade) {
		return tradeRepo.save(trade);
	}

	@Override
	public Mono<Trade> acceptTrade(Trade trade, User user) {
		User poster = userRepo.findById(trade.getPoster()).block();
		Map<String, Integer> cards1 = poster.getCards();
		Map<String, Integer> cards2 = user.getCards();
		//check if both parties have their cards
		if (cards1.containsKey(trade.getCard1()) && cards2.containsKey(trade.getCard2())){
			//remove poster's giving card to poster
			if(cards1.get(trade.getCard1()) > 1) {
				cards1.replace(trade.getCard1(), cards1.get(trade.getCard1())-1);
			} else {
				cards1.remove(trade.getCard1());
			}
			//add poster's recieving card to poster
			if(cards1.containsKey(trade.getCard2())) {
				cards1.replace(trade.getCard2(), cards1.get(trade.getCard2())+1);
			} else {
				cards1.put(trade.getCard2(), 1);
			}
			//remove posters recieving card from acceptor
			if(cards2.get(trade.getCard2()) > 1) {
				cards2.replace(trade.getCard2(), cards2.get(trade.getCard2())-1);
			} else {
				cards2.remove(trade.getCard2());
			}
			//add poster's giving card to acceptor
			if(cards2.containsKey(trade.getCard1())) {
				cards2.replace(trade.getCard1(), cards2.get(trade.getCard1())+1);
			} else {
				cards2.put(trade.getCard1(), 1);
			}
			user.setCards(cards2);
			poster.setCards(cards1);
			trade.setAcceptor(user.getName());
			trade.setTradeStatus(TradeStatus.ACCEPTED);
		}
		userRepo.save(user);
		userRepo.save(poster);
		return tradeRepo.save(trade);
	}

}
