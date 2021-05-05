package com.revature.group2.beans;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class CardPrimaryKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7715853812503259164L;
	@PrimaryKeyColumn(
			name="type",
			ordinal=1,
			type = PrimaryKeyType.PARTITIONED,
			ordering = Ordering.DESCENDING)
	@CassandraType(type = Name.TEXT)
	Type type;
	@PrimaryKeyColumn(
			name="archetype",
			ordinal=0,
			type = PrimaryKeyType.PARTITIONED,
			ordering = Ordering.DESCENDING)
	@CassandraType(type = Name.TEXT)
	Archetype archetype;
	@PrimaryKeyColumn(
			name="id",
			ordinal=4,
			type = PrimaryKeyType.CLUSTERED,
			ordering = Ordering.DESCENDING)
	@CassandraType(type = Name.UUID)
	UUID id;
	@PrimaryKeyColumn(
			name="rarity",
			ordinal=2,
			type = PrimaryKeyType.CLUSTERED,
			ordering = Ordering.DESCENDING)
	@CassandraType(type = Name.INT)
	int rarity;
	@Column
	Set<Effect> effects;
	@PrimaryKeyColumn(
			name="isBanned",
			ordinal=3,
			type = PrimaryKeyType.CLUSTERED,
			ordering = Ordering.DESCENDING)
	@CassandraType(type = Name.BOOLEAN)
	boolean isBanned;
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Archetype getArchetype() {
		return archetype;
	}
	public void setArchetype(Archetype archetype) {
		this.archetype = archetype;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public int getRarity() {
		return rarity;
	}
	public void setRarity(int rarity) {
		this.rarity = rarity;
	}
	public Set<Effect> getEffects() {
		return effects;
	}
	public void setEffects(Set<Effect> effects) {
		this.effects = effects;
	}
	public boolean isBanned() {
		return isBanned;
	}
	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((archetype == null) ? 0 : archetype.hashCode());
		result = prime * result + ((effects == null) ? 0 : effects.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isBanned ? 1231 : 1237);
		result = prime * result + rarity;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardPrimaryKey other = (CardPrimaryKey) obj;
		if (archetype != other.archetype)
			return false;
		if (effects == null) {
			if (other.effects != null)
				return false;
		} else if (!effects.equals(other.effects))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isBanned != other.isBanned)
			return false;
		if (rarity != other.rarity)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CardPrimaryKey [type=" + type + ", archetype=" + archetype + ", id=" + id + ", rarity=" + rarity
				+ ", effects=" + effects + ", isBanned=" + isBanned + "]";
	}

	
	
}
