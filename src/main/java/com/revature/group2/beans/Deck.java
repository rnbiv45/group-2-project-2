package com.revature.group2.beans;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

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
	
	public Deck(
			String creator,
			Archetype primaryArchetype,
			Archetype secondaryArchetype) {
		super();
		this.creator = creator;
		this.key = new DeckKey(primaryArchetype, secondaryArchetype);
	}
}
