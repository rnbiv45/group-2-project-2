package com.revature.group2.repos;

import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardKey;

import reactor.core.publisher.Mono;

public interface CardRepo extends ReactiveCassandraRepository<Card, CardKey> {
	@AllowFiltering
	Mono<Card> findByName(String name);
	@AllowFiltering
	Mono<Card> findByKeyUuid(UUID uuid);
}
