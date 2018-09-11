package com.yang.yunwang.home.homeservice;

import android.content.Context;

import com.google.gson.JsonArray;
import com.yang.yunwang.base.basereq.bean.staffinfo.StaffInfoReq;
import com.yang.yunwang.base.basereq.bean.stafflogin.StaffLoginResp;
import com.yang.yunwang.base.ret.HttpsServiceGenerator;
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

/**
 * Created by Administrator on 2018/6/11.
 */

public class HomeREService {
    private static HomeREService balanceModel;
    private APIHome mBalanceService;

    /**
     * Singleton
     */
    public static HomeREService getInstance(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        if (balanceModel == null) {
            balanceModel = new HomeREService(context, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
        }
        return balanceModel;
    }

    public static HomeREService getInstance(Context context) {
        if (balanceModel == null) {
            balanceModel = new HomeREService(context);
        }
        return balanceModel;
    }

    private HomeREService(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        mBalanceService = HttpsServiceGenerator.createService(APIHome.class, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
    }

    private HomeREService(Context context) {
        mBalanceService = HttpsServiceGenerator.createService(APIHome.class);
    }

    public Observable<JsonArray> getPassageWay(StaffInfoReq accessToken) {
        return mBalanceService.getPassageWay(accessToken);
    }

    public Observable<List<MerchAllcoateResp>> getMerchAllcoate(MerchAllcoateReq accessToken) {
        return mBalanceService.getMerchAllcoate(accessToken);
    }

    public Observable<List<StaffAllcoateResp>> getStaffAllcoate(StaffAllcoateReq accessToken) {
        return mBalanceService.getStaffAllcoate(accessToken);
    }


    public Observable<List<VersionCodeResp>> getVersionCode(VersionCodeReq accessToken) {
        return mBalanceService.getVersionCode(accessToken);
    }

    public Observable<MerchLoginResp> merchLogin(LoginPageReq accessToken) {
        return mBalanceService.merchentLogin(accessToken);
    }

    public Observable<StaffLoginResp> staffLogin(LoginPageReq accessToken) {
        return mBalanceService.staffLogin(accessToken);
    }

    public Observable<JidInsertResp> jidInsert(JidInsertReq accessToken) {
        return mBalanceService.insertJid(accessToken);
    }

    public Observable<RoleTypeResp> getRoleType(RoleTypeReq accessToken) {
        return mBalanceService.getRoleType(accessToken);
    }

    public Observable<MerchHomeFragResp> getSerMerchHomeFragStatics(MerchHomeFragReq mrchHomeFragReq) {
        return mBalanceService.getSerMerchStatics(mrchHomeFragReq);
    }

    public Observable<MerchHomeFragResp> getCusMerchHomeFragStatics(MerchHomeCusReq mrchHomeFragReq) {
        return mBalanceService.getCusMerchStatics(mrchHomeFragReq);
    }

    public Observable<RoleTypeResp> getTopInfo(RoleTypeReq accessToken) {
        return mBalanceService.getTopInfo(accessToken);
    }

    /**
     * IPP3Customers/IPP3CustomerUpdPwd/**
     *
     * @param accessToken a
     * @return a
     */
    public Observable<ResetPwdResp> resetCusPwd(ResetPwdReq accessToken) {
        return mBalanceService.resetCusPwd(accessToken);
    }

    /**
     * IPP3Customers/IPP3SystemUserUpdatePwd/**
     *
     * @param accessToken a
     * @return a
     */
    public Observable<ResetPwdResp> resetStaffPwd(ResetPwdReq accessToken) {
        return mBalanceService.resetStaffPwd(accessToken);
    }


    /**
     * IPP3Customers/SystemClassList/**
     *
     * @param accessToken a
     * @return a
     */
    public Observable<ArrayList<CusClassResp>> getCusClass(CommonClassProReq accessToken) {
        return mBalanceService.getCusClass(accessToken);
    }

    /**
     * IPP3Customers/IPP3GetAddress/**
     *
     * @param accessToken
     * @return
     */
    public Observable<ArrayList<ProviceResp>> getCusProvice(CommonClassProReq accessToken) {
        return mBalanceService.getCusProvice(accessToken);
    }


    /**
     * IPP3Customers/IPP3CustomerShopInsert/**
     *
     * @param accessToken
     * @return
     */
    public Observable<CusRegistResp> registCus(CusRegistReq accessToken) {
        return mBalanceService.registCus(accessToken);
    }


    /**
     * IPP3Customers/IPP3CustomerAndSystemDefaultRoles/**
     *
     * @param accessToken
     * @return
     */

    public Observable<RegistCommonResp> insertDefaultRole(ArrayList<InsertDefaultRoleReq> accessToken) {
        return mBalanceService.insertDefaultRole(accessToken);
    }

    /**
     * IPP3Customers/CustomerServiceRateADD/**
     *
     * @param accessToken
     * @return
     */
    public Observable<RegistCommonResp> insertCusRate(ArrayList<InsertRateReq> accessToken) {
        return mBalanceService.insertCusRate(accessToken);
    }


    /**
     * IPP3Customers/CustomerServicePassageWayInsert/**
     *
     * @param accessToken
     * @return
     */
    public Observable<RegistCommonResp> insertCusPassageWay(InsertPassageReq accessToken) {
        return mBalanceService.insertCusPassageWay(accessToken);
    }

    /**
     * IPP3Customers/IPP3CustomerShopList
     *
     * @param accessToken a
     * @return a
     */
    public Observable<MerchInitResp> initMerchPersonal(UpdateInitReq accessToken) {
        return mBalanceService.initMerchPersonal(accessToken);
    }

    /**
     * IPP3Customers/IPP3SystemUserList
     *
     * @param accessToken a
     * @return a
     */
    public Observable<StaffInitResp> initStaffPersonal(UpdateInitReq accessToken) {
        return mBalanceService.initStaffPersonal(accessToken);
    }

    /**
     * IPP3Customers/CustomerServicePassageWayList")
     *
     * @param accessToken a
     * @return a
     */
    public Observable<ArrayList<PassageWayResp>> initMerchPassage(CommonRateReq accessToken) {
        return mBalanceService.initMerchPassage(accessToken);
    }

    /**
     * IPP3Customers/CustomerServiceRateList")
     *
     * @param accessToken a
     * @return a
     */
    public Observable<ArrayList<RateResp>> initMerchRate(CommonRateReq accessToken) {
        return mBalanceService.initMerchRate(accessToken);
    }

    /**
     * IPP3Customers/IPP3CustomerUpd")
     *
     * @param accessToken a
     * @return a
     */
    public Observable<CommonUpdateResp> updateMerchInfo(MerchUpdateReq accessToken) {
        return mBalanceService.updateMerchInfo(accessToken);
    }

    /**
     * IPP3Customers/IPP3SystemUserUpdate
     *
     * @param accessToken a
     * @return a
     */
    public Observable<CommonUpdateResp> updateStaffInfo(StaffUpdateReq accessToken) {
        return mBalanceService.updateStaffInfo(accessToken);
    }

}
