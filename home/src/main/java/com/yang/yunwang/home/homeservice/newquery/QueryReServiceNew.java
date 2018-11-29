package com.yang.yunwang.home.homeservice.newquery;

import android.content.Context;

import com.yang.yunwang.base.ret.HttpsServiceGenerator;
import com.yang.yunwang.home.mainhome.bean.ordersearch.OrderSearchReq;
import com.yang.yunwang.home.mainhome.bean.ordersearch.OrderSearchRespNew;

import io.reactivex.Observable;


/**
 * Created by Administrator on 2018/7/2.
 */

public class QueryReServiceNew {
    private static QueryReServiceNew balanceModel;
    private ApiQueryNew mBalanceService;

    private QueryReServiceNew(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        mBalanceService = HttpsServiceGenerator.createService(ApiQueryNew.class, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
    }

    private QueryReServiceNew(Context context) {
        mBalanceService = HttpsServiceGenerator.createService(ApiQueryNew.class);
    }

    public static QueryReServiceNew getInstance(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        if (balanceModel == null) {
            balanceModel = new QueryReServiceNew(context, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
        }
        return balanceModel;
    }

    public static QueryReServiceNew getInstance(Context context) {
        if (balanceModel == null) {
            balanceModel = new QueryReServiceNew(context);
        }
        return balanceModel;
    }

    public Observable<OrderSearchRespNew> getStaffOrders(OrderSearchReq accessToken) {
        return mBalanceService.getStaffOrders(accessToken);
    }

    public Observable<OrderSearchRespNew> getMerchOrders(OrderSearchReq accessToken) {
        return mBalanceService.getMerchOrders(accessToken);
    }

    public Observable<OrderSearchRespNew> getCustomerOrders(OrderSearchReq accessToken) {
        return mBalanceService.getCustomerOrders(accessToken);
    }

    public Observable<OrderSearchRespNew> getCustomerUserOrders(OrderSearchReq accessToken) {
        return mBalanceService.getCustomerUserOrders(accessToken);
    }

}
