package com.revature.group2.beans;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import lombok.Data;

//import org.springframework.data.cassandra.core.mapping.Table;

//@Table("player")
@Data
public class User {
	private UUID id;
	private String name;
	private String pass;
	private Map<Card, Integer> cards;
	private Set<Deck> decks;
	private UserRole role;
	
	public User() {
		super();
		this.id = UUID.randomUUID();
		this.setName("");
		this.setPass("");
		this.setCards(new HashMap<Card, Integer>());
		this.setDecks(new HashSet<Deck>());
		this.setRole(UserRole.MEMBER);
	}
}
