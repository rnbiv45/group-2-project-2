package com.revature.group2.utils;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.group2.beans.User;
import com.revature.group2.beans.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@ExtendWith(SpringExtension.class)
public class JWTParserTest {
	@TestConfiguration
	static class Configuration {
		
		@Bean JWTParser getParser() {
			JWTParser parser = new JWTParser();
			return parser;
		}
		
		@Bean
		public ObjectMapper getObjectMapper() {
			return Mockito.mock(ObjectMapper.class);
		}
		
	}
	
	@Autowired
	JWTParser parser;
	
	@Autowired
	ObjectMapper mapper;
	
	@Test
	void testMakeTokencreatesAToken() throws JsonProcessingException {
		
		User logged = new User();
		logged.setName("test");
		logged.setPass("pass");
		logged.setRole(UserRole.MEMBER);
		
		String userString = "{\"uuid\":"+ logged.getUuid() +
				",\"name\":\"test\",\"pass\":\"pass\",\"cards\""
				+ ":null,\"decks\":null,\"role\":\"MEMBER\"}";
		String key = System.getenv("SECRET_KEY");
		
		String expected = Jwts.builder()
				.claim("user", userString)
				.signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode(key))
				.compact();

		when(mapper.writeValueAsString(logged)).thenReturn(userString);
		String result = parser.makeToken(logged);
		
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	void testParseToken() throws JsonProcessingException {
		User logged = new User();
		logged.setName("test");
		logged.setPass("pass");
		logged.setRole(UserRole.MEMBER);
		
		String userString = "{\"uuid\":"+ logged.getUuid() +
				",\"name\":\"test\",\"pass\":\"pass\",\"cards\""
				+ ":null,\"decks\":null,\"role\":\"MEMBER\"}";
		String key = System.getenv("SECRET_KEY");
		
		String token = Jwts.builder()
				.claim("user", userString)
				.signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode(key))
				.compact();
		
		Jws<Claims> jws = Jwts.parser()
				.setSigningKey(TextCodec.BASE64
						.decode(System.getenv("SECRET_KEY")))
				.parseClaimsJws(token);
		String parsed = Jwts.builder().claim("user", userString)
		.signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode(key))
		.compact();
		
		when(mapper.readValue(jws.getBody()
				.get("user").toString(),User.class)).thenReturn(logged);
		
		assertThat(parser.parser(parsed)).isEqualTo(logged);
		
	}
	
}
