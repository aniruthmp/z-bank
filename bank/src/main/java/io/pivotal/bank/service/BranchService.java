package io.pivotal.bank.service;

import io.pivotal.bank.domain.Branch;
import io.pivotal.bank.repository.BranchRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BranchService {

    private BranchRepo branchRepo;

    public BranchService(BranchRepo branchRepo) {
        this.branchRepo = branchRepo;
    }

    public Flux<Branch> allBranches() {
        return branchRepo.findAll();
    }

    public Mono<Void> deleteBranches() {
        return branchRepo.deleteAll();
    }
}
