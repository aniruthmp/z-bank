package io.pivotal.account.util;

import org.apache.commons.math3.util.Precision;

public class AccountUtil {

    public static double getDoubleWithPrecision(double inValue){
        return Precision.round(inValue, 2);
    }
}
