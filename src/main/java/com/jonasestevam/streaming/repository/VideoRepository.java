package com.jonasestevam.streaming.repository;

import java.util.Set;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jonasestevam.streaming.enuns.CategoriesEnum;
import com.jonasestevam.streaming.model.Video;

import reactor.core.publisher.Flux;

public interface VideoRepository extends ReactiveMongoRepository<Video, String> {

    Flux<Video> findByCategory(CategoriesEnum categories);

    Flux<Video> findByIdIn(Set<String> ids);

}
