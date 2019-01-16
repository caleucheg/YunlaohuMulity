package com.yang.yunwang.base.moduleinterface.module.home;

import android.content.Context;

import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.provider.IAppProvider;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.moduleinterface.router.ModuleManager;
import com.yang.yunwang.base.moduleinterface.router.MyRouter;

/**
 * on 2017/4/16.
 * <p>
 * description：
 * update by:
 * update day:
 */
public class HomeIntent {
    private static MyRouter aHome;

    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IHomeProvider.HOME_MAIN_SERVICE);
    }

    public static void login() {
        MyRouter.newInstance(IHomeProvider.HOME_ACT_LOGIN)
                .navigation();
    }

    public static void launchHome() {
        //HomeActivity
        MyBundle bundle = new MyBundle();
        bundle.put(IHomeProvider.HOME_TABTYPE, 1);
        KLog.i("home");
        if (aHome == null) {
            aHome = MyRouter.newInstance(IHomeProvider.HOME_ACT_HOME);
        }

        aHome.withBundle(bundle)
                .navigation();
        KLog.i("222222");
    }

    public static void launchHomeTest(Context context) {
        //HomeActivity
        MyBundle bundle = new MyBundle();
        bundle.put(IHomeProvider.HOME_TABTYPE, 1);
        KLog.i("home");
        if (aHome == null) {
            aHome = MyRouter.newInstance(IHomeProvider.HOME_ACT_HOME);
        }

        aHome.withBundle(bundle)
                .navigation(context);
        KLog.i("222222");
    }

    public static void launchHomePage() {
        MyRouter.newInstance(IHomeProvider.HOME_ACT_HOME)
                .navigation();
    }

    public static void searchStaffs(MyBundle bundle) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_STAFF_LIST)
                .withBundle(bundle)
                .navigation();
    }

    public static void scanPic(MyBundle bundle) {
        MyRouter.newInstance(IAppProvider.APP_ACT_CAPTURE)
                .withBundle(bundle)
                .navigation();
    }

    public static void orderLists(MyBundle bundle) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_ORDER_LIST)
                .withBundle(bundle)
                .navigation();
    }

    public static void settlementList(MyBundle bundle) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_ORDER_SETTLE)
                .withBundle(bundle)
                .navigation();
    }

    public static void updateInfo(MyBundle bundle) {
        MyRouter.newInstance(IHomeProvider.HOME_ACT_UPDATE_INFO)
                .withBundle(bundle)
                .navigation();
    }

    public static void passWord() {
        MyRouter.newInstance(IHomeProvider.HOME_ACT_RESET_PWD)
                .navigation();
    }

    public static void updateInfo() {
        MyRouter.newInstance(IHomeProvider.HOME_ACT_UPDATE_INFO)
                .navigation();
    }

    public static void merchantList(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_MERCH_LIST)
                .withBundle(intent)
                .navigation();
    }

    public static void unRefundList(MyBundle intent) {
        MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_UNREFUND_LIST)
                .withBundle(intent)
                .navigation();
    }

    public static void scanResult(MyBundle intent_result) {
        MyRouter.newInstance(IHomeProvider.HOME_ACT_SCAN_RESULT)
                .withBundle(intent_result)
                .navigation();
    }

    public static void orderFilter(MyBundle intent_result) {
        MyRouter.newInstance(IHomeProvider.HOME_ACT_ORDER_FILTER)
                .withBundle(intent_result)
                .navigation();
    }

    public static void reportNameFilter() {
        MyRouter.newInstance(IHomeProvider.HOME_ACT_REPORT_NAME_FILTER)
                .navigation();
    }

    public static void reportTimeFilter() {
        MyRouter.newInstance(IHomeProvider.HOME_ACT_REPORT_TIME_FILTER)
                .navigation();
    }

    public static void homeNewOrderList(MyBundle intent_result) {
        MyRouter.newInstance(IHomeProvider.HOME_ACT_NEW_ORDER_LIST)
                .withBundle(intent_result)
                .navigation();
    }

    public static void cusUserAlList(MyBundle intent_result) {
        MyRouter.newInstance(IHomeProvider.HOME_ACT_CUS_USER_AL_LIST)
                .withBundle(intent_result)
                .navigation();
    }

    public static void cusUserAlFilter(MyBundle intent_result) {
        MyRouter.newInstance(IHomeProvider.HOME_ACT_CUS_USER_AL_FILTER)
                .withBundle(intent_result)
                .navigation();
    }
}
