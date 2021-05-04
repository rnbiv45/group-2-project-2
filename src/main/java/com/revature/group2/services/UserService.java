package com.revature.group2.services;

import com.revature.group2.beans.User;

import reactor.core.publisher.Mono;

public interface UserService {

	Mono<User> addUser(User user);

}
