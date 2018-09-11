package com.yang.yunwang.base.util;

import android.util.Log;

/**
 *  on 2017/4/17.
 *
 * description：
 * update by:
 * update day:
 */
public class LG {

    public static boolean isDebug = false;

    private static final String DETAULT_TAG = "---Module Sample Log>>>";

    public static void d(String msg) {
        d(DETAULT_TAG, msg);
    }

    private static void d(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void e(String msg) {
        e(DETAULT_TAG, msg);
    }

    private static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
}
