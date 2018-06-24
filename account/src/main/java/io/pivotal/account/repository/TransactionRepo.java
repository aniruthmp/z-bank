package io.pivotal.account.repository;

import io.pivotal.account.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.pivotal.account.util.AccountConstants.TRANSACTION_STATUS;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    List<Transaction> getTransactionsByStatusOrderByCreatedAtAsc(TRANSACTION_STATUS status);
}
