package com.revature.group2.repos;


import java.util.UUID;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import com.revature.group2.beans.Deck;
import com.revature.group2.beans.DeckKey;

import reactor.core.publisher.Mono;

@Repository 
public interface DeckRepo extends ReactiveCassandraRepository<Deck, DeckKey> {
	Mono<Deck> findByKeyUuid(UUID uuid);

}
