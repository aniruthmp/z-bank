package io.pivotal.account.api;

import io.pivotal.account.domain.Account;
import io.pivotal.account.domain.Transaction;
import io.pivotal.account.job.TransactionProcessor;
import io.pivotal.account.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.pivotal.account.util.AccountConstants.*;

@RestController
@RequestMapping(value = ACCOUNT_ROOT)
public class AccountApi {

    private AccountService accountService;
    private TransactionProcessor transactionProcessor;

    public AccountApi(AccountService accountService, TransactionProcessor transactionProcessor) {
        this.accountService = accountService;
        this.transactionProcessor = transactionProcessor;
    }

    @PutMapping(value = RANDOM_ACCOUNT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Account randomAccount(@PathVariable(value = "bankId") String bankId) {
        return accountService.createAccount(bankId);
    }

    @GetMapping(value = ACCOUNTS, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> allAccounts() {
        return accountService.allAccounts();
    }

    @GetMapping(value = ACCOUNT, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> getAccount(@PathVariable(value = "bankId") String bankId) {
        return accountService.getAccountsByBankId(bankId);
    }

    @GetMapping(value = USERID, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> getAccountByUserId(@PathVariable(value = "userId") String userId) {
        return accountService.getAccountByUserId(userId);
    }

    @DeleteMapping(value = ACCOUNT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void deleteAccount(@PathVariable(value = "bankId") String bankId) {
        accountService.deleteAccountByBankId(bankId);
    }

    @DeleteMapping(value = ACCOUNTS, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void deleteAccounts() {
        accountService.deleteAccounts();
    }

    @GetMapping(value = POLL, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transaction> pollTransaction() {
        return transactionProcessor.pollTransaction();
    }

    @PutMapping(value = RANDOM_TRANSACTION, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Transaction randomTransaction(@PathVariable(value = "number") long number) {
        return accountService.createTransaction(number);
    }

}
