package io.pivotal.account.repository;

import io.pivotal.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

    List<Account> getAccountByBankId(String bankId);
    void deleteAccountByBankId(String bankId);
}
