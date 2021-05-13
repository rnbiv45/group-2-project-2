package com.revature.group2.beans;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;



@Table(value=Card.tableName)
@Data
public class Card {
	
	@Transient
	public static final String tableName = "card";
	
	@PrimaryKey
	private CardPrimaryKey cardPrimaryKey;
	@Column
	private String name;
	@Column
	private Boolean isUnique;
	@Column
	private Integer attackValue;
	@Column
	private Integer defenseValue;
	@Column
	private Integer damageValue;
	@Column
	private Integer buffValue;
	

	public Card() {
		super();
	}

}
