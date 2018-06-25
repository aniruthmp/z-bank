package io.pivotal.account.repository;

import io.pivotal.account.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    List<Transaction> getTop25TransactionsByStatusOrderByCreatedAtAsc(String status);
}
