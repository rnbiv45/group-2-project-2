package com.revature.group2.beans;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.oss.protocol.internal.ProtocolConstants.DataType;

import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

@Table("user")
public class User {
	@PrimaryKey
	@CassandraType(type = Name.TEXT)
	private String name; //* username: String
	@Column
	@CassandraType(type = Name.TEXT)
	private String pass; //* password: String
	@Column
	@CassandraType(type = Name.BLOB)
	private Map<Card, Integer> cards; //* cards: Map of Card, Amount
	@Column
	@CassandraType(type = Name.BLOB)
	private Set<Deck> decks; //* decks: Set of Decks
	@Column
	@CassandraType(type = Name.TEXT)
	private Role role; //* isAdmin: Boolean
	
	public enum Role {
		MEMBER, MOD, ADMIN;
	}
	
	
	public User() {
		super();
		this.setName("");
		this.setPass("");
		this.setCards(new HashMap<Card, Integer>());
		this.setDecks(new HashSet<Deck>());
		this.setRole(Role.MEMBER);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}


	public Map<Card, Integer> getCards() {
		return cards;
	}


	public void setCards(Map<Card, Integer> cards) {
		this.cards = cards;
	}


	public Set<Deck> getDecks() {
		return decks;
	}


	public void setDecks(Set<Deck> decks) {
		this.decks = decks;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
		result = prime * result + ((decks == null) ? 0 : decks.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		User other = (User) obj;
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!cards.equals(other.cards))
			return false;
		if (decks == null) {
			if (other.decks != null)
				return false;
		} else if (!decks.equals(other.decks))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pass == null) {
			if (other.pass != null)
				return false;
		} else if (!pass.equals(other.pass))
			return false;
		if (role != other.role)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "User [name=" + name + ", pass=" + pass + ", cards=" + cards + ", decks=" + decks + ", role=" + role
				+ "]";
	}


	
	
	
}
