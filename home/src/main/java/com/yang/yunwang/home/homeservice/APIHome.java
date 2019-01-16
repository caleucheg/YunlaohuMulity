package com.yang.yunwang.home.homeservice;

import com.google.gson.JsonArray;
import com.yang.yunwang.base.basereq.bean.merchlogin.MerchLoginResp;
import com.yang.yunwang.base.basereq.bean.staffinfo.StaffInfoReq;
import com.yang.yunwang.base.basereq.bean.stafflogin.StaffLoginResp;
import com.yang.yunwang.home.homeservice.bean.JidInsertReq;
import com.yang.yunwang.home.homeservice.bean.JidInsertResp;
import com.yang.yunwang.home.homeservice.bean.LoginPageReq;
import com.yang.yunwang.home.homeservice.bean.MerchAllcoateReq;
import com.yang.yunwang.home.homeservice.bean.MerchAllcoateResp;
import com.yang.yunwang.home.homeservice.bean.MerchHomeCusReq;
import com.yang.yunwang.home.homeservice.bean.MerchHomeFragReq;
import com.yang.yunwang.home.homeservice.bean.MerchHomeFragResp;
import com.yang.yunwang.home.homeservice.bean.RoleTypeReq;
import com.yang.yunwang.home.homeservice.bean.RoleTypeResp;
import com.yang.yunwang.home.homeservice.bean.StaffAllcoateReq;
import com.yang.yunwang.home.homeservice.bean.StaffAllcoateResp;
import com.yang.yunwang.home.homeservice.bean.VersionCodeReq;
import com.yang.yunwang.home.homeservice.bean.VersionCodeResp;
import com.yang.yunwang.home.mainhome.bean.CommonClassProReq;
import com.yang.yunwang.home.mainhome.bean.CusClassResp;
import com.yang.yunwang.home.mainhome.bean.ProviceResp;
import com.yang.yunwang.home.mainhome.bean.ResetPwdReq;
import com.yang.yunwang.home.mainhome.bean.ResetPwdResp;
import com.yang.yunwang.home.mainhome.bean.cusallcoate.request.CusUserAllcoateReq;
import com.yang.yunwang.home.mainhome.bean.cusallcoate.respone.CusUserAllcoateResp;
import com.yang.yunwang.home.mainhome.bean.homepage.customer.ShopTotalInfoReq;
import com.yang.yunwang.home.mainhome.bean.homepage.customer.ShopTotalInfoResp;
import com.yang.yunwang.home.mainhome.bean.homepage.serverstaff.active.ServerStaffActiveCusReq;
import com.yang.yunwang.home.mainhome.bean.homepage.serverstaff.active.ServerStaffActiveCusResp;
import com.yang.yunwang.home.mainhome.bean.homepage.serverstaff.totalinfo.ServerStaffTotalInfoReq;
import com.yang.yunwang.home.mainhome.bean.homepage.serverstaff.totalinfo.ServerStaffTotalInfoResp;
import com.yang.yunwang.home.mainhome.bean.homepage.services.activie.ServiceActiveCusReq;
import com.yang.yunwang.home.mainhome.bean.homepage.services.activie.ServiceActiveCusResp;
import com.yang.yunwang.home.mainhome.bean.homepage.services.totalinfo.ServiceTotalInfoReq;
import com.yang.yunwang.home.mainhome.bean.homepage.services.totalinfo.ServiceTotalResp;
import com.yang.yunwang.home.mainhome.bean.homepage.staff.StaffTotalInfoReq;
import com.yang.yunwang.home.mainhome.bean.homepage.staff.StaffTotalInfoResp;
import com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.request.CommonNewOrderReq;
import com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.respone.CommonNewOrderResp;
import com.yang.yunwang.home.mainhome.bean.regist.CusRegistReq;
import com.yang.yunwang.home.mainhome.bean.regist.CusRegistResp;
import com.yang.yunwang.home.mainhome.bean.regist.InsertDefaultRoleReq;
import com.yang.yunwang.home.mainhome.bean.regist.InsertPassageReq;
import com.yang.yunwang.home.mainhome.bean.regist.InsertRateReq;
import com.yang.yunwang.home.mainhome.bean.regist.RegistCommonResp;
import com.yang.yunwang.home.mainhome.bean.report.CommonReportReq;
import com.yang.yunwang.home.mainhome.bean.report.resp.CommonReportResp;
import com.yang.yunwang.home.mainhome.bean.update.CommonUpdateResp;
import com.yang.yunwang.home.mainhome.bean.update.MerchUpdateReq;
import com.yang.yunwang.home.mainhome.bean.update.StaffUpdateReq;
import com.yang.yunwang.home.mainhome.bean.update.init.UpdateInitReq;
import com.yang.yunwang.home.mainhome.bean.update.init.merch.MerchInitResp;
import com.yang.yunwang.home.mainhome.bean.update.init.staff.StaffInitResp;
import com.yang.yunwang.home.mainhome.bean.update.rate.CommonRateReq;
import com.yang.yunwang.home.mainhome.bean.update.rate.PassageWayResp;
import com.yang.yunwang.home.mainhome.bean.update.rate.RateResp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/6/11
 */

