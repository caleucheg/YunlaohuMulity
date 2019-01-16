package com.yang.yunwang.base.util;

import android.content.Context;
import android.content.SharedPreferences;

public class CommonShare {

    private static final String LOGIN_DATA = "login_data";
    private static final String HOME_DATA = "home_data";
    private static final String TYPE_DATA = "type_data";
    private static final String PASSAGE_DATA = "passage_data";
    private static final String PASSAGE_DATA_K = "passage_data_k";
    private static final String PASSAGE_DATA_Z = "passage_data_z";
    private static final String PASSAGE_DATA_K_Z = "passage_data_k_z";
    private static final String TYPE_DATA_K = "type_data_k";
    private static final String VERSION_S = "version_s";
    private static final String VERSION_S_K = "version_s_k";
    private static final String REMEMBER_B = "remember_b";
    private static final String REMEMBER_B_K = "remember_b_k";
    private static final String REMEMBER_N = "remember_n";
    private static final String REMEMBER_N_K = "remember_n_k";
    private static final String REMEMBER_P = "remember_p";
    private static final String REMEMBER_P_K = "remember_p_k";
    private static final String REMEMBER_POS = "remember_pos";
    private static final String REMEMBER_POS_K = "remember_pos_k";
    private static final String LAST_LOGIN_TIME = "last_login";
    private static final String LAST_LOGIN_TIME_K = "last_login_k";

    private static final String StrogeB = "StrogeB";
    private static final String StrogeB_K = "StrogeB_k";
    private static final String WxType = "WxType";
    private static final String WxType_K = "WxType_k";
    private static final String ZfbType = "ZfbType";
    private static final String ZfbType_K = "ZfbType_k";
    private static String JPushID = "JPushID";
    private static String JPushID_K = "JPushID_K";
    private static String JPushIDBoolean = "JPushIDBoolean";
    private static String JPushIDBoolean_K = "JPushIDBoolean_K";
    private static String CleanTagsBoolean = "CleanTags";
    private static String CleanTagsBoolean_K = "CleanTags_K";
    private static String PermissionBoolean = "PermissionBoolean";
    private static String PermissionBoolean_K = "PermissionBoolean_K";


//
//    private static final String ACCOUNT_DATA = "account_data";

    public static void putLoginData(Context context, String[] key, String[] value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (key.length == value.length) {
            for (int i = 0; i < key.length; i++) {
                edit.putString(key[i], value[i]);
            }
        }
        edit.commit();
    }

    public static String getLoginData(Context context, String key, String default_value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, default_value) == null ? "" : sharedPreferences.getString(key, default_value);
    }

    public static void putHomeData(Context context, String[] key, String[] value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(HOME_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (key.length == value.length) {
            for (int i = 0; i < key.length; i++) {
                edit.putString(key[i], value[i]);
            }
        }
        edit.commit();
    }

    public static void putTypeData(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TYPE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(TYPE_DATA_K, value);
        edit.commit();
    }

    public static String getTypeData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TYPE_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TYPE_DATA_K, "-1") == null ? "-1" : sharedPreferences.getString(TYPE_DATA_K, "-1");
    }

    public static String getHomeData(Context context, String key, String default_value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(HOME_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, default_value) == null ? "" : sharedPreferences.getString(key, default_value);
    }

