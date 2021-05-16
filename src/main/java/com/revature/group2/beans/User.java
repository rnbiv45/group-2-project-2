package com.revature.group2.beans;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.revature.group2.deserializers.UserDeserializer;

import lombok.Data;


@Table("user")
@Data
@JsonDeserialize(using = UserDeserializer.class)
public class User {
	@PrimaryKey
	@CassandraType(type = Name.UUID)
	@JsonProperty
	private UUID uuid;
	@Column
	@CassandraType(type = Name.TEXT)
	@JsonProperty
	private String name;
	@Column
	@CassandraType(type = Name.TEXT)
	@JsonProperty
	private String pass;
	@Column
	@CassandraType(type = Name.MAP, typeArguments = {Name.TEXT, Name.INT})
	@JsonProperty
	private Map<String, Integer> cards;
	@Column
	@CassandraType(type = Name.SET, typeArguments = {Name.TEXT})
	@JsonProperty
	private Set<String> decks;
	@Column
	@CassandraType(type = Name.TEXT)
	@JsonProperty
	private UserRole role;

	public User() {
		super();
		this.uuid = UUID.randomUUID();
		this.setName("");
		this.setPass("");
		this.setCards(new HashMap<>());
		this.setDecks(new HashSet<>());
		this.setRole(UserRole.MEMBER);
	}
	
	public void addCard(Card card) {
		String cardName = card.getKey().getUuid().toString();
		if(this.cards == null) {
			this.setCards(new HashMap<>());
		}
		this.cards.compute(cardName, (k, v) -> (v == null) ? 1 : v++);

	}
	
	public void removeCard(Card card) {
		String cardName = card.getKey().getUuid().toString(); 
		Integer amount = this.cards.get(cardName);
		if (amount == null) {
			return;
		}
		if (amount > 1) {
			this.cards.put(cardName, amount-1);
			return;
		}
		this.cards.remove(cardName);
	}
	
	public void addDeck(Deck deck) {
		if(this.decks == null) {
			this.decks = new HashSet<>();
		}
		this.decks.add(deck.getKey().getUuid().toString());
	}
	
	public void removeDeck(Deck deck) {
		this.decks.remove(deck.getKey().getUuid().toString());
	}


}
