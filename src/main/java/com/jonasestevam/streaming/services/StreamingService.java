package com.jonasestevam.streaming.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class StreamingService {

    @Autowired
    ResourceLoader resourceLoader;

    @Value("${upload.directory}")
    private String VIDEO_DIRECTORY;

    public Mono<Resource> stream(String videoId) {
        var resource = resourceLoader.getResource(generateResourcePath(videoId));
        return Mono.fromSupplier(() -> resource);
    }

    private String generateResourcePath(String videoId) {
        return "file:" + VIDEO_DIRECTORY + "/" + videoId + ".mp4";
    }
}
