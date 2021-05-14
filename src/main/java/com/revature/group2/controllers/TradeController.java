package com.revature.group2.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.Trade;
import com.revature.group2.beans.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/trade")
public class TradeController {
	
	@GetMapping
	public Flux<Trade> getOwnTrades(ServerWebExchange exchange){
		return null;
		
	}
	
	@GetMapping("")
	public Flux<Trade> getTradesByUser(ServerWebExchange exchange, @RequestBody User user){
		return null;
		
	}
	
	@GetMapping("")
	public Flux<Trade> getPendingTrades(ServerWebExchange exchange){
		return null;
		
	}
	
	@GetMapping("")
	public Flux<Trade> getTradesByCard(ServerWebExchange exchange, @RequestBody Card card){
		return null;
		
	}
	
	@PostMapping("submit")
	public Mono<Trade> submitTrade(ServerWebExchange exchange, @RequestBody Trade trade){
		return null;
	}
	
	@PostMapping("accept")
	public Mono<Trade> acceptTrade(ServerWebExchange exchange, @RequestBody Trade trade){
		return null;
		
	}

}
