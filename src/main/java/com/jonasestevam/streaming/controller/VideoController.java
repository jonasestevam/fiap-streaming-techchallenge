package com.jonasestevam.streaming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.jonasestevam.streaming.dto.FilterDTO;
import com.jonasestevam.streaming.dto.VideoDTO;
import com.jonasestevam.streaming.services.VideoService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("video")
public class VideoController {

    @Autowired
    VideoService videoService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<?> handleFileUpload(@RequestPart("file") Mono<FilePart> filePartFlux,
            @RequestPart("video") @Valid Mono<VideoDTO> videoDTO) {

        return videoService.uploadVideo(filePartFlux, videoDTO).doOnError(e -> e.printStackTrace());
    }

    @GetMapping()
    public Flux<VideoDTO> getVideos(FilterDTO filter) {
        return videoService.getVideos(filter);
    }

    @GetMapping("/{id}")
    public Mono<VideoDTO> getVideos(@PathVariable String id) {
        return videoService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Mono<?> deleteVideo(@PathVariable String id) {
        return videoService.deleteVideo(id);
    }

    @PutMapping()
    public Mono<?> updateVideo(@RequestBody @Valid VideoDTO videoDTO) {
        return videoService.updateVideo(videoDTO);
    }

}
