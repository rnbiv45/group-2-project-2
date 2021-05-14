package com.revature.group2.repos;

import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.CardKey;
import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Effect;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CardRepo extends ReactiveCassandraRepository<Card, CardKey> {
	@AllowFiltering
	Mono<Card> findByName(String name);
	Mono<Card> findByKeyUuid(UUID uuid);
}
