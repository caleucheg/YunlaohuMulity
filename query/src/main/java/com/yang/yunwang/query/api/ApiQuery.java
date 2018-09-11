package com.yang.yunwang.query.api;

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
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 */

public interface ApiQuery {

    @POST("IPP3Order/IPP3OrderListShopUserSP")
    Observable<OrderSearchResp> getStaffOrders(@Body OrderSearchReq loginRequest);

    @POST("IPP3Order/IPP3OrderListCustomerSP")
    Observable<OrderSearchResp> getCustomerOrders(@Body OrderSearchReq loginRequest);

    @POST("IPP3Order/IPP3OrderListShopSP")
    Observable<OrderSearchResp> getMerchOrders(@Body OrderSearchReq loginRequest);

    @POST("IPP3Order/IPP3OrderListCustomerUserSP")
    Observable<OrderSearchResp> getCustomerUserOrders(@Body OrderSearchReq loginRequest);

    @POST("Payment/Payments/QueryWxOrder")
    Observable<WxPlantYihuiResp> getWXYihuiPlantOrders(@Body WxPlantYihuiReq loginRequest);

    @POST("IPP3Swiftpass/OrderQueryApi")
    Observable<WxBankPlantResp> getWXBankPlantOrders(@Body WxBankPlantReq loginRequest);

    @POST("IPP3Order/So_MasterQuery")
    Observable<WxNetTranIdResp> getWXTransId(@Body WxNetTranIDReq loginRequest);

    @POST("IPP3WSOrder/WSPayQuery")
    Observable<WXNetBankPlantResp> getWXNetBankPlantOrders(@Body WXNetBankPlantReq loginRequest);

    @POST("IPP3AliPay/AliPayquery")
    Observable<ZFBYihuiPlantResp> getZFBYihuiPlantOrders(@Body WxPlantYihuiReq loginRequest);

    @POST("IPP3Swiftpass/OrderQueryApi")
    Observable<ZFBBankPlantResp> getZFBBankPlantOrders(@Body WxBankPlantReq loginRequest);

    @POST("IPP3WSOrder/WSPayQuery")
    Observable<ZFBNetBankPlantResp> getZFBNetBankPlantOrders(@Body WXNetBankPlantReq loginRequest);

    @POST("IPP3Order/IPP3OrderListcollect")
    Observable<OrderSettleResp> getCollectList(@Body OrderSettleReq loginRequest);

    @POST("IPP3Customers/IPP3RoleApplicationList")
    Observable<RefundRoleResp> getUnrefundRole(@Body RefundRoleReq loginRequest);

    @POST("IPP3Order/IPP3OrderFundListSP")
    Observable<RefundListResp> getUnrefundList(@Body RefundListReq loginRequest);

    @POST("POS/POSRefundInsert")
    Observable<WxYihuiUnrefundResp> doYihuiWxRefund(@Body WxYihuiUnrefundReq loginRequest);

    @POST("IPP3AliPay/AliPayRefundUnion")
    Observable<ZfbYihuiUnrefundResp> doZfbhuiWxRefund(@Body ZfbYihuiUnrefundReq loginRequest);

    @POST("IPP3WSOrder/WSPayRefundUnion")
    Observable<NetBankUnrefundResp> doNetBankRefund(@Body NetBankUnrefundReq loginRequest);

    @POST("IPP3Swiftpass/RefundApiUnion")
    Observable<BankUnrefundResp> doBankRefund(@Body BankUnrefundReq loginRequest);

    @POST("IPP3Order/IPP3RMA_RequestSP")
    Observable<RefundListSResp> getRefundListS(@Body RefundListSReq loginRequest);

    @POST("IPP3Order/IPP3OrderFundListSP")
    Observable<OrdersDetialResp> getOrdersDetialStaff(@Body OrderDetialReq loginRequest);

    @POST("IPP3Order/IPP3OrderFundList_Shop_SP")
    Observable<OrdersDetialResp> getOrdersDetialCus(@Body OrderDetialReq loginRequest);

    @POST("IPP3Order/IPP3Order_Fund_ShopUserRateSP")
    Observable<CommonOrdersResp> getStaffRateSettlement(@Body CommonOrdersReq loginRequest);

