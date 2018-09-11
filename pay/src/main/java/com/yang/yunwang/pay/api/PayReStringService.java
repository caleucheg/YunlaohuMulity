package com.yang.yunwang.pay.api;

import android.content.Context;

import com.yang.yunwang.base.ret.HttpsStringServiceGenerator;
import com.yang.yunwang.pay.api.scan.insertorder.InsertOrderLogReq;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/7/19.
 */

public class PayReStringService {
    private static PayReStringService balanceModel;
    private PayApiString mBalanceService;

    public static PayReStringService getInstance(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        if (balanceModel == null) {
            balanceModel = new PayReStringService(context, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
        }
        return balanceModel;
    }

    public static PayReStringService getInstance(Context context) {
        if (balanceModel == null) {
            balanceModel = new PayReStringService(context);
        }
        return balanceModel;
    }

    private PayReStringService(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        mBalanceService = HttpsStringServiceGenerator.createService(PayApiString.class, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
    }

    private PayReStringService(Context context) {
        mBalanceService = HttpsStringServiceGenerator.createService(PayApiString.class);
    }

    /**
     * IPP3Order/IPP3So_MasterLog
     *
     * @param accessToken a
     * @return a
     */
    public Observable<String> insertLog(InsertOrderLogReq accessToken) {
        return mBalanceService.insertLog(accessToken);
    }
}
