package com.jonasestevam.streaming.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jonasestevam.streaming.model.Video;

public interface VideoRepository extends ReactiveMongoRepository<Video, String> {

}
