package com.revature.group2.beans;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import lombok.Data;

//import org.springframework.data.cassandra.core.mapping.Column;
//import org.springframework.data.cassandra.core.mapping.PrimaryKey;
//import org.springframework.data.cassandra.core.mapping.Table;

//@Table("player")
@Data
public class User {
	//@PrimaryKey
	private String name; //* username: String
	//@Column
	private String pass; //* password: String
	//@Column
	private Map<Card, Integer> cards; //* cards: Map of Card, Amount
	//@Column
	private Set<Deck> decks; //* decks: Set of Decks
	//@Column
	private UserRole role; //* isAdmin: Boolean
	
	public User() {
		super();
		this.setName("");
		this.setPass("");
		this.setCards(new HashMap<Card, Integer>());
		this.setDecks(new HashSet<Deck>());
		this.setRole(UserRole.MEMBER);
	}
}
