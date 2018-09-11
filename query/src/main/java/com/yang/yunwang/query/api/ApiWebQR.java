package com.yang.yunwang.query.api;

import com.yang.yunwang.query.api.bean.personalqr.PersonalQrReq;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/7/9.
 */

public interface ApiWebQR {

    @POST("GetQrcode/getQrcode")
    Observable<String> getQRCode(@Body PersonalQrReq loginRequest);

}
