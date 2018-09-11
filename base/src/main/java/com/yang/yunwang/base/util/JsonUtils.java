package com.yang.yunwang.base.util;

import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;


/**
 * Created by Administrator on 2018/1/23.
 */

public class JsonUtils {
    public static boolean isBadJson(String json) {
        return !isGoodJson(json);
    }

    public static boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
//    public static boolean isXML(String value) {
//        try {
//            DocumentHelper.parseText(value);
//        } catch (DocumentException e) {
//            return false;
//        }
//        return true;
//    }
}
