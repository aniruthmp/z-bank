package io.pivotal.bank.job;

import com.github.javafaker.Faker;
import io.pivotal.bank.domain.Address;
import io.pivotal.bank.domain.Branch;
import io.pivotal.bank.domain.Hours;
import io.pivotal.bank.repository.BranchRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.UUID;

import static io.pivotal.bank.util.BankConstants.driveUp;
import static io.pivotal.bank.util.BankConstants.lobby;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final int MAX_RECORDS = 15;
    private BranchRepo branchRepo;

    public DataInitializer(BranchRepo branchRepo) {
        this.branchRepo = branchRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        StopWatch stopWatch = new StopWatch("Stop Watch for DataInitializer");
        stopWatch.start();
        log.info("Dummy branch records getting inserted..");
        for (int i = 0; i < MAX_RECORDS; i++) {
            Faker faker = new Faker();
            Branch branch = Branch.builder()
                    .bankId(UUID.randomUUID().toString())
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
            branchRepo.save(branch).block();
        }
        log.info(stopWatch.prettyPrint());
    }
}
