package com.yang.yunwang.query.api;

import android.content.Context;

import com.yang.yunwang.base.ret.HttpsServiceGenerator;
import com.yang.yunwang.query.api.bean.allcoate.AllcoateInitLeftResp;
import com.yang.yunwang.query.api.bean.allcoate.AllcoateInitReq;
import com.yang.yunwang.query.api.bean.allcoate.AllcoateInitRightResp;
import com.yang.yunwang.query.api.bean.allcopage.DeleteRoleReq;
import com.yang.yunwang.query.api.bean.allcopage.DeleteRoleResp;
import com.yang.yunwang.query.api.bean.allcopage.InsertRoleReq;
import com.yang.yunwang.query.api.bean.allcopage.InsertRoleResp;
import com.yang.yunwang.query.api.bean.commondaycollect.CommonDayCollectReq;
import com.yang.yunwang.query.api.bean.commondaycollect.CommonDayCollectResp;
import com.yang.yunwang.query.api.bean.commonrefund.CommonRefundReq;
import com.yang.yunwang.query.api.bean.commonrefund.CommonRefundResp;
import com.yang.yunwang.query.api.bean.commonsearch.CommonOrdersReq;
import com.yang.yunwang.query.api.bean.commonsearch.CommonOrdersResp;
import com.yang.yunwang.query.api.bean.merchinfo.MerchInfoInitReq;
import com.yang.yunwang.query.api.bean.merchinfo.merchrole.MerchRoleLiftResp;
import com.yang.yunwang.query.api.bean.merchinfo.rates.MerchRateResp;
import com.yang.yunwang.query.api.bean.merchinfo.upuser.AllcoateUserLiftReq;
import com.yang.yunwang.query.api.bean.merchinfo.upuser.left.AllcoateUserLifResp;
import com.yang.yunwang.query.api.bean.merchinfo.upuser.right.AllcoateUserRightResp;
import com.yang.yunwang.query.api.bean.merchinfo.upuserinfo.MerchUpUserInfoResp;
import com.yang.yunwang.query.api.bean.merchsearch.MerchListReq;
import com.yang.yunwang.query.api.bean.merchsearch.MerchListResp;
import com.yang.yunwang.query.api.bean.orderprint.OrderDetialReq;
import com.yang.yunwang.query.api.bean.orderprint.OrdersDetialResp;
import com.yang.yunwang.query.api.bean.ordersearch.OrderSearchReq;
import com.yang.yunwang.query.api.bean.ordersearch.OrderSearchResp;
import com.yang.yunwang.query.api.bean.ordersettel.OrderSettleReq;
import com.yang.yunwang.query.api.bean.ordersettel.OrderSettleResp;
import com.yang.yunwang.query.api.bean.refundsearch.refundlist.RefundListReq;
import com.yang.yunwang.query.api.bean.refundsearch.refundlist.RefundListResp;
import com.yang.yunwang.query.api.bean.refundsearch.refundrole.RefundRoleReq;
import com.yang.yunwang.query.api.bean.refundsearch.refundrole.RefundRoleResp;
import com.yang.yunwang.query.api.bean.refundsearchs.RefundListSReq;
import com.yang.yunwang.query.api.bean.refundsearchs.RefundListSResp;
import com.yang.yunwang.query.api.bean.staffsearch.StaffListReq;
import com.yang.yunwang.query.api.bean.staffsearch.StaffListResp;
import com.yang.yunwang.query.api.bean.susersettle.list.SStaffCollectReq;
import com.yang.yunwang.query.api.bean.susersettle.list.SStaffCollectResp;
import com.yang.yunwang.query.api.bean.unrefund.bank.BankUnrefundReq;
import com.yang.yunwang.query.api.bean.unrefund.bank.BankUnrefundResp;
import com.yang.yunwang.query.api.bean.unrefund.netbank.NetBankUnrefundReq;
import com.yang.yunwang.query.api.bean.unrefund.netbank.NetBankUnrefundResp;
import com.yang.yunwang.query.api.bean.unrefund.yihui.wx.WxYihuiUnrefundReq;
import com.yang.yunwang.query.api.bean.unrefund.yihui.wx.WxYihuiUnrefundResp;
import com.yang.yunwang.query.api.bean.unrefund.yihui.zfb.ZfbYihuiUnrefundReq;
import com.yang.yunwang.query.api.bean.unrefund.yihui.zfb.ZfbYihuiUnrefundResp;
import com.yang.yunwang.query.api.bean.wxplant.bank.WxBankPlantReq;
import com.yang.yunwang.query.api.bean.wxplant.bank.WxBankPlantResp;
import com.yang.yunwang.query.api.bean.wxplant.netbank.WXNetBankPlantReq;
import com.yang.yunwang.query.api.bean.wxplant.netbank.WXNetBankPlantResp;
import com.yang.yunwang.query.api.bean.wxplant.netbank.WxNetTranIDReq;
import com.yang.yunwang.query.api.bean.wxplant.netbank.WxNetTranIdResp;
import com.yang.yunwang.query.api.bean.wxplant.yihui.WxPlantYihuiReq;
import com.yang.yunwang.query.api.bean.wxplant.yihui.WxPlantYihuiResp;
import com.yang.yunwang.query.api.bean.zfbplant.bank.ZFBBankPlantResp;
import com.yang.yunwang.query.api.bean.zfbplant.newbank.ZFBNetBankPlantResp;
import com.yang.yunwang.query.api.bean.zfbplant.yihui.ZFBYihuiPlantResp;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/7/2.
 */

