package com.jonasestevam.streaming.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public MongoService(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public ReactiveMongoTemplate getReactiveMongoTemplate() {
        return reactiveMongoTemplate;
    }
}
