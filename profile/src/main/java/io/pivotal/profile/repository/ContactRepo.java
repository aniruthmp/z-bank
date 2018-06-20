package io.pivotal.profile.repository;

import io.pivotal.profile.domain.Contact;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends ReactiveCrudRepository<Contact, String> {
}
