package com.revature.group2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.User;

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
		Card c1 = new Card();
		c1.setName("Albert");
		Card c2 = new Card();
		c2.setName("Bill");
		Card c3 = new Card();
		c3.setName("Chris");
		User user = new User();
		Map<Card, Integer> m = new HashMap<Card, Integer>();
		m.put(c3, 0);
		user.setCards(m);
		
		return Flux.just(c1,c2,c3).filter(c -> {
			for(Card result : user.getCards().keySet()) {
				if (c.equals(result)) {
					return false;
				}
			}
			return true;
		});
	}
}
