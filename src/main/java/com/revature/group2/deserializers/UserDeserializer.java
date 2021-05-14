package com.revature.group2.deserializers;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

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
		user.setName(node.get("name").asText());
		user.setPass(node.get("pass").asText());
		user.setCards(new HashMap<>());
		user.setDecks(new HashSet<>());
		user.setRole(UserRole.MEMBER);
		user.setUuid(UUID.randomUUID());
		return user;
    }
}