public interface APIHome {

    @POST("IPP3Customers/SystemPassageWayList")
    Observable<JsonArray> getPassageWay(@Body StaffInfoReq loginRequest);

    @POST("IPP3Customers/IPP3CustomerRoleList")
    Observable<List<MerchAllcoateResp>> getMerchAllcoate(@Body MerchAllcoateReq allcoateReq);

    @POST("IPP3Customers/IPP3UserRoleList")
    Observable<List<StaffAllcoateResp>> getStaffAllcoate(@Body StaffAllcoateReq allcoateReq);


    @POST("IPP3Customers/SystemVersionList")
    Observable<List<VersionCodeResp>> getVersionCode(@Body VersionCodeReq allcoateReq);


    @POST("IPP3Customers/IPP3CustomerLogin")
    Observable<MerchLoginResp> merchentLogin(@Body LoginPageReq allcoateReq);


    @POST("IPP3Customers/IPP3Login")
    Observable<StaffLoginResp> staffLogin(@Body LoginPageReq allcoateReq);

    @POST("IPP3Customers/WSJPushIDInsert")
    Observable<JidInsertResp> insertJid(@Body JidInsertReq allcoateReq);

    @POST("IPP3Customers/IPP3GetCustomerServiceSysNoList")
    Observable<RoleTypeResp> getRoleType(@Body RoleTypeReq allcoateReq);

    @POST("IPP3Order/IPP3Order_Group_Customer")
    Observable<MerchHomeFragResp> getSerMerchStatics(@Body MerchHomeFragReq merchHomeFragReq);

    @POST("IPP3Order/IPP3Order_Group_Shop")
    Observable<MerchHomeFragResp> getCusMerchStatics(@Body MerchHomeCusReq merchHomeFragReq);


    @POST("IPP3Customers/IPP3GetCustomerServiceSysNoList")
    Observable<RoleTypeResp> getTopInfo(@Body RoleTypeReq merchHomeFragReq);


    @POST("IPP3Customers/IPP3CustomerUpdPwd")
    Observable<ResetPwdResp> resetCusPwd(@Body ResetPwdReq merchHomeFragReq);

    @POST("IPP3Customers/IPP3SystemUserUpdatePwd")
    Observable<ResetPwdResp> resetStaffPwd(@Body ResetPwdReq merchHomeFragReq);


    @POST("IPP3Customers/SystemClassList")
    Observable<ArrayList<CusClassResp>> getCusClass(@Body CommonClassProReq merchHomeFragReq);

