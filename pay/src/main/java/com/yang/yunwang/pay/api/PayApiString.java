package com.yang.yunwang.pay.api;

import com.yang.yunwang.pay.api.scan.insertorder.InsertOrderLogReq;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/7/19.
 */

public interface PayApiString {
    @POST("IPP3Order/IPP3So_MasterLog")
    Observable<String> insertLog(@Body InsertOrderLogReq loginRequest);
}
