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
	private boolean isUnique;
	@Column
	private Integer attackValue;
	@Column
	private Integer defenseValue;
	@Column
	private Integer damageValue;
	@Column
	private Integer buffValue;
	
	//Prevents negative numbers
//	public void setAttackValue(Integer attackValue) {
//		if (attackValue >= 0) {
//			this.attackValue = attackValue;
//		} else {
//			System.err.println("Value cannot be negative!");
//		}
//	}
//	public void setDefenseValue(Integer defenseValue) {
//		if (defenseValue >= 0) {
//			this.defenseValue = defenseValue;
//		} else {
//			System.err.println("Value cannot be negative!");
//		}
//	}
//	public void setDamageValue(Integer damageValue) {
//		if (damageValue >= 0) {
//			this.damageValue = damageValue;
//		} else {
//			System.err.println("Value cannot be negative!");
//		}
//	}
}
