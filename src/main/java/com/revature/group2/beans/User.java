package com.revature.group2.beans;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.oss.protocol.internal.ProtocolConstants.DataType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.revature.group2.deserializers.UserDeserializer;

import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import lombok.Data;

@Table("user")
//@JsonAutoDetect(fieldVisibility = Visibility.ANY)
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Data
//@Accessors(fluent = true)
//@NoArgsConstructor
//@AllArgsConstructor
@JsonDeserialize(using = UserDeserializer.class)
public class User {
	@PrimaryKey
	@CassandraType(type = Name.TEXT)
	@JsonProperty
	private String name;
	@Column
	@CassandraType(type = Name.TEXT)
	@JsonProperty
	private String pass;
	@Column
	@CassandraType(type = Name.BLOB)
	@JsonProperty
	private Map<Card, Integer> cards;
	@Column
	@CassandraType(type = Name.BLOB)
	@JsonProperty
	private Set<Deck> decks;
	@Column
	@CassandraType(type = Name.TEXT)
	@JsonProperty
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
