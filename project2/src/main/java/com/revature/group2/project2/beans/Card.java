package com.revature.group2.project2.beans;

import java.time.LocalDate;
import java.util.Set;

public class Card {
	private int id;
	private String name;
	private Type type;
	private Archetype archetype;
	private int rarity;
	//private Set<SpecialEffect> specialEffect; turn this on when we have effects
	private Boolean isBanned;
	private Boolean isUnique;
	private LocalDate releaseYear;
	//MONSTER CARDS
	private int attackValue;
	private int defenseValue;
	//SPELL/TRAP CARDS
	private int damageValue;
	
	public enum Type{
		MONSTER, SPELL, TRAP
	}
	
	public enum Archetype{
		EARTH, FIRE, WIND, WATER //,HEART 
	}
	
	
	public Card() {
		super();
		this.setId(0);
		this.setName("");
		this.setType(null);
		this.setArchetype(null);
		this.setRarity(0);
		this.setBanned(false);
		this.setUnique(false);
		this.setAttackValue(0);
		this.setDefenseValue(0);
		this.setDamageValue(0);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public int getRarity() {
		return rarity;
	}

	public void setRarity(int rarity) {
		this.rarity = rarity;
	}

	public Boolean isBanned() {
		return isBanned;
	}

	public void setBanned(Boolean isBanned) {
		this.isBanned = isBanned;
	}

	public Boolean isUnique() {
		return this.isUnique;
	}

	public void setUnique(Boolean isUnique) {
		this.isUnique = isUnique;
	}

	public LocalDate getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(LocalDate releaseYear) {
		this.releaseYear = releaseYear;
	}

	public int getAttackValue() {
		return this.attackValue;
	}

	public void setAttackValue(int attackValue) {
		if (attackValue >= 0) {
			this.attackValue = attackValue;
		} else {
			System.err.println("Cannot go under 0!");
		}
	}

	public int getDefenseValue() {
		return defenseValue;
	}

	public void setDefenseValue(int defenseValue) {
		if (defenseValue >= 0) {
			this.defenseValue = defenseValue;
		} else {
			System.err.println("Cannot go under 0!");
		}
	}

	public int getDamageValue() {
		return this.damageValue;
	}

	public void setDamageValue(int damageValue) {
		if (damageValue >= 0) {
			this.damageValue = damageValue;
		} else {
			System.err.println("Cannot go under 0!");
		}
	}

}