public class QueryReService {
    private static QueryReService balanceModel;
    private ApiQuery mBalanceService;

    public static QueryReService getInstance(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        if (balanceModel == null) {
            balanceModel = new QueryReService(context, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
        }
        return balanceModel;
    }

    public static QueryReService getInstance(Context context) {
        if (balanceModel == null) {
            balanceModel = new QueryReService(context);
        }
        return balanceModel;
    }

    private QueryReService(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        mBalanceService = HttpsServiceGenerator.createService(ApiQuery.class, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
    }

    private QueryReService(Context context) {
        mBalanceService = HttpsServiceGenerator.createService(ApiQuery.class);
    }

    public Observable<OrderSearchResp> getStaffOrders(OrderSearchReq accessToken) {
        return mBalanceService.getStaffOrders(accessToken);
    }

    public Observable<OrderSearchResp> getMerchOrders(OrderSearchReq accessToken) {
        return mBalanceService.getMerchOrders(accessToken);
    }

    public Observable<OrderSearchResp> getCustomerOrders(OrderSearchReq accessToken) {
        return mBalanceService.getCustomerOrders(accessToken);
    }

    public Observable<OrderSearchResp> getCustomerUserOrders(OrderSearchReq accessToken) {
        return mBalanceService.getCustomerUserOrders(accessToken);
    }

    public Observable<WxPlantYihuiResp> getWXYihuiPlantOrders(WxPlantYihuiReq accessToken) {
        return mBalanceService.getWXYihuiPlantOrders(accessToken);
    }

    public Observable<WxBankPlantResp> getWXBankPlantOrders(WxBankPlantReq accessToken) {
        return mBalanceService.getWXBankPlantOrders(accessToken);
    }

    public Observable<WxNetTranIdResp> getWXTransID(WxNetTranIDReq accessToken) {
        return mBalanceService.getWXTransId(accessToken);
    }

    public Observable<WXNetBankPlantResp> getWXNetBankOrders(WXNetBankPlantReq accessToken) {
        return mBalanceService.getWXNetBankPlantOrders(accessToken);
    }

    public Observable<ZFBYihuiPlantResp> getZFBYihuiPlantOrders(WxPlantYihuiReq accessToken) {
        return mBalanceService.getZFBYihuiPlantOrders(accessToken);
    }

    public Observable<ZFBBankPlantResp> getZFBBankPlantOrders(WxBankPlantReq accessToken) {
        return mBalanceService.getZFBBankPlantOrders(accessToken);
    }

    public Observable<ZFBNetBankPlantResp> getZFBNetBankOrders(WXNetBankPlantReq accessToken) {
        return mBalanceService.getZFBNetBankPlantOrders(accessToken);
    }

    public Observable<OrderSettleResp> getSettlementList(OrderSettleReq accessToken) {
        return mBalanceService.getCollectList(accessToken);
    }

    public Observable<RefundListResp> getUnrefundList(RefundListReq accessToken) {
        return mBalanceService.getUnrefundList(accessToken);
    }

    public Observable<RefundRoleResp> getRefundRole(RefundRoleReq accessToken) {
        return mBalanceService.getUnrefundRole(accessToken);
    }

    public Observable<WxYihuiUnrefundResp> doWxYihuiRefund(WxYihuiUnrefundReq accessToken) {
        return mBalanceService.doYihuiWxRefund(accessToken);
    }

    public Observable<ZfbYihuiUnrefundResp> doZfbYihuiRefund(ZfbYihuiUnrefundReq accessToken) {
        return mBalanceService.doZfbhuiWxRefund(accessToken);
    }

    public Observable<NetBankUnrefundResp> doNetBankRefund(NetBankUnrefundReq accessToken) {
        return mBalanceService.doNetBankRefund(accessToken);
    }

    public Observable<BankUnrefundResp> doBankRefund(BankUnrefundReq accessToken) {
        return mBalanceService.doBankRefund(accessToken);
    }

    public Observable<RefundListSResp> getRefundListS(RefundListSReq accessToken) {
        return mBalanceService.getRefundListS(accessToken);
    }

    public Observable<OrdersDetialResp> getOrdersDetialStaff(OrderDetialReq accessToken) {
        return mBalanceService.getOrdersDetialStaff(accessToken);
    }

    public Observable<OrdersDetialResp> getOrdersDetialCus(OrderDetialReq accessToken) {
        return mBalanceService.getOrdersDetialCus(accessToken);
    }


    /**
     * IPP3Order/IPP3Order_Fund_ShopUserRateSP
     *
     * @param accessToken params
     * @return resp
     */
    public Observable<CommonOrdersResp> getStaffRateSettlement(CommonOrdersReq accessToken) {
        return mBalanceService.getStaffRateSettlement(accessToken);
    }


    /**
     * IPP3Order/IPP3OrderListShopUserRateSP
     *
     * @param accessToken params
     * @return resp
     */
    public Observable<CommonOrdersResp> getStaffRate(CommonOrdersReq accessToken) {
        return mBalanceService.getStaffRate(accessToken);
    }


    /**
     * IPP3Order/IPP3Order_Fund_ShopSPRate
     *
     * @param accessToken params
     * @return resp
     */
    public Observable<CommonOrdersResp> getCusRateSettlement(CommonOrdersReq accessToken) {
        return mBalanceService.getCusRateSettlement(accessToken);
    }

    /**
     * IPP3Order/IPP3OrderListShopSPRate
     *
     * @param accessToken params
     * @return resp
     */
    public Observable<CommonOrdersResp> getCusRate(CommonOrdersReq accessToken) {
        return mBalanceService.getCusRate(accessToken);
    }

    /**
     * IPP3Order/IPP3Order_Fund_CustomerUserRateSP
     *
     * @param accessToken params
     * @return resp
     */
    public Observable<CommonOrdersResp> getCusTopRateSettlement(CommonOrdersReq accessToken) {
        return mBalanceService.getCusTopRateSettlement(accessToken);
    }

    /**
     * "IPP3Order/IPP3OrderListCustomerUserRateSP
     *
     * @param accessToken params
     * @return resp
     */
    public Observable<CommonOrdersResp> getCusTopRate(CommonOrdersReq accessToken) {
        return mBalanceService.getCusTopRate(accessToken);
    }

    /**
     * IPP3Order/IPP3Order_Fund_ShopSPRate
     *
     * @param accessToken params
     * @return resp
     */
    public Observable<CommonOrdersResp> getServierUserRateSettlement(CommonOrdersReq accessToken) {
        return mBalanceService.getServierUserRateSettlement(accessToken);
    }

    /**
     * IPP3Order/IPP3OrderListCustomerUserRateSP
     *
     * @param accessToken params
     * @return resp
     */
    public Observable<CommonOrdersResp> getServierUserRate(CommonOrdersReq accessToken) {
        return mBalanceService.getServierUserRate(accessToken);
    }

    /**
     * IPP3Order/IPP3OrderByDayList
     *
     * @param accessToken a
     * @return a
     */
    public Observable<CommonDayCollectResp> getCommonDayCollectList(CommonDayCollectReq accessToken) {
        return mBalanceService.getCommonDayCollectList(accessToken);
    }


    /**
     * IPP3Order/IPP3RMA_RequestSP
     *
     * @param accessToken a
     * @return a
     */
    public Observable<CommonRefundResp> getCommonRefundList(CommonRefundReq accessToken) {
        return mBalanceService.getCommonRefundList(accessToken);
    }

    /**
     * "IPP3Customers/IPP3SystemUserListCSsysno
     *
     * @param accessToken req
     * @return resp
     */
    public Observable<StaffListResp> getStaffList(StaffListReq accessToken) {
        return mBalanceService.getStaffList(accessToken);
    }

    /**
     * IPP3Customers/IPP3UserRoleList
     *
     * @param accessToken req
     * @return resp
     */
    public Observable<ArrayList<AllcoateInitLeftResp>> getLiftRoleList(AllcoateInitReq accessToken) {
        return mBalanceService.getLiftRoleList(accessToken);
    }

    /**
     * IPP3Customers/IPP3SystemRoleList
     *
     * @param accessToken req
     * @return resp
     */
    public Observable<AllcoateInitRightResp> getRightRoleList(AllcoateInitReq accessToken) {
        return mBalanceService.getRightRoleList(accessToken);
    }

    /**
     * IPP3Customers/IPP3CustomerShopList
     *
     * @param accessToken req
     * @return resp
     */
    public Observable<MerchListResp> getMerchList(MerchListReq accessToken) {
        return mBalanceService.getMerchList(accessToken);
    }

//    /**
//     * IPP3Customers/IPP3CustomerUserRateUpdate
//     *
//     * @param accessToken req
//     * @return resp
//     */
//    public Observable<String> updateMerchRate(MerchUpdateRateReq accessToken) {
//        return mBalanceService.updateMerchRate(accessToken);
//    }


    /**
     * IPP3Customers/IPP3SystemUserByCSsysNo
     *
     * @param accessToken a
     * @return e
     */
    public Observable<AllcoateUserLifResp> getMerchUpUser(AllcoateUserLiftReq accessToken) {
        return mBalanceService.getMerchUpUser(accessToken);
    }

    /**
     * IPP3Customers/IPP3SystemUserListCSsysno
     *
     * @param accessToken a
     * @return e
     */
    public Observable<AllcoateUserRightResp> getMerchAllUser(AllcoateUserLiftReq accessToken) {
        return mBalanceService.getMerchAllUser(accessToken);
    }

    /**
     * IPP3Customers/IPP3CustomerRoleList
     *
     * @param accessToken a
     * @return e
     */
    public Observable<ArrayList<MerchRoleLiftResp>> getMerchRoleLift(AllcoateInitReq accessToken) {
        return mBalanceService.getMerchRoleLift(accessToken);
    }

    /**
     * IPP3Customers/IPP3SystemUserByCSsysNo
     *
     * @param accessToken a
     * @return a
     */
    public Observable<MerchUpUserInfoResp> getMerchUpUserInfo(MerchInfoInitReq accessToken) {
        return mBalanceService.getMerchUpUserInfo(accessToken);
    }

    /**
     * IPP3Customers/CustomerServiceRateList
     *
     * @param accessToken a
     * @return a
     */
    public Observable<ArrayList<MerchRateResp>> getMerchRateInfo(MerchInfoInitReq accessToken) {
        return mBalanceService.getMerchRateInfo(accessToken);
    }

    /**IPP3Customers/IPP3CustomerRoleDelete
     *
     * @param accessToken a
     * @return a
     */
    public Observable<DeleteRoleResp> deleteCusRole(DeleteRoleReq accessToken) {
        return mBalanceService.deleteCusRole(accessToken);
    }

    /**IPP3Customers/IPP3UserRoleDelete
     *
     * @param accessToken a
     * @return a
     */
    public Observable<DeleteRoleResp> deleteUserRole(DeleteRoleReq accessToken) {
        return mBalanceService.deleteUserRole(accessToken);
    }

    /**IPP3Customers/IPP3UserRoleInsert
     *
     * @param accessToken
     * @return a
     */
    public Observable<InsertRoleResp> insertUserRole(ArrayList<InsertRoleReq> accessToken) {
        return mBalanceService.insertUserRole(accessToken);
    }

    /**IPP3Customers/IPP3CustomerRoleInsert
     *
     * @param accessToken a
     * @return a
     */
    public Observable<InsertRoleResp> insertCusRole(ArrayList<InsertRoleReq> accessToken) {
        return mBalanceService.insertCusRole(accessToken);
    }

//    /**IPP3Customers/IPP3CustomerUserUpdate")
//     *
//     * @param accessToken a
//     * @return a
//     */
//    public Observable<String> allcoateCus(AllcoateInitReq accessToken) {
//        return mBalanceService.allcoateCus(accessToken);
//    }

    /**IPP3Order/IPP3Order_Group_CustomerUserList
     *
     * @param accessToken a
     * @return a
     */
    public Observable<SStaffCollectResp> getSStaffCollectList(SStaffCollectReq accessToken) {
        return mBalanceService.getSStaffCollectList(accessToken);
    }
}
