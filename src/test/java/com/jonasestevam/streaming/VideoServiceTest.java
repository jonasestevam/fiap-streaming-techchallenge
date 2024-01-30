// FILEPATH: /C:/Users/jonas/Documents/streaming/src/test/java/com/jonasestevam/streaming/VideoServiceTest.java

package com.jonasestevam.streaming;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;

import com.jonasestevam.streaming.dto.VideoDTO;
import com.jonasestevam.streaming.enuns.CategoriesEnum;
import com.jonasestevam.streaming.mapper.VideoMapper;
import com.jonasestevam.streaming.model.Video;
import com.jonasestevam.streaming.repository.VideoRepository;
import com.jonasestevam.streaming.services.MongoService;
import com.jonasestevam.streaming.services.VideoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoMapper videoMapper;

    @Mock
    private MongoService mongoService;

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private FilePart filePart;

    @InjectMocks
    private VideoService videoService;

    private Video video;
    private VideoDTO videoDTO;

    @BeforeEach
    public void setup() {
        video = new Video();
        videoDTO = new VideoDTO();
    }

    @Test
    public void testUploadVideo() {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId("testId");
        videoDTO.setTitle("testTitle");
        videoDTO.setDescription("testDescription");
        Video video = new Video();
        video.setId(videoDTO.getId());
        video.setTitle(videoDTO.getTitle());
        video.setDescription(videoDTO.getDescription());

        FilePart filePart = mock(FilePart.class);
        when(filePart.filename()).thenReturn("test.mp4");

        when(videoMapper.toEntity(any(VideoDTO.class))).thenReturn(video);
        when(videoRepository.save(any(Video.class))).thenReturn(Mono.just(video));
        when(videoMapper.toDto(any(Video.class))).thenReturn(videoDTO);

        Mono<VideoDTO> result = videoService.uploadVideo(Mono.just(filePart), Mono.just(videoDTO));

        StepVerifier.create(result)
                .expectNextMatches(updatedVideoDTO -> updatedVideoDTO.getId().equals(videoDTO.getId())
                        && updatedVideoDTO.getTitle().equals(videoDTO.getTitle())
                        && updatedVideoDTO.getDescription().equals(videoDTO.getDescription()))
                .verifyComplete();
    }

    @Test
    public void testDeleteVideo() {
        when(videoRepository.existsById(anyString())).thenReturn(Mono.just(true));
        when(videoRepository.deleteById(anyString())).thenReturn(Mono.empty());

        Mono<Void> result = videoService.deleteVideo("testId");

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    public void testUpdateVideo() {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId("testId");
        Video video = new Video();
        video.setId(videoDTO.getId());

        when(videoMapper.toEntity(videoDTO)).thenReturn(video);
        when(videoRepository.existsById(videoDTO.getId())).thenReturn(Mono.just(true));
        when(videoRepository.save(video)).thenReturn(Mono.just(video));

        Mono<Video> result = videoService.updateVideo(videoDTO);

        StepVerifier.create(result)
                .expectNextMatches(updatedVideo -> updatedVideo.getId().equals(videoDTO.getId()))
                .verifyComplete();
    }

    @Test
    public void testGetById() {
        when(videoMapper.toDto(any(Video.class))).thenReturn(videoDTO);
        when(videoRepository.findById(anyString())).thenReturn(Mono.just(video));

        Mono<VideoDTO> result = videoService.getById("testId");

        StepVerifier.create(result)
                .expectNext(videoDTO)
                .verifyComplete();
    }

    @Test
    public void testGetCategoriesByVideoId() {
        Video video = new Video();
        video.setCategory(CategoriesEnum.ANIMAISNATUREZA);

        when(videoRepository.findByIdIn(any())).thenReturn(Flux.just(video));

        Flux<CategoriesEnum> result = videoService.getCategoriesByVideoId(new HashSet<>());

        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void testGetByCategories() {
        when(videoMapper.toDto(any(Video.class))).thenReturn(videoDTO);
        when(videoRepository.findByCategory(any())).thenReturn(Flux.just(video));

        Flux<VideoDTO> result = videoService.getByCategories(CategoriesEnum.ANIMAISNATUREZA);

        StepVerifier.create(result)
                .expectNext(videoDTO)
                .verifyComplete();
    }
}