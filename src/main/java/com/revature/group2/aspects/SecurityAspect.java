package com.revature.group2.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.result.view.RequestContext;

@Component
@Aspect
public class SecurityAspect {

	public void authorizedUser (ProceedingJoinPoint joinPoint) throws Throwable{
		if(joinPoint.getArgs().length == 0) {
			throw new Exception("Invalid User");
		}
		RequestContext ctx = (RequestContext) joinPoint.getArgs()[0];
		
	}
}