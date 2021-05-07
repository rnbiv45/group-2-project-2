package com.revature.group2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.group2.beans.Card;
import com.revature.group2.services.CardServiceImp;

import reactor.core.publisher.Flux;

@SpringBootApplication
@RestController
public class Project2Application {

	public static void main(String[] args) {
		SpringApplication.run(Project2Application.class, args);
	}
	
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
	
	@GetMapping("/test")
	public Flux<Card> testMethod(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new CardServiceImp().getCardsFromSystem();
	}
	
}
