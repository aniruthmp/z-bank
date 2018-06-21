package io.pivotal.bank.api;

import io.pivotal.bank.domain.Branch;
import io.pivotal.bank.service.BranchService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/bank")
public class BranchApi {

    private BranchService branchService;

    public BranchApi(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping(value = "/branches", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Branch> allBranches() {
        return branchService.allBranches();
    }

    @DeleteMapping(value = "/branches", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> deleteBranches() {
        return branchService.deleteBranches();
    }

}
