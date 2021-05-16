package com.revature.group2.deserializers;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import com.datastax.oss.driver.shaded.guava.common.base.Splitter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.revature.group2.beans.User;
import com.revature.group2.beans.UserRole;

public class UserDeserializer extends StdDeserializer<User> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4006626363177619800L;

	public UserDeserializer() {
		this(null);
	}
	
	public UserDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		JsonNode node = jp.getCodec().readTree(jp);
		User user = new User();
		if(node.get("card") != null) {
			System.out.println(node.get("card").toString());
		}
		else {
			System.out.println("null value");
		}
		
		if(node.get("uuid") != null) {
			user.setUuid(UUID.fromString(node.get("uuid").asText()));
		}
		else {
			user.setUuid(UUID.randomUUID());
		}
		
		user.setName(node.get("name").asText());
		user.setPass(node.get("pass").asText());
		user.setCards(new HashMap<>());
		if(node.get("card") != null){
			System.out.println(node.get("card"));
			//user.setCards(node.get("card").asText());
		}
		else {
			System.out.println("card is null");
		}
		user.setDecks(new HashSet<>());
		if(node.get("role") != null) {
			user.setRole(UserRole.valueOf(node.get("role").asText()));
		}
		else {
			user.setRole(UserRole.MEMBER);
		}
		return user;
    }

}
