package com.yang.yunwang.base.basereq;


import com.yang.yunwang.base.basereq.bean.merchinfo.MerchInfoReq;
import com.yang.yunwang.base.basereq.bean.merchinfo.MerchInfoResp;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigReq;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigResp;
import com.yang.yunwang.base.basereq.bean.staffinfo.StaffInfoReq;
import com.yang.yunwang.base.basereq.bean.staffinfo.StaffInfoResp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/6/11.
 */

public interface APIBase {
    @POST("IPP3Customers/IPP3SystemUserListCSsysno")
    Observable<StaffInfoResp> checkStaffPWD(@Body StaffInfoReq loginRequest);

    @POST("IPP3Customers/IPP3CustomerShopList")
    Observable<MerchInfoResp> checkMerchPWD(@Body MerchInfoReq prepairReq);

    @POST("IPP3Customers/CustomerServicePassageWayList")
    Observable<List<PayConfigResp>> getPayConfig(@Body PayConfigReq accessToken);


}
