package io.pivotal.account.service;

import com.github.javafaker.Faker;
import io.pivotal.account.domain.Account;
import io.pivotal.account.repository.AccountRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class AccountService {

    private AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
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
        return accountRepo.getAccountByBankId(bankId);
    }

    public void deleteAccountByBankId(String bankId) {
        log.info("Came inside deleteAccount for bankId: {}", bankId);
        accountRepo.deleteAccountByBankId(bankId);
    }

    public void deleteAccounts() {
        accountRepo.deleteAll();
    }
}
