package com.revature.group2.repos;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import com.revature.group2.beans.Card;
import com.revature.group2.beans.User;

public interface UserRepo extends ReactiveCassandraRepository<User, String> {
	
}
