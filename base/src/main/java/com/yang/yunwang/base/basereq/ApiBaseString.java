package com.yang.yunwang.base.basereq;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/7/25.
 */

public interface ApiBaseString {
    @POST("IPP3Order/IPP3So_MasterLog")
    Observable<String> reinsertLog(@Body String accessToken);
}
