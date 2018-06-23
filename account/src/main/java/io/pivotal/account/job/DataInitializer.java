//package io.pivotal.account.job;
//
//import com.github.javafaker.Faker;
//import io.pivotal.account.domain.Account;
//import io.pivotal.account.repository.AccountRepo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StopWatch;
//
//import java.util.UUID;
//
//@Component
//@Slf4j
//public class DataInitializer implements CommandLineRunner {
//    private final int MAX_RECORDS = 5;
//    private AccountRepo accountRepo;
//
//    public DataInitializer(AccountRepo accountRepo) {
//        this.accountRepo = accountRepo;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        StopWatch stopWatch = new StopWatch("Stop Watch for DataInitializer");
//        stopWatch.start();
//        log.info("Dummy branch records getting inserted..");
//        for (int i = 0; i < MAX_RECORDS; i++) {
//            Faker faker = new Faker();
//            double postedBalance = faker.number().randomDouble(2, 10000L, 99999L);
//            double pendingDebits = faker.number().randomDouble(2, 0L, 5000L);
//            double pendingCredits = faker.number().randomDouble(2, -5000L, 50000L);
//            double availableBalance = postedBalance + pendingCredits - pendingDebits;
//            Account account = Account.builder()
//                    .number(faker.number().randomNumber())
//                    .label(faker.ancient().hero())
//                    .bankId(UUID.randomUUID().toString())
//                    .postedBalance(postedBalance)
//                    .pendingDebits(pendingDebits)
//                    .pendingCredits(pendingCredits)
//                    .availableBalance(availableBalance)
//                    .primaryOwner(faker.lordOfTheRings().character())
//                    .secondaryOwner(faker.random().nextBoolean() ? faker.ancient().god() : null)
//                    .IBAN(faker.random().toString())
//                    .build();
//            accountRepo.save(account);
//        }
//        log.info(stopWatch.prettyPrint());
//    }
//}
