package com.yang.yunwang.query.api;

import com.yang.yunwang.query.api.bean.allcoate.AllcoateInitReq;
import com.yang.yunwang.query.api.bean.merchinfo.MerchUpdateRateReq;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/7/25.
 */

public interface ApiQueryString {
    @POST("IPP3Customers/IPP3CustomerUserUpdate")
    Observable<String> allcoateCus(@Body AllcoateInitReq loginRequest);
    @POST("IPP3Customers/IPP3CustomerUserRateUpdate")
    Observable<String> updateMerchRate(@Body MerchUpdateRateReq loginRequest);
}
