package com.teriley510.userservice.repository;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.teriley510.userservice.entity.User;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCosmosRepository<User, String> {
    Flux<User> findByFirstName(String firstName);

    Mono<User> findById(String id);



}
