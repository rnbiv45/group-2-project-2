package com.revature.group2.repos;

import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.group2.beans.User;

import reactor.core.publisher.Mono;

public interface UserRepo extends ReactiveCassandraRepository<User, UUID> {
	Mono<User> findByUuid(UUID uuid);
	Mono<User> deleteByUuid(UUID uuid);
	@AllowFiltering
	Mono<User> findByName(String username);
}