//    public static void putAccountData(Context context, String[] key, String[] value) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(ACCOUNT_DATA, Context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sharedPreferences.edit();
//        if (key.length == value.length) {
//            for (int i = 0; i < key.length; i++) {
//                edit.putString(key[i], value[i]);
//            }
//        }
//        edit.commit();
//    }
//
//    public static String getAccountData(Context context, String key, String default_value) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(ACCOUNT_DATA, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(key, default_value) == null ? "" : sharedPreferences.getString(key, default_value);
//    }

    public static void clear(Context context) {
        SharedPreferences sharedPreferences_login = context.getSharedPreferences(LOGIN_DATA, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences_home = context.getSharedPreferences(HOME_DATA, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = context.getSharedPreferences(TYPE_DATA, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences_v = context.getSharedPreferences(VERSION_S, Context.MODE_PRIVATE);
//        JPushInterface.stopPush(context);
        SharedPreferences JPushIDa = context.getSharedPreferences(JPushID, Context.MODE_PRIVATE);
        SharedPreferences JPushIDBooleana = context.getSharedPreferences(JPushIDBoolean, Context.MODE_PRIVATE);
        SharedPreferences CleanTagsBooleana = context.getSharedPreferences(CleanTagsBoolean, Context.MODE_PRIVATE);
        SharedPreferences stroage_b = context.getSharedPreferences(StrogeB, Context.MODE_PRIVATE);
        SharedPreferences wxPay = context.getSharedPreferences(WxType, Context.MODE_PRIVATE);
        SharedPreferences zfbPay = context.getSharedPreferences(ZfbType, Context.MODE_PRIVATE);
//        SharedPreferences sharedPreferencesB = context.getSharedPreferences(REMEMBER_B, Context.MODE_PRIVATE);
//        SharedPreferences sharedPreferencesN = context.getSharedPreferences(REMEMBER_N, Context.MODE_PRIVATE);
//        SharedPreferences sharedPreferencesP = context.getSharedPreferences(REMEMBER_P, Context.MODE_PRIVATE);
//        SharedPreferences sharedPreferencesPos = context.getSharedPreferences(REMEMBER_POS, Context.MODE_PRIVATE);
//        sharedPreferencesPos.edit().clear().commit();
//        sharedPreferencesB.edit().clear().commit();
//        sharedPreferencesN.edit().clear().commit();
//        sharedPreferencesP.edit().clear().commit();
        wxPay.edit().clear().commit();
        zfbPay.edit().clear().commit();
        stroage_b.edit().clear().commit();
        CleanTagsBooleana.edit().clear().commit();
        JPushIDa.edit().clear().commit();
        JPushIDBooleana.edit().clear().commit();
        sharedPreferences_v.edit().clear().commit();
        sharedPreferences.edit().clear().commit();
        sharedPreferences_login.edit().clear().commit();
        sharedPreferences_home.edit().clear().commit();
//        SharedPreferences sharedPreferences_account = context.getSharedPreferences(ACCOUNT_DATA, Context.MODE_PRIVATE);
//        sharedPreferences_account.edit().clear().commit();
    }

    public static void putPassageDataW(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PASSAGE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(PASSAGE_DATA_K, value);
        edit.commit();
    }

    public static String getPassageDataW(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PASSAGE_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASSAGE_DATA_K, "") == null ? "" : sharedPreferences.getString(PASSAGE_DATA_K, "");
    }

    public static void putPassageDataZ(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PASSAGE_DATA_Z, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(PASSAGE_DATA_K_Z, value);
        edit.commit();
    }

    public static String getPassageDataZ(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PASSAGE_DATA_Z, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASSAGE_DATA_K, "-1") == null ? "-1" : sharedPreferences.getString(PASSAGE_DATA_K_Z, "-1");
    }

    public static void putVersionCode(Context context, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(VERSION_S, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(VERSION_S_K, value);
        edit.commit();
    }

    public static boolean getVersionCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(VERSION_S, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(VERSION_S_K, false);
    }

    public static void putJPushID(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(JPushID, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(JPushID_K, value);
        edit.commit();
    }

    public static String getJpushID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(JPushID, Context.MODE_PRIVATE);
        return sharedPreferences.getString(JPushID_K, "");
    }

    public static void putJPushIDBoolean(Context context, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(JPushIDBoolean, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(JPushIDBoolean_K, value);
        edit.commit();
    }

    public static boolean getJpushIDBoolean(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(JPushIDBoolean, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(JPushIDBoolean_K, false);
    }

    public static void putCleanTagsBoolean(Context context, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CleanTagsBoolean, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(CleanTagsBoolean_K, value);
        edit.commit();
    }

    public static boolean getCleanTagsBoolean(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CleanTagsBoolean, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(CleanTagsBoolean_K, false);
    }

    public static void putStrogeB(Context context, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(StrogeB, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(StrogeB_K, value);
        edit.commit();
    }

    public static boolean getStrogeB(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(StrogeB, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(StrogeB_K, false);
    }

    public static void putWxType(Context context, String getedWxType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WxType, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WxType_K, getedWxType);
        edit.commit();
    }

    public static void putZfbType(Context context, String getedZfbType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ZfbType, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(ZfbType_K, getedZfbType);
        edit.commit();
    }

    public static String getWxType(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WxType, Context.MODE_PRIVATE);
        return sharedPreferences.getString(WxType_K, "");
    }

    public static String getZfbType(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ZfbType, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ZfbType_K, "");
    }

    public static boolean getPermissionBoolean(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PermissionBoolean, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(PermissionBoolean_K, false);
    }

    public static void putPermissionBoolean(Context context, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PermissionBoolean, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(PermissionBoolean_K, value);
        edit.commit();
    }

    public static void putBooleanRememberPwd(Context context, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(REMEMBER_B, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(REMEMBER_B_K, value);
        edit.commit();
    }

    public static boolean getBooleanRememberPwd(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(REMEMBER_B, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(REMEMBER_B_K, false);
    }

    public static void putRememberPwd(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(REMEMBER_P, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(REMEMBER_P_K, value);
        edit.commit();
    }

    public static String getRememberPwd(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(REMEMBER_P, Context.MODE_PRIVATE);
        return sharedPreferences.getString(REMEMBER_P_K, "");
    }

    public static void putRememberName(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(REMEMBER_N, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(REMEMBER_N_K, value);
        edit.commit();
    }

    public static String getRememberName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(REMEMBER_N, Context.MODE_PRIVATE);
        return sharedPreferences.getString(REMEMBER_N_K, "");
    }

    public static void putRememberPos(Context context, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(REMEMBER_POS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(REMEMBER_POS_K, value);
        edit.commit();
    }

    public static int getRememberPos(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(REMEMBER_POS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(REMEMBER_POS_K, -1);
    }

    public static void putLastLoginTime(Context context, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LAST_LOGIN_TIME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(LAST_LOGIN_TIME_K, value);
        edit.commit();
    }

    public static long getLastLoginTime(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LAST_LOGIN_TIME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(LAST_LOGIN_TIME_K, 0);
    }

    public static void clearLogin(Context context) {
        SharedPreferences sharedPreferencesPos = context.getSharedPreferences(REMEMBER_POS, Context.MODE_PRIVATE);
        sharedPreferencesPos.edit().clear().commit();
        SharedPreferences sharedPreferencesIsRem = context.getSharedPreferences(REMEMBER_B, Context.MODE_PRIVATE);
        sharedPreferencesIsRem.edit().clear().commit();
        SharedPreferences sharedPreferencesName = context.getSharedPreferences(REMEMBER_N, Context.MODE_PRIVATE);
        sharedPreferencesName.edit().clear().commit();
        SharedPreferences sharedPreferencesPwd = context.getSharedPreferences(REMEMBER_P, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = context.getSharedPreferences(LAST_LOGIN_TIME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        sharedPreferencesPwd.edit().clear().commit();
    }
}
