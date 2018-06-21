package io.pivotal.bank.repository;

import io.pivotal.bank.domain.Branch;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepo extends ReactiveCrudRepository<Branch, String> {
}
