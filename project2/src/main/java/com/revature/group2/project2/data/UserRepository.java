package com.revature.group2.project2.data;

import java.util.List;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.revature.group2.project2.beans.User;


@Repository
public interface UserRepository extends CassandraRepository<User, String>{
	@AllowFiltering
	List<User> findAllByRole(User.Role r);

}
