package io.pivotal.account.util;

import java.util.Arrays;
import java.util.List;

public @interface AccountConstants {

    public final String ACCOUNT_ROOT = "/api/account";
    public final String ACCOUNTS = "/accounts";
    public final String BANKID = "/{bankId}";
    public final String ACCOUNT = "/account" + BANKID;
    public final String USERID = "/{userId}";
    public final String RANDOM_ACCOUNT = "/account/random" + BANKID;
    public final String ACCOUNT_NUMBER = "/{number}";
    public final String RANDOM_TRANSACTION = "/transaction/random" + ACCOUNT_NUMBER;
    public final String POLL = "/poll";

    public final List<String> accountTypes = Arrays.asList("CHECKING", "SAVING", "CD", "MONEY_MARKET", "IRA");

    public final String Tx_DEBIT = "DEBIT";
    public final String Tx_CREDIT = "CREDIT";
    public final String Tx_PENDING = "PENDING";
    public final String Tx_COMPLETE = "COMPLETE";
    public final String Tx_ERROR = "ERROR";


}
