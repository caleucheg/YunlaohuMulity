package com.yang.yunwang.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/9.
 */
public class PatternUtils {

    /**
     * 四位费率
     *
     * @param input 输入内容
     * @return
     */
    public static boolean isRateNumber(String input) {
        if (Double.parseDouble(input) == 0) {
            return true;
        } else {
            String regex = "^[0](\\.\\d{1,4})?$";
            Pattern p = Pattern.compile(regex);
            Matcher matcher = p.matcher(input);
            return matcher.matches();
        }
    }

    /**
     * 四位费率
     *
     * @param input 输入内容
     * @return
     */
    public static boolean isFourRateNumber(String input) {
        if (Double.parseDouble(input) == 0) {
            return true;
        } else {
        String regex = "^[0](\\.\\d{1,4})?$";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(input);
        return matcher.matches();
        }
    }

    /**
     * 传真号码
     *
     * @param input
     * @return
     */
    public static boolean isFaxNumber(String input) {
        String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(input);
        return matcher.matches();
    }
}
