package com.revature.group2.beans;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;

import lombok.Data;


@Data
public class Deck {
	@PrimaryKeyColumn(
			name="primaryArchetype",
			ordinal=0,
			type = PrimaryKeyType.PARTITIONED)
	@CassandraType(type = Name.TEXT)
	private Archetype primaryArchetype;
	@PrimaryKeyColumn(
			name="secondaryArchetype",
			ordinal=1,
			type = PrimaryKeyType.PARTITIONED)
	@CassandraType(type = Name.TEXT)
	private Archetype secondaryArchetype;
	@PrimaryKeyColumn(
			name="uuid",
			ordinal=2,
			type = PrimaryKeyType.CLUSTERED,
			ordering = Ordering.DESCENDING)
	@CassandraType(type = Name.UUID)
	private UUID uuid;
	@Column
	@CassandraType(type = Name.TEXT)
	private String creator;
	@Column
	@CassandraType(type = Name.BLOB)
	private Map<Card, Integer> cards;
	
	public Deck() {
		super();
	}
	
	public void addCard(Card card) {
		this.cards.compute(card, (k, v) -> (v == null) ? 1 : v++);

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
