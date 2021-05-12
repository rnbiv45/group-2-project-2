package com.revature.group2.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	public void makeNewToken(Mono<User> user) throws Throwable {
		user.subscribe();
	}

	@Pointcut
	public void makeToken() {
		/* Empty Method for Hook */}
}
