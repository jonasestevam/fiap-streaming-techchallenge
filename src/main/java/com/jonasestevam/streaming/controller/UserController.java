package com.jonasestevam.streaming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jonasestevam.streaming.dto.UserDTO;
import com.jonasestevam.streaming.services.UserService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Mono<UserDTO> createUser(@RequestBody @Valid Mono<UserDTO> user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public Mono<UserDTO> getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public Mono<UserDTO> updateUser(@PathVariable String id, @RequestBody @Valid Mono<UserDTO> user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}/favorite/{videoId}")
    public Mono<UserDTO> addFavorite(@PathVariable String id, @PathVariable String videoId) {
        return userService.addFavorite(id, videoId);
    }

}