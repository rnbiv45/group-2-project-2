package com.revature.group2.beans;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;


@Table("deck")
@Data
public class Deck {
	@PrimaryKey
	private DeckKey key;
	@Column
	@CassandraType(type = Name.TEXT)
	private String creator;
	@Column
	@CassandraType(type = Name.MAP, typeArguments = {Name.TEXT, Name.INT})
	private Map<String, Integer> cards;
	
	public Deck() {
		super();
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

	public Deck(
			String creator,
			Archetype primaryArchetype,
			Archetype secondaryArchetype) {
		super();
		this.creator = creator;
		this.key = new DeckKey(primaryArchetype, secondaryArchetype);

	}
}
