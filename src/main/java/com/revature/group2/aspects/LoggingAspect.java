package com.revature.group2.aspects;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	private Logger log;
	
	@Around("everything()")
	public Object log(ProceedingJoinPoint pjp) throws Throwable {

		Object result = null;
		log = LogManager.getLogger(pjp.getTarget().getClass());
		log.trace("Method with signature: "+pjp.getSignature());
		log.trace("with arguments: "+Arrays.toString(pjp.getArgs()));
		try {
			result = pjp.proceed(); 
		}  catch(Throwable t) {
			log.error("Method threw exception: "+t);
			for(StackTraceElement s : t.getStackTrace()) {
				log.warn(s);
			}
			if(t.getCause() != null) {
				Throwable t2 = t.getCause();
				log.error("Method threw wrapped exception: "+t2);
				for(StackTraceElement s : t2.getStackTrace()) {
					log.warn(s);
				}
			}
			throw t; 
		}
		log.trace("Method returning with: "+result);
		return result;
	}
	
	@Pointcut("execution( * com.revature..*(..) )")
	private void everything() { /* Empty method for hook */ }
}
