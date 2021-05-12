package com.revature.group2.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
	public void authorizedUser (ProceedingJoinPoint joinPoint) throws Throwable{
		if(joinPoint.getArgs().length == 0) {
			throw new Exception("Invalid arguments for advice method" + joinPoint.getSignature());
		}
		String token;
		try {
			token = (String) joinPoint.getArgs()[0];
		}catch (Exception e) {
			ResponseEntity.badRequest().body("Unauthorized User");
			return;
		}
		
		User user = tokenService.parser(token);
		
		if(user != null) {
			joinPoint.proceed();
		}
		else {
			ResponseEntity.badRequest().body("Unauthorized User");
			return;
		}
		
	}
	
	@Around("adminHook()")
	public void adminUser (ProceedingJoinPoint joinPoint) throws Throwable{
		if(joinPoint.getArgs().length == 0) {
			throw new Exception("Invalid arguments for advice method" + joinPoint.getSignature());
		}
		String token = (String) joinPoint.getArgs()[0];
		
		User user = tokenService.parser(token);
		
		if(user != null && user.getRole().equals(UserRole.ADMIN)) {
			// We're an admin, the advised method can be called.
			joinPoint.proceed();
		} else {
			// We aren't an admin, we aren't even going to call the method.
			ResponseEntity.status(403).body("Forbidden");
			return;
		}
	}
	
	@Pointcut("@annotation(com.revature.group2.aspects.Admin)")
	public void adminHook() { /* Empty method for Hook */ }
	@Pointcut("@annotation(com.revature.group2.aspects.Authorized)")
	public void authorizedHook() { /* Empty method for Hook */ } 
}