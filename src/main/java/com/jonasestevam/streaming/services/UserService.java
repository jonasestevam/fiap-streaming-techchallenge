package com.jonasestevam.streaming.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.jonasestevam.streaming.dto.UserDTO;
import com.jonasestevam.streaming.mapper.UserMapper;
import com.jonasestevam.streaming.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    UserMapper mapper;

    public Mono<UserDTO> createUser(Mono<UserDTO> userDTO) {
        return userDTO
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

    public Mono<UserDTO> getUser(String id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    public Mono<UserDTO> updateUser(String id, Mono<UserDTO> user) {
        return checkIfExists(id).then(Mono.defer(() -> {

            return user.map(mapper::toEntity).flatMap((userEntity) -> {
                userEntity.setId(id);
                return repository.save(userEntity);
            }).map(mapper::toDto);
        }));
    }

    public Mono<Void> deleteUser(String id) {
        return checkIfExists(id).then(Mono.defer(() -> {
            return repository.deleteById(id);
        }));
    }

    private Mono<Void> checkIfExists(String id) {
        return repository.existsById(id).flatMap(exists -> exists ? Mono.empty() : Mono.error(new NotFoundException()));
    }

    public Mono<UserDTO> addFavorite(String id, String monoVideoId) {
        return getUser(id).flatMap(user -> {
            user.getFavoriteList().add(monoVideoId);
            return repository.save(mapper.toEntity(user)).map(mapper::toDto);
        });
    }
}