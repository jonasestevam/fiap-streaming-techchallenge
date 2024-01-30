package com.jonasestevam.streaming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;

import com.jonasestevam.streaming.services.StreamingService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("stream")
public class StreamingController {
    @Autowired
    StreamingService streamingService;

    @GetMapping(value = "/{videoId}", produces = "video/mp4")
    public Mono<Resource> streamVideo(@PathVariable String videoId) {
        return streamingService.stream(videoId);
    }
}
