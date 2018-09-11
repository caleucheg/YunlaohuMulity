package com.yang.yunwang.query.api;

import android.content.Context;

import com.yang.yunwang.base.ret.WebHttpServiceGenerator;
import com.yang.yunwang.query.api.bean.personalqr.PersonalQrReq;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/7/2.
 */

public class QrReService {
    private static QrReService balanceModel;
    private ApiWebQR mBalanceService;

    public static QrReService getInstance(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        if (balanceModel == null) {
            balanceModel = new QrReService(context, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
        }
        return balanceModel;
    }

    public static QrReService getInstance(Context context) {
        if (balanceModel == null) {
            balanceModel = new QrReService(context);
        }
        return balanceModel;
    }

    private QrReService(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        mBalanceService = WebHttpServiceGenerator.createService(ApiWebQR.class, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
    }

    private QrReService(Context context) {
        mBalanceService = WebHttpServiceGenerator.createService(ApiWebQR.class);
    }

    public Observable<String> getQRCode(PersonalQrReq accessToken) {
        return mBalanceService.getQRCode(accessToken);
    }
}
