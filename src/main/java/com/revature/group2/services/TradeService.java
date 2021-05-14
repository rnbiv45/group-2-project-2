package com.revature.group2.services;

import java.util.UUID;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.Trade;
import com.revature.group2.beans.User;
import com.revature.group2.repos.TradeRepo;
import com.revature.group2.repos.UserRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TradeService {
	void setTradeRepo(TradeRepo tradeRepo);
	void setUserRepo(UserRepo userRepo);
	Flux<Trade> viewTradesForCard(Card card);
	Flux<Trade> viewTradesByUser(User user);
	Flux<Trade> viewPendingTrades();
	Mono<Trade> submitTrade(Trade trade);
	Mono<Trade> acceptTrade(UUID tradeId, User user);

}
