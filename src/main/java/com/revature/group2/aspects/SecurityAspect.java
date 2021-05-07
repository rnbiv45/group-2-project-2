package com.revature.group2.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import com.revature.group2.beans.User;

@Component
@Aspect
public class SecurityAspect {

	public void authorizedUser (ProceedingJoinPoint joinPoint) throws Throwable{
		if(joinPoint.getArgs().length == 0) {
			throw new Exception("Invalid User");
		}
		HttpRequest ctx = (HttpRequest) joinPoint.getArgs()[0];
		
	}
	
	public void adminUser (ProceedingJoinPoint joinPoint) throws Throwable{
		if(joinPoint.getArgs().length == 0) {
			throw new Exception("Invalid User");
		}
		HttpRequest req = (HttpRequest) joinPoint.getArgs()[0];
	}
}