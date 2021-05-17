package com.revature.group2.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.revature.group2.aspects.Authorized;
import com.revature.group2.aspects.OwnerAndAdmin;
import com.revature.group2.beans.Card;
import com.revature.group2.beans.Trade;
import com.revature.group2.beans.User;
import com.revature.group2.services.TradeService;
import com.revature.group2.utils.JWTParser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/trade")
public class TradeController {
	private TradeService tradeService;
	private JWTParser tokenService;
	private String tokenString;
	
	@Autowired
	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	
	@Autowired
	public void setTokenService(JWTParser parser) {
		this.tokenService = parser;
	}
	
	@Autowired
	public void setTokenString() {
		this.tokenString = "token";
	}
	
	@Authorized
	@GetMapping("/Own")
	public Flux<Trade> getOwnTrades(ServerWebExchange exchange){
		User user = null;
		try {
			if(exchange.getRequest().getCookies().get(tokenString) != null) {
				String token = exchange.getRequest().getCookies().getFirst(tokenString).getValue();
				if(!token.equals("")) {
					user = tokenService.parser(token);
					return tradeService.viewTradesByUser(user);
				}
			}
		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return null;
		}
		return null;
		
	}
	
	@Authorized
	@GetMapping("/User")
	public Flux<Trade> getTradesByUser(ServerWebExchange exchange, @RequestBody User user){
		return tradeService.viewTradesByUser(user);
		
	}
	
	@Authorized
	@GetMapping
	public Flux<Trade> getPendingTrades(ServerWebExchange exchange){
		return tradeService.viewPendingTrades();
		
	}
	
	@Authorized
	@GetMapping("/Card")
	public Flux<Trade> getTradesByCard(ServerWebExchange exchange, @RequestBody Card card){
		return tradeService.viewTradesForCard(card);
		
	}
	
	@Authorized
	@PostMapping("submit")
	public Mono<Trade> submitTrade(ServerWebExchange exchange, @RequestBody Trade trade){
		User user = null;
		try {
			if(exchange.getRequest().getCookies().get(tokenString) != null) {
				String token = exchange.getRequest().getCookies().getFirst(tokenString).getValue();
				if(!token.equals("")) {
					user = tokenService.parser(token);
					trade.setPoster(user.getName());
					trade.setPosterId(user.getUuid());
					trade.setAcceptor("pending");
					trade.setAcceptorId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
					return tradeService.submitTrade(trade);
				}
			}
		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return null;
		}
		return null;
	}
	
	@Authorized
	@PutMapping("accept")
	public Mono<Trade> acceptTrade(ServerWebExchange exchange, @RequestBody Trade trade){
		User user = null;
		try {
			if(exchange.getRequest().getCookies().get(tokenString) != null) {
				String token = exchange.getRequest().getCookies().getFirst(tokenString).getValue();
				if(!token.equals("")) {
					user = tokenService.parser(token);
					return tradeService.acceptTrade(trade.getTradeId(), user);
				}
			}
		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return null;
		}
		return null;
	}

}
