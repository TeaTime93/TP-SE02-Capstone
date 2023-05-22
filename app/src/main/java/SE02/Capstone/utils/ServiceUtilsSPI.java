package SE02.Capstone.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public final class ServiceUtilsSPI {

    static final int USER_ID_LENGTH = 5;
    private static final Pattern INVALID_CHARACTER_PATTERN = Pattern.compile("[\"'\\\\]");


    private ServiceUtilsSPI() {
    }

    public static boolean isValidString(String stringToValidate) {
        if (StringUtils.isBlank(stringToValidate)) {
            return false;
        } else {
            return !INVALID_CHARACTER_PATTERN.matcher(stringToValidate).find();
        }
    }

    public static String generateUserId() {
        return RandomStringUtils.randomAlphanumeric(5);
    }
}