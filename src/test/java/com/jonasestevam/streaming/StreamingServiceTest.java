package com.jonasestevam.streaming;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

// FILEPATH: /C:/Users/jonas/Documents/streaming/src/test/java/com/jonasestevam/streaming/services/StreamingServiceTest.java

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.jonasestevam.streaming.services.StreamingService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class StreamingServiceTest {

    @Mock
    private ResourceLoader mockResourceLoader;

    @InjectMocks
    private StreamingService streamingService;

    @Test
    public void testStream() {
        // Arrange
        String videoId = "testVideoId";
        Resource mockResource = Mockito.mock(Resource.class);
        when(mockResourceLoader.getResource(anyString())).thenReturn(mockResource);

        // Act
        Mono<Resource> result = streamingService.stream(videoId);

        // Assert
        StepVerifier.create(result)
                .expectNext(mockResource)
                .verifyComplete();
    }
}