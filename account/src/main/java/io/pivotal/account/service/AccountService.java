package io.pivotal.account.service;

import com.github.javafaker.Company;
import com.github.javafaker.Faker;
import io.pivotal.account.domain.Account;
import io.pivotal.account.domain.Details;
import io.pivotal.account.domain.Transaction;
import io.pivotal.account.repository.AccountRepo;
import io.pivotal.account.repository.TransactionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static io.pivotal.account.util.AccountConstants.*;
import static io.pivotal.account.util.AccountUtil.getDoubleWithPrecision;


@Service
@Slf4j
public class AccountService {

    private AccountRepo accountRepo;
    private TransactionRepo transactionRepo;

    public AccountService(AccountRepo accountRepo, TransactionRepo transactionRepo) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
    }

    public Account createAccount(String bankId) {
        log.info("Came inside createAccount for bankId: {}", bankId);
        Faker faker = new Faker();
        double postedBalance = faker.number().randomDouble(2, 10000L, 99999L);
        double pendingDebits = faker.number().randomDouble(2, 0L, 5000L);
        double pendingCredits = faker.number().randomDouble(2, -5000L, 50000L);
        double availableBalance = postedBalance + pendingCredits - pendingDebits;
        Account account = Account.builder()
                .number(System.currentTimeMillis())
                .label(faker.ancient().hero())
                .bankId(bankId)
                .userId(StringUtils.trimWhitespace(faker.random().nextBoolean() ?
                        faker.stock().nsdqSymbol() : faker.stock().nyseSymbol()))
                .postedBalance(postedBalance)
                .pendingDebits(pendingDebits)
                .pendingCredits(pendingCredits)
                .availableBalance(availableBalance)
                .primaryOwner(faker.lordOfTheRings().character())
                .secondaryOwner(faker.random().nextBoolean() ? faker.ancient().god() : null)
                .IBAN(faker.random().toString())
                .build();
        return accountRepo.save(account);
    }

    public List<Account> allAccounts() {
        return accountRepo.findAll();
    }

    public List<Account> getAccountsByBankId(String bankId) {
        log.info("Came inside getAccountsByBankId for bankId: {}", bankId);
        return accountRepo.getAccountByBankId(bankId);
    }

    public void deleteAccountByBankId(String bankId) {
        log.info("Came inside deleteAccount for bankId: {}", bankId);
        accountRepo.getAccountByBankId(bankId);
    }

    public void deleteAccounts() {
        accountRepo.deleteAll();
    }

    public Transaction createTransaction(long number) {
        log.info("Came inside createTransaction for accountNumber: {}", number);
        Transaction transaction = null;

        Account account = accountRepo.getAccountByNumber(number);
        if (Objects.isNull(account)) {
            log.warn("Incorrect accountNumber: {}", number);
        } else {
            Faker faker = new Faker();
            Company company = faker.company();
            Details details = Details.builder()
                    .type(faker.random().nextBoolean() ? Tx_CREDIT : Tx_DEBIT)
                    .vendor(company.name())
                    .vendorUrl(company.url())
                    .description(company.buzzword())
                    .posted(faker.date().between(account.getCreatedAt(), new Date()))
                    .value(getDoubleWithPrecision(faker.number().randomDouble(2, 100L, 9999L)))
                    .build();
            transaction = transactionRepo.save(Transaction.builder()
                    .accountNumber(number)
                    .bankId(account.getBankId())
                    .status(Tx_PENDING)
                    .details(details)
                    .createdAt(details.getPosted())
                    .build());
        }
        return transaction;
    }

    public List<Account> getAccountByUserId(String userId) {
        log.info("Came inside getAccountByUserId for userId: {}", userId);
        return accountRepo.getAccountByUserId(userId);
    }
}
