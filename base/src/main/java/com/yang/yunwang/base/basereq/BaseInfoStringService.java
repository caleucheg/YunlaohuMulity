package com.yang.yunwang.base.basereq;

import android.content.Context;

import com.yang.yunwang.base.ret.HttpsStringServiceGenerator;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/6/11.
 */

public class BaseInfoStringService {
    private static BaseInfoStringService balanceModel;
    private ApiBaseString mBalanceService;

    /**
     * Singleton
     */
    public static BaseInfoStringService getInstance(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT , int CONNECT_TIMEOUT) {
        if (balanceModel == null) {
            balanceModel = new BaseInfoStringService(context, READ_TIMEOUT,  WRIT_TIMEOUT , CONNECT_TIMEOUT);
        }
        return balanceModel;
    }

    public static BaseInfoStringService getInstance(Context context) {
        if (balanceModel == null) {
            balanceModel = new BaseInfoStringService(context);
        }
        return balanceModel;
    }
    private BaseInfoStringService(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT , int CONNECT_TIMEOUT) {
        mBalanceService =  HttpsStringServiceGenerator.createService(ApiBaseString.class, READ_TIMEOUT,  WRIT_TIMEOUT , CONNECT_TIMEOUT);
    }

    private BaseInfoStringService(Context context) {
        mBalanceService =  HttpsStringServiceGenerator.createService(ApiBaseString.class);
    }

    public Observable<String> reinsertLog(String accessToken) {
        return mBalanceService.reinsertLog(accessToken);
    }
}
