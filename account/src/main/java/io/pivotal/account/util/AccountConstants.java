package io.pivotal.account.util;

public @interface AccountConstants {

    public final String ACCOUNT_ROOT = "/api/account";
    public final String ACCOUNTS = "/accounts";
    public final String BANKID = "/{bankId}";
    public final String ACCOUNT = "/account" + BANKID;
    public final String RANDOM_ACCOUNT = "/account/random" + BANKID;
    public final String POLL = "/poll";

    public enum ACCOUNT_TYPE {CHECKING, SAVING, CD, MONEY_MARKET, IRA};
    public enum TRANSACTION_TYPE {DEBIT, CREDIT};
    public enum TRANSACTION_STATUS {PENDING, COMPLETE, ERROR};

}
