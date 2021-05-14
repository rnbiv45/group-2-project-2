package com.revature.group2.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.group2.beans.User;
import com.revature.group2.utils.JWTParser;

import reactor.core.publisher.Mono;

@Component
@Aspect
public class TokenAspects {

	private JWTParser tokenService;

	@Autowired
	public void setTokenServicer(JWTParser parser) {
		this.tokenService = parser;
	}

	@AfterReturning(pointcut = "updateToken()", returning = "user")
	public void makeNewToken(JoinPoint joinpoint, Mono<User> user) {
		ServerWebExchange exchange = (ServerWebExchange) joinpoint.getArgs()[0];
		user.subscribe(updatedUser -> {
			try {
				exchange.getResponse()
				.addCookie(ResponseCookie
						.from("token", tokenService.makeToken(updatedUser))
						.httpOnly(true).build());
			} catch (JsonProcessingException e) {
				exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		});
	}

	@Pointcut("@annotation(com.revature.group2.aspects.UpdateToken)")
	public void updateToken() {/* Empty Method for Hook */}
}
