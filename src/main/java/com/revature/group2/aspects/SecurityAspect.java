package com.revature.group2.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.group2.beans.User;
import com.revature.group2.beans.UserRole;
import com.revature.group2.utils.JWTParser;

@Component
@Aspect
public class SecurityAspect {
	
	private JWTParser tokenService;
	
	@Autowired
	public void setTokenServicer(JWTParser parser) {
		this.tokenService = parser;
	}

	@Around("authorizedHook()")
	public Object authorizedUser (ProceedingJoinPoint joinPoint) throws Throwable{
		if(joinPoint.getArgs().length == 0) {
			throw new IllegalArgumentException();
		}
		Object[] methodArgs = joinPoint.getArgs();
		
		ServerWebExchange exchange = null;
		for(Object arg: methodArgs) {
			if(arg instanceof ServerWebExchange) {
				exchange = (ServerWebExchange) arg;
				break;
			}
		}
		
		try {
			User user = new User();
			HttpCookie cookie = exchange.getRequest().getCookies().getFirst("token");
			
			if(cookie == null) {
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return null;
			}
			String token = cookie.getValue();
			user = tokenService.parser(token);
			if(user == null) {
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return null;
			}
			
		} catch ( JsonProcessingException e) {
			exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			return null;
		}
		
		return joinPoint.proceed();
	}
	
	@Around("adminHook()")
	public Object adminUser (ProceedingJoinPoint joinPoint) throws Throwable{
		if(joinPoint.getArgs().length == 0) {
			throw new Exception("Invalid arguments for advice method" + joinPoint.getSignature());
		}
		Object[] methodArgs = joinPoint.getArgs();
		
		ServerWebExchange exchange = null;
		for(Object arg: methodArgs) {
			if(arg instanceof ServerWebExchange) {
				exchange = (ServerWebExchange) arg;
				break;
			}
		}
		
		try {
			User user = new User();
			HttpCookie cookie = exchange.getRequest().getCookies().getFirst("token");
			
			if(cookie == null) {
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return null;
			}
			String token = cookie.getValue();
			user = tokenService.parser(token);
			
			if(!user.getRole().equals(UserRole.ADMIN)) {
				exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
				return null;
			}
			
		} catch ( JsonProcessingException e) {
			exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			return null;
		}
		
		return joinPoint.proceed();
	}
	
	@Pointcut("@annotation(com.revature.group2.aspects.Admin)")
	public void adminHook() { /* Empty method for Hook */ }
	@Pointcut("@annotation(com.revature.group2.aspects.Authorized)")
	public void authorizedHook() { /* Empty method for Hook */ } 
}