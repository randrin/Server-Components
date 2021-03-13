package com.nbp.bear.components.util;

import com.nbp.bear.components.constant.NbpConstant;
import org.apache.commons.lang3.RandomStringUtils;

public class NbpUtil {

    public static String NbpGenerateUserId() {
        return "NBP" + RandomStringUtils.random(6, NbpConstant.NBP_RANDOM_NUMBERS);
    }
}
