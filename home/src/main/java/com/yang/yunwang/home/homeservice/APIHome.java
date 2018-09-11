package com.yang.yunwang.home.homeservice;

import com.google.gson.JsonArray;
import com.yang.yunwang.base.basereq.bean.staffinfo.StaffInfoReq;
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
import com.yang.yunwang.base.basereq.bean.merchlogin.MerchLoginResp;
import com.yang.yunwang.base.basereq.bean.stafflogin.StaffLoginResp;
import com.yang.yunwang.home.mainhome.bean.CommonClassProReq;
import com.yang.yunwang.home.mainhome.bean.CusClassResp;
import com.yang.yunwang.home.mainhome.bean.ProviceResp;
import com.yang.yunwang.home.mainhome.bean.ResetPwdReq;
import com.yang.yunwang.home.mainhome.bean.ResetPwdResp;
import com.yang.yunwang.home.mainhome.bean.regist.CusRegistReq;
import com.yang.yunwang.home.mainhome.bean.regist.CusRegistResp;
import com.yang.yunwang.home.mainhome.bean.regist.InsertDefaultRoleReq;
import com.yang.yunwang.home.mainhome.bean.regist.InsertPassageReq;
import com.yang.yunwang.home.mainhome.bean.regist.InsertRateReq;
import com.yang.yunwang.home.mainhome.bean.regist.RegistCommonResp;
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
}
