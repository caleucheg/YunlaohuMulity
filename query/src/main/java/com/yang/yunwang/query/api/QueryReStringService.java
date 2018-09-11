package com.yang.yunwang.query.api;

import android.content.Context;

import com.yang.yunwang.base.ret.HttpsStringServiceGenerator;
import com.yang.yunwang.query.api.bean.allcoate.AllcoateInitReq;
import com.yang.yunwang.query.api.bean.merchinfo.MerchUpdateRateReq;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/7/25.
 */

public class QueryReStringService {
    private static QueryReStringService balanceModel;
    private ApiQueryString mBalanceService;

    public static com.yang.yunwang.query.api.QueryReStringService getInstance(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        if (balanceModel == null) {
            balanceModel = new QueryReStringService(context, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
        }
        return balanceModel;
    }

    public static com.yang.yunwang.query.api.QueryReStringService getInstance(Context context) {
        if (balanceModel == null) {
            balanceModel = new QueryReStringService(context);
        }
        return balanceModel;
    }

    private QueryReStringService(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        mBalanceService = HttpsStringServiceGenerator.createService(ApiQueryString.class, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
    }

    private QueryReStringService(Context context) {
        mBalanceService = HttpsStringServiceGenerator.createService(ApiQueryString.class);
    }

    /**
     * IPP3Customers/IPP3CustomerUserRateUpdate
     *
     * @param accessToken req
     * @return resp
     */
    public Observable<String> updateMerchRate(MerchUpdateRateReq accessToken) {
        return mBalanceService.updateMerchRate(accessToken);
    }

    /**
     * IPP3Customers/IPP3CustomerUserUpdate")
     *
     * @param accessToken a
     * @return a
     */
    public Observable<String> allcoateCus(AllcoateInitReq accessToken) {
        return mBalanceService.allcoateCus(accessToken);
    }

}
