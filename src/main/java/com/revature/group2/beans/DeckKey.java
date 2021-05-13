package com.revature.group2.beans;

import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.Data;

import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;

@PrimaryKeyClass
@Data
public class DeckKey {
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
	
	public DeckKey() {
		super();
		this.primaryArchetype = Archetype.FIRE;
		this.secondaryArchetype = Archetype.WATER;
		this.uuid = UUID.randomUUID();
	}
	
	public DeckKey(
			Archetype primaryArchetype, 
			Archetype secondaryArchetype) {
		super();
		this.primaryArchetype = primaryArchetype;
		this.secondaryArchetype = secondaryArchetype;
		this.uuid = UUID.randomUUID();
	}
}
