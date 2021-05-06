package com.revature.group2.beans;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;

import com.revature.group2.beans.Archetype;;

public class Deck {
	@PrimaryKeyColumn(
			name="primaryArchetype",
			ordinal=0,
			type = PrimaryKeyType.PARTITIONED,
			ordering = Ordering.DESCENDING)
	@CassandraType(type = Name.TEXT)
	private Archetype primaryArchetype;
	@PrimaryKeyColumn(
			name="secondaryArchetype",
			ordinal=1,
			type = PrimaryKeyType.PARTITIONED,
			ordering = Ordering.DESCENDING)
	@CassandraType(type = Name.TEXT)
	private Archetype secondaryArchetype;
	@PrimaryKeyColumn(
			name="uuid",
			ordinal=2,
			type = PrimaryKeyType.CLUSTERED)
	@CassandraType(type = Name.UUID)
	private UUID uuid;
	@Column
	private User creator;
	@Column
	private Map<Card, Integer> cards;
		
	public Deck() {
		super();
		this.setPrimaryArchetype(null);
		this.setSecondaryArchetype(null);
		this.setCreator(null);
		this.setCards(new HashMap<Card, Integer>());
		
	}



	public Archetype getPrimaryArchetype() {
		return primaryArchetype;
	}



	public void setPrimaryArchetype(Archetype primaryArchetype) {
		this.primaryArchetype = primaryArchetype;
	}



	public Archetype getSecondaryArchetype() {
		return secondaryArchetype;
	}



	public void setSecondaryArchetype(Archetype secondaryArchetype) {
		this.secondaryArchetype = secondaryArchetype;
	}



	public User getCreator() {
		return creator;
	}



	public void setCreator(User creator) {
		this.creator = creator;
	}



	public Map<Card, Integer> getCards() {
		return cards;
	}



	public void setCards(Map<Card, Integer> cards) {
		this.cards = cards;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((primaryArchetype == null) ? 0 : primaryArchetype.hashCode());
		result = prime * result + ((secondaryArchetype == null) ? 0 : secondaryArchetype.hashCode());
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
		Deck other = (Deck) obj;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (primaryArchetype != other.primaryArchetype)
			return false;
		if (secondaryArchetype != other.secondaryArchetype)
			return false;
		return true;
	}
	
	
	
	
}
