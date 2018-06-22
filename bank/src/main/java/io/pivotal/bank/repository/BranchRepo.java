package io.pivotal.bank.repository;

import io.pivotal.bank.domain.Branch;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BranchRepo extends ReactiveCrudRepository<Branch, String> {
    Mono<Branch> findBranchByBankId(String bankId);
}
