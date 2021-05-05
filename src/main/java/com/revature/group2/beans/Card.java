package com.revature.group2.beans;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Table("card")
@Data
public class Card {
	@Column 
	String name;
	@PrimaryKeyColumn(
			name="type",
			ordinal=1,
			type = PrimaryKeyType.PARTITIONED,
			ordering = Ordering.DESCENDING)
	Type type;
	@PrimaryKeyColumn(
			name="archetype",
			ordinal=0,
			type = PrimaryKeyType.PARTITIONED,
			ordering = Ordering.DESCENDING)
	Archetype archetype;
	@PrimaryKeyColumn(
			name="id",
			ordinal=4,
			type = PrimaryKeyType.CLUSTERED,
			ordering = Ordering.DESCENDING)
	UUID id;
	@PrimaryKeyColumn(
			name="rarity",
			ordinal=2,
			type = PrimaryKeyType.CLUSTERED,
			ordering = Ordering.DESCENDING)
	int rarity;
	@Column
	Set<Effect> effects;
	@PrimaryKeyColumn(
			name="isBanned",
			ordinal=3,
			type = PrimaryKeyType.CLUSTERED,
			ordering = Ordering.DESCENDING)
	boolean isBanned;
	@Column
	boolean isUnique;
	@Column
	Integer attackValue;
	@Column
	Integer defenseValue;
	@Column
	Integer damageValue;
}