    @POST("IPP3Customers/IPP3GetAddress")
    Observable<ArrayList<ProviceResp>> getCusProvice(@Body CommonClassProReq merchHomeFragReq);


    @POST("IPP3Customers/IPP3CustomerShopInsert")
    Observable<CusRegistResp> registCus(@Body CusRegistReq merchHomeFragReq);


    @POST("IPP3Customers/IPP3CustomerAndSystemDefaultRoles")
    Observable<RegistCommonResp> insertDefaultRole(@Body ArrayList<InsertDefaultRoleReq> merchHomeFragReq);

    @POST("IPP3Customers/CustomerServiceRateADD")
    Observable<RegistCommonResp> insertCusRate(@Body ArrayList<InsertRateReq> merchHomeFragReq);

    @POST("IPP3Customers/CustomerServicePassageWayInsert")
    Observable<RegistCommonResp> insertCusPassageWay(@Body InsertPassageReq merchHomeFragReq);


    @POST("IPP3Customers/IPP3CustomerShopList")
    Observable<MerchInitResp> initMerchPersonal(@Body UpdateInitReq merchHomeFragReq);

    @POST("IPP3Customers/IPP3SystemUserList")
    Observable<StaffInitResp> initStaffPersonal(@Body UpdateInitReq merchHomeFragReq);

    @POST("IPP3Customers/CustomerServicePassageWayList")
    Observable<ArrayList<PassageWayResp>> initMerchPassage(@Body CommonRateReq merchHomeFragReq);

    @POST("IPP3Customers/CustomerServiceRateList")
    Observable<ArrayList<RateResp>> initMerchRate(@Body CommonRateReq merchHomeFragReq);

    @POST("IPP3Customers/IPP3CustomerUpd")
    Observable<CommonUpdateResp> updateMerchInfo(@Body MerchUpdateReq merchHomeFragReq);

    @POST("IPP3Customers/IPP3SystemUserUpdate")
    Observable<CommonUpdateResp> updateStaffInfo(@Body StaffUpdateReq merchHomeFragReq);

    @POST("IPP3Order/IPP3Order_Group_Customer")
    Observable<ServiceTotalResp> serviceTotalInfo(@Body ServiceTotalInfoReq serviceTotalResp);

    @POST("IPP3Order/IPP3Order_Group_Shop")
    Observable<ShopTotalInfoResp> shopTotalInfo(@Body ShopTotalInfoReq serviceTotalResp);

    @POST("IPP3OrderPassage/IPP3Order_Master_Fund_Group_Customer")
    Observable<ServiceActiveCusResp> serviceActive(@Body ServiceActiveCusReq serviceActiveCusReq);

    @POST("IPP3Order/IPP3Order_Group_CustomerUser")
    Observable<ServerStaffTotalInfoResp> serverStaffTotalInfo(@Body ServerStaffTotalInfoReq serviceActiveCusReq);

    @POST("IPP3Order/IPP3Order_Group_ShopUser")
    Observable<StaffTotalInfoResp> staffTotalInfo(@Body StaffTotalInfoReq serviceActiveCusReq);

    @POST("IPP3OrderPassage/IPP3Order_Master_Fund_Group_CustomerUser")
    Observable<ServerStaffActiveCusResp> serverStaffActiveCus(@Body ServerStaffActiveCusReq serviceActiveCusReq);

    @POST("IPP3OrderPassage/SP_Master_Passage_Customer_List")
    Observable<CommonNewOrderResp> orderNewSearch(@Body CommonNewOrderReq serviceActiveCusReq);

    @POST("IPP3OrderPassage/SP_Master_Passage_Group_List")
    Observable<CommonReportResp> commonReport(@Body CommonReportReq serviceActiveCusReq);

    @POST("IPP3OrderPassage/IPP3Order_Master_Passage_Fund_ShopUser")
    Observable<CusUserAllcoateResp> cusUserAllcoate(@Body CusUserAllcoateReq serviceActiveCusReq);

}
