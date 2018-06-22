package io.pivotal.bank.service;

import com.github.javafaker.Faker;
import io.pivotal.bank.domain.Address;
import io.pivotal.bank.domain.Branch;
import io.pivotal.bank.domain.Hours;
import io.pivotal.bank.repository.BranchRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static io.pivotal.bank.util.BankConstants.driveUp;
import static io.pivotal.bank.util.BankConstants.lobby;


@Service
@Slf4j
public class BranchService {

    private BranchRepo branchRepo;
    private MongoOperations mongoOperations;

    public BranchService(BranchRepo branchRepo, MongoOperations mongoOperations) {
        this.branchRepo = branchRepo;
        this.mongoOperations = mongoOperations;
    }

    public Mono<Branch> createBranch(String bankId) {
        log.info("Came inside createBranch for bankId: {}", bankId);
        Faker faker = new Faker();
        Branch branch = Branch.builder()
                .bankId(bankId)
                .name(faker.lordOfTheRings().location())
                .address(Address.builder()
                        .addressLine1(faker.address().streetAddress())
                        .addressLine2(faker.address().secondaryAddress())
                        .city(faker.address().city())
                        .state(faker.address().state())
                        .zip(faker.address().zipCode())
                        .latitude(Double.valueOf(faker.address().latitude()))
                        .longitude(Double.valueOf(faker.address().longitude()))
                        .build())
                .hours(Hours.builder()
                        .lobby(lobby.get(faker.random().nextInt(0, lobby.size() - 1)))
                        .driveUp(driveUp.get(faker.random().nextInt(0, driveUp.size() - 1)))
                        .build())
                .build();
        return branchRepo.save(branch);
    }

    public Flux<Branch> allBranches() {
        return branchRepo.findAll();
    }

    public Mono<Void> deleteBranches() {
        return branchRepo.deleteAll();
    }

    public Mono<Branch> getBranch(String bankId) {
        return branchRepo.findBranchByBankId(bankId);
    }

    public void deleteBranch(String bankId) {
        log.info("Came inside deleteBranch for bankId: {}", bankId);

        Query deleteQuery = new Query();
        deleteQuery.addCriteria(Criteria.where("bankId").is(bankId));
        mongoOperations.findAllAndRemove(deleteQuery, Branch.class);
    }
}