    @POST("IPP3Order/IPP3OrderListShopUserRateSP")
    Observable<CommonOrdersResp> getStaffRate(@Body CommonOrdersReq loginRequest);

    @POST("IPP3Order/IPP3Order_Fund_ShopSPRate")
    Observable<CommonOrdersResp> getCusRateSettlement(@Body CommonOrdersReq loginRequest);

    @POST("IPP3Order/IPP3OrderListShopSPRate")
    Observable<CommonOrdersResp> getCusRate(@Body CommonOrdersReq loginRequest);

    @POST("IPP3Order/IPP3Order_Fund_CustomerUserRateSP")
    Observable<CommonOrdersResp> getCusTopRateSettlement(@Body CommonOrdersReq loginRequest);

    @POST("IPP3Order/IPP3OrderListCustomerUserRateSP")
    Observable<CommonOrdersResp> getCusTopRate(@Body CommonOrdersReq loginRequest);

    @POST("IPP3Order/IPP3Order_Fund_ShopSPRate")
    Observable<CommonOrdersResp> getServierUserRateSettlement(@Body CommonOrdersReq loginRequest);

    @POST("IPP3Order/IPP3OrderListCustomerUserRateSP")
    Observable<CommonOrdersResp> getServierUserRate(@Body CommonOrdersReq loginRequest);

    @POST("IPP3Order/IPP3OrderByDayList")
    Observable<CommonDayCollectResp> getCommonDayCollectList(@Body CommonDayCollectReq loginRequest);

    @POST("IPP3Order/IPP3RMA_RequestSP")
    Observable<CommonRefundResp> getCommonRefundList(@Body CommonRefundReq loginRequest);

    @POST("IPP3Customers/IPP3SystemUserListCSsysno")
    Observable<StaffListResp> getStaffList(@Body StaffListReq loginRequest);

    @POST("IPP3Customers/IPP3UserRoleList")
    Observable<ArrayList<AllcoateInitLeftResp>> getLiftRoleList(@Body AllcoateInitReq loginRequest);

    @POST("IPP3Customers/IPP3SystemRoleList")
    Observable<AllcoateInitRightResp> getRightRoleList(@Body AllcoateInitReq loginRequest);

    @POST("IPP3Customers/IPP3CustomerShopList")
    Observable<MerchListResp> getMerchList(@Body MerchListReq loginRequest);




    @POST("IPP3Customers/IPP3SystemUserByCSsysNo")
    Observable<AllcoateUserLifResp> getMerchUpUser(@Body AllcoateUserLiftReq loginRequest);

    @POST("IPP3Customers/IPP3SystemUserListCSsysno")
    Observable<AllcoateUserRightResp> getMerchAllUser(@Body AllcoateUserLiftReq loginRequest);

    @POST("IPP3Customers/IPP3CustomerRoleList")
    Observable<ArrayList<MerchRoleLiftResp>> getMerchRoleLift(@Body AllcoateInitReq loginRequest);

    @POST("IPP3Customers/IPP3SystemUserByCSsysNo")
    Observable<MerchUpUserInfoResp> getMerchUpUserInfo(@Body MerchInfoInitReq loginRequest);

    @POST("IPP3Customers/CustomerServiceRateList")
    Observable<ArrayList<MerchRateResp>> getMerchRateInfo(@Body MerchInfoInitReq loginRequest);

    @POST("IPP3Customers/IPP3CustomerRoleDelete")
    Observable<DeleteRoleResp> deleteCusRole(@Body DeleteRoleReq loginRequest);

    @POST("IPP3Customers/IPP3UserRoleDelete")
    Observable<DeleteRoleResp> deleteUserRole(@Body DeleteRoleReq loginRequest);

    @POST("IPP3Customers/IPP3UserRoleInsert")
    Observable<InsertRoleResp> insertUserRole(@Body ArrayList<InsertRoleReq> loginRequest);

    @POST("IPP3Customers/IPP3CustomerRoleInsert")
    Observable<InsertRoleResp> insertCusRole(@Body ArrayList<InsertRoleReq> loginRequest);




    @POST("IPP3Order/IPP3Order_Group_CustomerUserList")
    Observable<SStaffCollectResp> getSStaffCollectList(@Body SStaffCollectReq loginRequest);
}
