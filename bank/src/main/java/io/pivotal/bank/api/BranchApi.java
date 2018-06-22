package io.pivotal.bank.api;

import io.pivotal.bank.domain.Branch;
import io.pivotal.bank.service.BranchService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static io.pivotal.bank.util.BankConstants.*;

@RestController
@RequestMapping(value = BRANCH_ROOT)
public class BranchApi {

    private BranchService branchService;

    public BranchApi(BranchService branchService) {
        this.branchService = branchService;
    }

    @PutMapping(value = RANDOM_BRANCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Branch> randomBranch(@PathVariable(value = "bankId") String bankId) {
        return branchService.createBranch(bankId);
    }

    @GetMapping(value = BRANCHES, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Branch> allBranches() {
        return branchService.allBranches();
    }

    @GetMapping(value = BRANCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Branch> getBranch(@PathVariable(value = "bankId") String bankId) {
        return branchService.getBranch(bankId);
    }

    @DeleteMapping(value = BRANCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBranch(@PathVariable(value = "bankId") String bankId) {
        branchService.deleteBranch(bankId);
    }

    @DeleteMapping(value = BRANCHES, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> deleteBranches() {
        return branchService.deleteBranches();
    }

}
