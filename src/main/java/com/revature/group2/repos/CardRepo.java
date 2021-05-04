package com.revature.group2.repos;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.Type;
import com.revature.group2.beans.Archetype;
import com.revature.group2.beans.Effect;

import reactor.core.publisher.Flux;

public interface CardRepo extends ReactiveCassandraRepository<Card, String>{

}
