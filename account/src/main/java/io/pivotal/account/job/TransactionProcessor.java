package io.pivotal.account.job;

import io.pivotal.account.domain.Account;
import io.pivotal.account.domain.Details;
import io.pivotal.account.domain.Transaction;
import io.pivotal.account.repository.AccountRepo;
import io.pivotal.account.repository.TransactionRepo;
import io.pivotal.account.util.AccountConstants.TRANSACTION_STATUS;
import io.pivotal.account.util.AccountConstants.TRANSACTION_TYPE;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class TransactionProcessor {

    private TransactionRepo transactionRepo;
    private AccountRepo accountRepo;

    public TransactionProcessor(TransactionRepo transactionRepo, AccountRepo accountRepo) {
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
    }

    /**
     * Poll health every 5 mins
     */
//    @Scheduled(cron = "${cron.poll-transaction}") //TODO remove this comment after testing
    @Transactional
    public List<Transaction> pollTransaction() {
        log.info("pollTransaction job started @ " + new Date());
        List<Transaction> transactions = null;
        try {
            transactions = transactionRepo.getTransactionsByStatusOrderByCreatedAtAsc(TRANSACTION_STATUS.PENDING);
            if (CollectionUtils.isNotEmpty(transactions)) {
                log.info("Total {} transactions got from the database is {}", TRANSACTION_STATUS.PENDING, transactions.size());

                transactions.forEach(transaction -> {
                    log.info("Processing {}", transaction.toString());
                    if (Objects.isNull(transaction.getDetails())) {
                        log.warn("Incorrect transaction with no details");
                        transaction.setStatus(TRANSACTION_STATUS.ERROR);
                        transaction.setUpdatedAt(new Date());
                        transactionRepo.save(transaction);
                    }

                    //First get the corresponding account
                    Account account = accountRepo.getAccountByNumber(transaction.getAccountNumber());
                    if (Objects.isNull(account)) {
                        log.warn("Incorrect accountNumber: {}. Flag this transaction as {}",
                                transaction.getAccountNumber(), TRANSACTION_STATUS.ERROR);
                        transaction.setStatus(TRANSACTION_STATUS.ERROR);
                        transaction.setUpdatedAt(new Date());
                        transactionRepo.save(transaction);
                    } else {
                        //Now calculate the balance(s)
                        Details details = transaction.getDetails();
                        TRANSACTION_TYPE type = details.getType();
                        switch (type) {
                            case DEBIT:
                                account.setPostedBalance(account.getPostedBalance() - details.getValue());
                                account.setPendingDebits(account.getPendingDebits() - details.getValue());
                                break;
                            case CREDIT:
                                account.setPostedBalance(account.getPostedBalance() + details.getValue());
                                account.setPendingCredits(account.getPendingCredits() - details.getValue());
                                break;
                        }
                        account.setAvailableBalance(account.getPostedBalance() +
                                account.getPendingCredits() - account.getPendingDebits());

                        //Set some auditing fields
                        account.setUpdatedAt(new Date());
                        transaction.setUpdatedAt(new Date());
                        transaction.setStatus(TRANSACTION_STATUS.COMPLETE);

                        //Pray and persist this into the database
                        log.info("Transaction Persisted {}", transactionRepo.save(transaction));
                        log.info("Account Persisted {}", accountRepo.save(account));
                    }
                });
            } else {
                log.warn("No {} transaction got from the database", TRANSACTION_STATUS.PENDING);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        log.info("pollHealth job completed @ " + new Date());
        return transactions;
    }
}
