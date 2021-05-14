package com.revature.group2.services;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.Trade;
import com.revature.group2.beans.User;
import com.revature.group2.repos.TradeRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TradeService {
	void setTradeRepo(TradeRepo tradeRepo);
	Flux<Trade> viewTradesForCard(Card card);
	Flux<Trade> viewTradesByUser(User user);
	Mono<Trade> submitTrade(Trade trade);
	Mono<Trade> acceptTrade(Trade trade, User user);

}
