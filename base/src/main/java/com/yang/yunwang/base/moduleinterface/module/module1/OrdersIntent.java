package com.yang.yunwang.base.moduleinterface.module.module1;

import android.app.Activity;

import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.moduleinterface.router.ModuleManager;
import com.yang.yunwang.base.moduleinterface.router.MyRouter;

/**
 *  on 2017/4/13.
 *
 * description：
 * update by:
 * update day:
 */
public class OrdersIntent {
    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IOrdersProvider.ORDRES_MAIN_SERVICE);
    }
    public static void orderSearch(MyBundle intent_order_search){
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_ORDER_SEARCH)
                .withBundle(intent_order_search)
                .navigation();
    }
    public static void orderSearch(){
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_ORDER_SEARCH)
                .navigation();
    }
    public static void orderList(MyBundle bundle){
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_ORDER_LIST)
                .withBundle(bundle)
                .navigation();
    }

    public static void orderSettle(MyBundle bundle){
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_ORDER_SETTLE)
                .withBundle(bundle)
                .navigation();
    }

    public static void orderListnfo(MyBundle bundle){
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_ORDER_LIST_INFO)
                .withBundle(bundle)
                .navigation();
    }

    public static void wxPlantorderSearch(MyBundle intent_wx) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_WX_PLANT)
                .withBundle(intent_wx)
                .navigation();
    }
    public static void wxPlantorderSearch( ) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_WX_PLANT)
                .navigation();
    }

    public static void zfbPlantorderSearch(MyBundle intent_zfb) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_ZFB_PLANT_SEARCH)
                .withBundle(intent_zfb)
                .navigation();
    }

    public static void settleInfo(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_ORDER_SETTLE_INFO)
                .withBundle(intent)
                .navigation();
    }

    public static void unRefundList(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_UNREFUND_LIST)
                .withBundle(intent)
                .navigation();
    }

    public static void unRefundInfo(MyBundle intent, Activity activity, int i) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_UNREFUND_INFO)
                .withBundle(intent)
                .navigation(activity,i);
    }

    public static void refundList(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_REFUND_LIST)
                .withBundle(intent)
                .navigation();
    }

    public static void refundInfo(MyBundle intent, Activity activity, int i) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_REFUND_INFO)
                .withBundle(intent)
                .navigation(activity,i);
    }

    public static void refundInfo(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_REFUND_INFO)
                .withBundle(intent)
                .navigation();
    }

    public static void getOrderDetialsList(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_ORDER_DETIALS_LIST)
                .withBundle(intent)
                .navigation();
    }

    public static void getCommonList(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_COMMON_LIST)
                .withBundle(intent)
                .navigation();
    }

    public static void getDayCommonList(MyBundle intenth) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_DAY_COMMON_LIST)
                .withBundle(intenth)
                .navigation();
    }

    public static void getCommonInfo(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_COMMON_INFO)
                .withBundle(intent)
                .navigation();
    }

    public static void getDayCommonInfo(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_DAY_COMMON_INFO)
                .withBundle(intent)
                .navigation();
    }

    public static void getCommonRefundList(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_COMMON_REFUND_LIST)
                .withBundle(intent)
                .navigation();
    }

    public static void getCommonRefundInfo(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_COMMON_REFUND_INFO)
                .withBundle(intent)
                .navigation();
    }

    public static void getStaffList(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_STAFF_LIST)
                .withBundle(intent)
                .navigation();
    }

    public static void getStaffInfo(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_STAFF_INFO)
                .withBundle(intent)
                .navigation();
    }

    public static void getMerchSearch(MyBundle intent_shop) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_MERCH_SEARCH)
                .withBundle(intent_shop)
                .navigation();
    }

    public static void getCommonSearch(MyBundle intent_tenants_rate) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_COMMON_SEARCH)
                .withBundle(intent_tenants_rate)
                .navigation();
    }

    public static void getRegistPage(MyBundle intent_regist) {
        MyRouter.newInstance(IHomeProvider.HOME_ACT_REGIST)
                .withBundle(intent_regist)
                .navigation();
    }

    public static void getAllcoateList(MyBundle intent1) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_ALLCOATE_PAGE)
                .withBundle(intent1)
                .navigation();
    }

    public static void getMerchList(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_MERCH_LIST)
                .withBundle(intent)
                .navigation();
    }

    public static void getMerchInfo(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_MERCH_INFO)
                .withBundle(intent)
                .navigation();
    }

    public static void getSStafCollectList(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_SSTAFF_COLLECT_LIST)
                .withBundle(intent)
                .navigation();
    }

    public static void getSStafCollectInfo(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_SSTAFF_COLLECT_INFO)
                .withBundle(intent)
                .navigation();
    }

    public static void persnoalQr(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_PERSONAL_QR)
                .withBundle(intent)
                .navigation();
    }

    public static void refundSearch(MyBundle intent_order_search) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_REFUND_SEARCH)
                .withBundle(intent_order_search)
                .navigation();
    }

    public static void refundPrintSearch(MyBundle intent_order_search) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_ORDER_DETIALS_SEARCH)
                .withBundle(intent_order_search)
                .navigation();
    }

    public static void unRefundSearch(MyBundle intent_order_search) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_UNREFUND_SEARCH)
                .withBundle(intent_order_search)
                .navigation();
    }
}
