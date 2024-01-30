
package com.jonasestevam.streaming;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jonasestevam.streaming.dto.UserDTO;
import com.jonasestevam.streaming.dto.VideoDTO;
import com.jonasestevam.streaming.enuns.CategoriesEnum;
import com.jonasestevam.streaming.mapper.UserMapper;
import com.jonasestevam.streaming.model.User;
import com.jonasestevam.streaming.repository.UserRepository;
import com.jonasestevam.streaming.services.UserService;
import com.jonasestevam.streaming.services.VideoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VideoService videoService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setup() {
        user = new User();
        userDTO = new UserDTO();
        userDTO.setFavoriteList(new HashSet<>());
    }

    @Test
    public void testCreateUser() {
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        Mono<UserDTO> result = userService.createUser(Mono.just(userDTO));

        StepVerifier.create(result)
                .expectNext(userDTO)
                .verifyComplete();
    }

    @Test
    public void testGetUser() {
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);
        when(userRepository.findById(anyString())).thenReturn(Mono.just(user));

        Mono<UserDTO> result = userService.getUser("testId");
        StepVerifier.create(result)
                .expectNext(userDTO)
                .verifyComplete();
    }

    @Test
    public void testUpdateUser() {
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);
        when(userRepository.existsById(anyString())).thenReturn(Mono.just(true));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        Mono<UserDTO> result = userService.updateUser("testId", Mono.just(userDTO));

        StepVerifier.create(result)
                .expectNext(userDTO)
                .verifyComplete();
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.existsById(anyString())).thenReturn(Mono.just(true));
        when(userRepository.deleteById(anyString())).thenReturn(Mono.empty());

        Mono<Void> result = userService.deleteUser("testId");

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    public void testAddFavorite() {

        when(userRepository.existsById(anyString())).thenReturn(Mono.just(true));
        when(userRepository.deleteById(anyString())).thenReturn(Mono.empty());

        Mono<Void> result = userService.deleteUser("testId");

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    public void testGetRecommendedVideos() {
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);
        when(userRepository.findById(anyString())).thenReturn(Mono.just(user));
        when(userRepository.findById(anyString())).thenReturn(Mono.just(user));
        when(videoService.getCategoriesByVideoId(any())).thenReturn(Flux.just(CategoriesEnum.ANIMAISNATUREZA));
        when(videoService.getByCategories(CategoriesEnum.ANIMAISNATUREZA)).thenReturn(Flux.just(new VideoDTO()));

        Flux<VideoDTO> result = userService.getRecommendedVideos("testId");

        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }
}