package com.jonasestevam.streaming.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jonasestevam.streaming.model.User;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
