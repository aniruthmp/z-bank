package io.pivotal.profile.repository;

import io.pivotal.profile.domain.Privacy;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PrivacyRepo extends ReactiveCrudRepository<Privacy, String> {
    @Query("{ 'userId': ?0 }")
    Mono<Privacy> findByUserId(String userId);
}
