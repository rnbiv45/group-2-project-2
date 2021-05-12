package com.revature.group2.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
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

	@AfterReturning(pointcut = "makeToken()", returning = "user")
	public void makeNewToken(Mono<User> user, ServerWebExchange exchange) throws Throwable {
		user.subscribe(updatedUser -> {
			try {
				exchange.getResponse()
				.addCookie(ResponseCookie
						.from("token", tokenService.makeToken(updatedUser))
						.httpOnly(true).build());
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		});
	}

	@Pointcut("@annotation(com.revature.aspects.UpdateToken)")
	public void makeToken() {
		/* Empty Method for Hook */}
}
