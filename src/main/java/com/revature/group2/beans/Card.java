package com.revature.group2.beans;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(value=Card.tableName)
@Data
public class Card {
	
	@org.springframework.data.annotation.Transient
	public static final String tableName = "card";
	
	@PrimaryKey
	@Getter @Setter private CardPrimaryKey cardPrimaryKey;
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
}
