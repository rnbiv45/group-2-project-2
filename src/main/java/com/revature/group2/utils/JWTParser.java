package com.revature.group2.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.group2.beans.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;

public class JWTParser {

	private ObjectMapper mapper;
	
	public User parser(String jwStr) throws JsonMappingException, JsonProcessingException{
		Jws<Claims> jws = Jwts.parser()
				.setSigningKey(TextCodec.BASE64
						.decode(System.getenv("SECRET_KEY")))
				.parseClaimsJws(jwStr);
		
		return mapper.readValue(jws.getBody()
				.get("user").toString(), User.class);
	}
	
	public String makeToken(User user) {
		return null;
		
	}
}
