package io.pivotal.bank.util;

import java.util.Arrays;
import java.util.List;

public @interface BankConstants {

    public final List<String> lobby = Arrays.asList("Monday: 09:00 - 16:30</br>Tuesday: 09:00 - 16:30</br>Wednesday: 09:30 - 16:30</br>Thursday: 09:00 - 16:30</br>Friday: 09:00 - 16:30</br>Saturday: 09:00 - 12:30</br>Sunday: Closed",
            "Monday: 09:30 - 15:00</br>Tuesday: 09:30 - 15:00</br>Wednesday: 09:30 - 15:00</br>Thursday: 10:00 - 15:00</br>Friday: 09:30 - 15:00</br>Saturday: Closed</br>Sunday: Closed",
            "Monday: 09:00 - 17:00</br>Tuesday: 09:00 - 17:00</br>Wednesday: 09:30 - 17:00</br>Thursday: 09:00 - 17:00</br>Friday: 09:00 - 17:00</br>Saturday: 09:00 - 13:00</br>Sunday: Closed");
    public final List<String> driveUp = Arrays.asList("09:00 - 16:30", "09:30 - 15:00", "10:00 - 17:00", "10:00 - 13:00");

    public final String BRANCH_ROOT = "/api/bank";
    public final String BRANCHES = "/branches";
    public final String BANKID = "/{bankId}";
    public final String BRANCH = "/branch" + BANKID;
    public final String RANDOM_BRANCH = "/branch/random" + BANKID;

}
