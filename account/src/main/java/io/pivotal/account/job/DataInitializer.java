package io.pivotal.account.job;

import com.github.javafaker.Company;
import com.github.javafaker.Faker;
import io.pivotal.account.domain.Account;
import io.pivotal.account.domain.Details;
import io.pivotal.account.domain.Transaction;
import io.pivotal.account.repository.AccountRepo;
import io.pivotal.account.repository.TransactionRepo;
import io.pivotal.account.util.AccountConstants.ACCOUNT_TYPE;
import io.pivotal.account.util.AccountConstants.TRANSACTION_STATUS;
import io.pivotal.account.util.AccountConstants.TRANSACTION_TYPE;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Precision;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@Profile("!test")
public class DataInitializer implements CommandLineRunner {
    private final short MAX_ACCOUNTS = 100;
    private final short MAX_TRANSACTIONS = 10000;
    private AccountRepo accountRepo;
    private TransactionRepo transactionRepo;

    public DataInitializer(AccountRepo accountRepo, TransactionRepo transactionRepo) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        ACCOUNT_TYPE accountTypes[] = ACCOUNT_TYPE.values();
        List<Account> accounts = new ArrayList<>(MAX_ACCOUNTS);
        for (short i = 0; i < MAX_ACCOUNTS; i++) {
            Account account = Account.builder()
                    .number(faker.number().randomNumber(10, true))
                    .bankId(faker.finance().bic())
                    .createdAt(faker.date().between(faker.date().birthday(), new Date()))
                    .postedBalance(Precision.round(faker.number().randomDouble(2, 10000L, 99999L), 2))
                    .pendingCredits(0L)
                    .pendingDebits(0L)
                    .build();
            accounts.add(i, account);
        }
        StopWatch stopWatch = new StopWatch("Stop Watch for DataInitializer");

        stopWatch.start("Database clean up Task");
        transactionRepo.deleteAll();
        accountRepo.deleteAll();
        stopWatch.stop();

        stopWatch.start("Data Initializer Task");
        accounts.parallelStream().forEach(account -> {
            List<Transaction> transactions = new ArrayList<>();
            for (int i = 0; i < faker.number().numberBetween(100, MAX_TRANSACTIONS); i++) {
                Company company = faker.company();
                Details details = Details.builder()
                        .type(faker.random().nextBoolean() ? TRANSACTION_TYPE.CREDIT : TRANSACTION_TYPE.DEBIT)
                        .vendor(company.name())
                        .vendorUrl(company.url())
                        .description(company.buzzword())
                        .posted(faker.date().between(account.getCreatedAt(), new Date()))
                        .value(Precision.round(faker.number().randomDouble(2, 100L, 9999L), 2))
                        .build();
                Transaction transaction = Transaction.builder()
                        .accountNumber(faker.random().nextBoolean() ? account.getNumber() : faker.number().randomNumber(10, true))
                        .bankId(account.getBankId())
                        .status(faker.random().nextBoolean() ? TRANSACTION_STATUS.PENDING : TRANSACTION_STATUS.COMPLETE)
                        .details(details)
                        .createdAt(details.getPosted())
                        .build();
                transactions.add(transactionRepo.save(transaction));

                //Now update the right values to the account
                if (transaction.getStatus() == TRANSACTION_STATUS.PENDING &&
                        account.getNumber() == transaction.getAccountNumber()) {
                    switch (transaction.getDetails().getType()) {
                        case DEBIT:
                            account.setPendingDebits(Precision.round(account.getPendingDebits() + details.getValue(), 2));
                            break;
                        case CREDIT:
                            account.setPendingCredits(Precision.round(account.getPendingCredits() + details.getValue(), 2));
                            break;
                    }
                    account.setAvailableBalance(Precision.round((account.getPostedBalance() +
                            account.getPendingCredits() - account.getPendingDebits()), 2));
                }
            }

            account = account.toBuilder()
                    .label(faker.ancient().hero())
                    .type(accountTypes[faker.number().numberBetween(0, 4)])
                    .bankId(faker.finance().bic())
                    .primaryOwner(faker.lordOfTheRings().character())
                    .secondaryOwner(faker.random().nextBoolean() ? faker.ancient().god() : null)
                    .IBAN(faker.finance().iban())
                    .build();
            log.info("Account {} saved", accountRepo.save(account));
        });
        stopWatch.stop();
        log.info(stopWatch.toString());
    }
}
