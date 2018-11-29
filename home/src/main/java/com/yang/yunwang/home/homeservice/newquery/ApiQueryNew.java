package com.yang.yunwang.home.homeservice.newquery;


import com.yang.yunwang.home.mainhome.bean.ordersearch.OrderSearchReq;
import com.yang.yunwang.home.mainhome.bean.ordersearch.OrderSearchRespNew;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 */

public interface ApiQueryNew {

    @POST("IPP3Order/IPP3OrderListShopUserSP")
    Observable<OrderSearchRespNew> getStaffOrders(@Body OrderSearchReq loginRequest);

    @POST("IPP3Order/IPP3OrderListCustomerSP")
    Observable<OrderSearchRespNew> getCustomerOrders(@Body OrderSearchReq loginRequest);

    @POST("IPP3Order/IPP3OrderListShopSP")
    Observable<OrderSearchRespNew> getMerchOrders(@Body OrderSearchReq loginRequest);

    @POST("IPP3Order/IPP3OrderListCustomerUserSP")
    Observable<OrderSearchRespNew> getCustomerUserOrders(@Body OrderSearchReq loginRequest);

}
