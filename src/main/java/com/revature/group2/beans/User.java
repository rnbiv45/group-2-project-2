package com.revature.group2.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Table("user")
@Data
public class User implements Serializable{
	private static final long serialVersionUID = 2952017686227479583L;
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
	private UserRole role; //* isAdmin: Boolean

	public User() {
		super();
		this.setName("");
		this.setPass("");
		this.setCards(new HashMap<Card, Integer>());
		this.setDecks(new HashSet<Deck>());
		this.setRole(UserRole.MEMBER);
	}
}
