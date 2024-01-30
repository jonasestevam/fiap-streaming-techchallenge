package com.jonasestevam.streaming.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import com.jonasestevam.streaming.dto.FilterDTO;
import com.jonasestevam.streaming.dto.VideoDTO;
import com.jonasestevam.streaming.mapper.VideoMapper;
import com.jonasestevam.streaming.model.Video;
import com.jonasestevam.streaming.repository.VideoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VideoService {

    @Value("${upload.directory}")
    private String VIDEO_DIRECTORY;

    @Autowired
    VideoRepository repository;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    VideoMapper mapper;

    @Autowired
    MongoService mongoService;

    public Mono<VideoDTO> uploadVideo(Mono<FilePart> fluxPart, Mono<VideoDTO> videoDTO) {
        return videoDTO
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(video -> {
                    video.setLink("/stream/" + video.getId());
                    return video;
                })
                .flatMap(savedVideo -> {
                    return fluxPart.flatMap(e -> {
                        String fileName = savedVideo.getId() + "." + FilenameUtils.getExtension(e.filename());
                        Path filePath = Paths.get(VIDEO_DIRECTORY, fileName);
                        return e.transferTo(filePath).thenReturn(savedVideo);
                    });
                })
                .flatMap(savedVideo -> {
                    return this.updateVideo(mapper.toDto(savedVideo));
                })
                .doOnError(e -> e.printStackTrace())
                .map(mapper::toDto);
    }

    public Mono<Void> deleteVideo(String id) {
        return checkIfExists(id)
                .then(Mono.defer(() -> {
                    try {
                        Files.deleteIfExists(Paths.get(generateFilePath(id)));
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to delete file", e);
                    }
                    return repository.deleteById(id);
                }));
    }

    public Mono<Video> updateVideo(VideoDTO videoDTO) {
        return checkIfExists(videoDTO.getId())
                .then(Mono.defer(() -> {
                    return repository.save(mapper.toEntity(videoDTO));
                }));
    }

    public Flux<VideoDTO> getVideos(FilterDTO filter) {

        Query query = new Query();

        if (filter.getTitle() != null) {
            query.addCriteria(
                    Criteria.where("title").regex(Pattern.compile(filter.getTitle(), Pattern.CASE_INSENSITIVE)));
        }

        if (filter.getPublishedAt() != null) {
            LocalDateTime startOfDay = LocalDateTime.parse(filter.getPublishedAt())
                    .withHour(0)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);
            LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);
            query.addCriteria(Criteria.where("publishedAt").gte(startOfDay).lt(endOfDay));
        }

        if (filter.getCategory() != null) {
            query.addCriteria(Criteria.where("category").is(filter.getCategory()));
        }

        query.with(createPageable(filter));

        return mongoService.getReactiveMongoTemplate().find(query, Video.class).map(mapper::toDto);

    }

    private Pageable createPageable(FilterDTO filter) {
        return PageRequest.of(filter.getPageNumber(), filter.getPageSize(),
                filter.getSortDirection() >= 0 ? Sort.by(filter.getSortBy()).descending()
                        : Sort.by(filter.getSortBy()).ascending());

    }

    public Mono<VideoDTO> getById(String id) {
        return repository.findById(id).map(mapper::toDto);
    }

    private String generateFilePath(String videoId) {
        return VIDEO_DIRECTORY + "/" + videoId + ".mp4";
    }

    private Mono<Void> checkIfExists(String id) {
        return repository.existsById(id).flatMap(exists -> exists ? Mono.empty() : Mono.error(new NotFoundException()));
    }

}
