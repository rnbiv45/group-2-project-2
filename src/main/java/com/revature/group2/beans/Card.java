package com.revature.group2.beans;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value=Card.tableName)
public class Card {
	
	@org.springframework.data.annotation.Transient
	public static final String tableName = "card";
	
	@PrimaryKey
	private CardPrimaryKey cardPrimaryKey;
	@Column
	private String name;
	@Column
	private boolean isUnique;
	@Column
	private Integer attackValue;
	@Column
	private Integer defenseValue;
	@Column
	private Integer damageValue;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CardPrimaryKey getCardPrimaryKey() {
		return cardPrimaryKey;
	}
	public void setCardPrimaryKey(CardPrimaryKey cardPrimaryKey) {
		this.cardPrimaryKey = cardPrimaryKey;
	}
	public boolean isUnique() {
		return isUnique;
	}
	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}
	public Integer getAttackValue() {
		return attackValue;
	}
	public void setAttackValue(Integer attackValue) {
		this.attackValue = attackValue;
	}
	public Integer getDefenseValue() {
		return defenseValue;
	}
	public void setDefenseValue(Integer defenseValue) {
		this.defenseValue = defenseValue;
	}
	public Integer getDamageValue() {
		return damageValue;
	}
	public void setDamageValue(Integer damageValue) {
		this.damageValue = damageValue;
	}
	public static String getTablename() {
		return tableName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attackValue == null) ? 0 : attackValue.hashCode());
		result = prime * result + ((cardPrimaryKey == null) ? 0 : cardPrimaryKey.hashCode());
		result = prime * result + ((damageValue == null) ? 0 : damageValue.hashCode());
		result = prime * result + ((defenseValue == null) ? 0 : defenseValue.hashCode());
		result = prime * result + (isUnique ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Card other = (Card) obj;
		if (attackValue == null) {
			if (other.attackValue != null)
				return false;
		} else if (!attackValue.equals(other.attackValue))
			return false;
		if (cardPrimaryKey == null) {
			if (other.cardPrimaryKey != null)
				return false;
		} else if (!cardPrimaryKey.equals(other.cardPrimaryKey))
			return false;
		if (damageValue == null) {
			if (other.damageValue != null)
				return false;
		} else if (!damageValue.equals(other.damageValue))
			return false;
		if (defenseValue == null) {
			if (other.defenseValue != null)
				return false;
		} else if (!defenseValue.equals(other.defenseValue))
			return false;
		if (isUnique != other.isUnique)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Card [name=" + name + ", cardPrimaryKey=" + cardPrimaryKey + ", isUnique=" + isUnique + ", attackValue="
				+ attackValue + ", defenseValue=" + defenseValue + ", damageValue=" + damageValue + "]";
	}
	
	

}
