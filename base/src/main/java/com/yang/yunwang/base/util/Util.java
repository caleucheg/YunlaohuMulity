package com.yang.yunwang.base.util;

import android.text.TextUtils;

/**
 *  on 2017/4/13.
 *
 * description：
 * update by:
 * update day:
 */
public class Util {
    public static boolean isNull(String... args) {
        if (args == null) return true;
        for (String item : args) {
            if (TextUtils.isEmpty(item)) return true;
        }
        return false;
    }
}
