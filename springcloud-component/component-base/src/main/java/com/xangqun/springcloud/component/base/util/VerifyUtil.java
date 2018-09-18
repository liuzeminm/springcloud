package com.xangqun.springcloud.component.base.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VerifyUtil {

    private static String REGEX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
    private static Pattern PHONE_PATTERN = Pattern.compile(REGEX);

    private static String EMAIL_REGEX =  "^[0-9a-zA-Z\\.\\-_]+@[0-9a-zA-Z\\-\\_]+\\.[a-zA-Z0-9_\\-\\.]+$";
    private static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public final static boolean isNumberNotLower0(Number number) {
        if (number == null) {
            return false;
        }
        if (number.longValue() < 0) {
            return false;
        }
        return true;

    }

    public final static boolean isPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        if (phone.length() != 11) {
            return false;
        }
        if (!phone.startsWith("1")) {
            return false;
        }
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    public final static boolean isEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String email = "sdfg%%%%sf.324.2gdf@qq.com";
        System.err.println(isEmail(email));
    }

    public final static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        }
        if (obj1 == null && obj2 != null) {
            return false;
        }
        return obj1.equals(obj2);
    }

    public final static boolean isNumberLower0(Number number) {
        return !isNumberNotLower0(number);

    }

    public final static boolean isNumberGt0(Number number) {
        if (number == null) {
            return false;
        }
        if (number.longValue() <= 0) {
            return false;
        }
        return true;
    }

    public final static boolean isNull(Object obj) {
        return !VerifyUtil.isNotNull(obj);
    }

    public final static boolean isNotNull(Object obj) {
        if (obj == null) {
            return false;
        }
        return true;
    }

    public final static boolean isNotEmpty(String string) {
        if (StringUtils.isEmpty(string)) {
            return false;
        }
        return true;
    }

    public final static boolean isNotEmpty(List<?> list) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        return true;
    }

    public final static boolean isEmpty(List<?> list) {
        return !VerifyUtil.isNotEmpty(list);
    }

    public final static boolean isEmpty(Object[] array) {
        if (ArrayUtils.isEmpty(array)) {
            return true;
        }
        return false;
    }

    public final static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public final static boolean containChineseCharacter(String text) {
        char[] charArray = text.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
                // Java判断一个字符串是否有中文是利用Unicode编码来判断，
                // 因为中文的编码区间为：0x4e00--0x9fbb
                return true;
            }
        }
        return false;
    }

}
