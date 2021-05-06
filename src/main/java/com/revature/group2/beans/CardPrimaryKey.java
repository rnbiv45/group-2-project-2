package com.revature.group2.beans;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.Data;

@PrimaryKeyClass
@Data
public class CardPrimaryKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7715853812503259164L;
	@PrimaryKeyColumn(
			name="type",
			ordinal=1,
			type = PrimaryKeyType.PARTITIONED)
	@CassandraType(type = Name.TEXT)
	Type type;
	@PrimaryKeyColumn(
			name="archetype",
			ordinal=0,
			type = PrimaryKeyType.PARTITIONED)
	@CassandraType(type = Name.TEXT)
	Archetype archetype;
	@PrimaryKeyColumn(
			name="uuid",
			ordinal=4,
			type = PrimaryKeyType.CLUSTERED,
			ordering = Ordering.DESCENDING)
	@CassandraType(type = Name.UUID)
	UUID uuid;
	@PrimaryKeyColumn(
			name="rarity",
			ordinal=2,
			type = PrimaryKeyType.CLUSTERED,
			ordering = Ordering.DESCENDING)
	@CassandraType(type = Name.INT)
	int rarity;
	@PrimaryKeyColumn(
			name="isBanned",
			ordinal=3,
			type = PrimaryKeyType.CLUSTERED,
			ordering = Ordering.DESCENDING)
	@CassandraType(type = Name.BOOLEAN)
	boolean isBanned;
}
