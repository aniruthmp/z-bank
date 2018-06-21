package io.pivotal.profile.repository;

import io.pivotal.profile.domain.Contact;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ContactRepo extends ReactiveCrudRepository<Contact, String> {

    Mono<Contact> findByUserId(String userId);
}
