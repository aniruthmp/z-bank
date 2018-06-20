package io.pivotal.profile.repository;

import io.pivotal.profile.domain.Contact;
import io.pivotal.profile.domain.Privacy;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivacyRepo extends ReactiveCrudRepository<Privacy, String> {
}
