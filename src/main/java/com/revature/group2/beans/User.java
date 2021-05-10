package com.revature.group2.beans;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import lombok.Data;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.oss.protocol.internal.ProtocolConstants.DataType;

import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

@Table("user")
@Data
public class User {
	@PrimaryKey
	@CassandraType(type = Name.TEXT)
	private String name;
	@Column
	@CassandraType(type = Name.TEXT)
	private String pass;
	@Column
	@CassandraType(type = Name.BLOB)
	private Map<Card, Integer> cards;
	@Column
	@CassandraType(type = Name.BLOB)
	private Set<Deck> decks;
	@Column
	@CassandraType(type = Name.TEXT)
	private UserRole role;

	
	public User() {
		super();
		this.setName("");
		this.setPass("");
		this.setCards(new HashMap<Card, Integer>());
		this.setDecks(new HashSet<Deck>());
		this.setRole(UserRole.MEMBER);
	}
	
	public void addCard(Card card) {
		Integer amount = this.cards.get(card);
		if (amount == null) {
			this.cards.put(card, 1);
			return;
		}
		this.cards.put(card, amount+1);
	}
	
	public void removeCard(Card card) {
		Integer amount = this.cards.get(card);
		if (amount == null) {
			return;
		}
		if (amount > 1) {
			this.cards.put(card, amount-1);
			return;
		}
		this.cards.remove(card);
	}
}
