package com.revature.group2.repos;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends ReactiveCassandraRepository {
	
}
