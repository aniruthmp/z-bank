package io.pivotal.account.util;

import java.util.Arrays;
import java.util.List;

public @interface AccountConstants {

    public final String ACCOUNT_ROOT = "/api/account";
    public final String ACCOUNTS = "/accounts";
    public final String BANKID = "/{bankId}";
    public final String ACCOUNT = "/account" + BANKID;
    public final String RANDOM_ACCOUNT = "/account/random" + BANKID;

}
