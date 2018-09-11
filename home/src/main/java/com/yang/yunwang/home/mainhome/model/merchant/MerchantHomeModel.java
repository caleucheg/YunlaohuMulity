package com.yang.yunwang.home.mainhome.model.merchant;

import android.content.Context;
import android.os.Bundle;

import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.bean.MerchantHomeBean;
import com.yang.yunwang.home.mainhome.model.merchant.intf.MerchantHomeModelInterface;

import java.util.ArrayList;
import java.util.List;

public class MerchantHomeModel implements MerchantHomeModelInterface {

    private Context context;
    private MerchantHomeBean bean;

    public MerchantHomeModel(Context context) {
        this.context = context;
        bean = new MerchantHomeBean();
        KLog.i(ConstantUtils.STAFF_TYPE + "--s--c--" + ConstantUtils.CUSTOMERS_TYPE);
    }

    @Override
    public void initServiceRecMenu() {
        List<String> list = new ArrayList<String>();
        String[] string_array = context.getResources().getStringArray(R.array.service_rec_menu);
        for (int i = 0; i < string_array.length; i++) {
            list.add(string_array[i]);
        }
        int[] reses = new int[]{
                R.drawable.staff_normal, R.drawable.order_normal,
                R.drawable.merchant_normal,
                R.drawable.staff_destribute, R.drawable.staff_user_destribute,
                R.drawable.staff_collect, R.drawable.refund_search_icon, R.drawable.statics_normal_grey};
        int[] reses_select = new int[]{
                R.drawable.staff_select, R.drawable.order_select,
                R.drawable.merchant_select,
                R.drawable.staff_destribute, R.drawable.staff_user_destribute,
                R.drawable.staff_collect, R.drawable.refund_search_icon, R.drawable.statics_normal_grey};

//        Intent intent_staff = new Intent(context, StaffSearchActivity.class);
//        Intent intent_order = new Intent(context, OrdersSearchActivity.class);
//        Intent intent_statics = new Intent(context, StatisticsActivity.class);
//        Intent intent_merchant = new Intent(context, MerchantSearchActivity.class);
//        Intent intentStaffDistribute = new Intent(context, MerchantSearchActivity.class);
//        intent_statics.putExtra(ConstantUtils.ACTIVEBTN, false);
//        intentStaffDistribute.putExtra("allocate", true);
//        intentStaffDistribute.putExtra("from_home", true);
//        Intent intentStaffUserDistribute = new Intent(context, StaffSearchActivity.class);
//        intentStaffUserDistribute.putExtra("from_home", true);
//        intentStaffUserDistribute.putExtra(ConstantUtils.fromHomeDis, true);
//        Intent intent_Staff_Collect = new Intent(context, StaffCollectSearchActivity.class);
//        Intent intentRefund = new Intent(context, CommonSearchRefundActivity.class);
//        intentRefund.putExtra("CustomersTopSysNo", ConstantUtils.SYS_NO);
//        Intent[] intents = new Intent[]{
//                intent_staff,
//                intent_order,
//                intent_merchant,
//                intentStaffDistribute,
//                intentStaffUserDistribute,
//                intent_Staff_Collect,
//                intentRefund,
//                intent_statics};
        List<String> actions = new ArrayList<>();
        actions.add(IOrdersProvider.ORDERS_ACT_STAFF_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_ORDER_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_MERCH_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_MERCH_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_STAFF_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_SSTAFF_COLLECT_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_COMMON_REFUND_SEARCH);
        actions.add(IHomeProvider.HOME_ACT_STATICS);
        Bundle staffSearchBundle = new Bundle();
        staffSearchBundle.putBoolean(ConstantUtils.CLICKABLE, true);
        Bundle orderSearchBundle = new Bundle();
        orderSearchBundle.putBoolean(ConstantUtils.CLICKABLE, true);
        Bundle merchSearchBundle = new Bundle();
        merchSearchBundle.putBoolean(ConstantUtils.CLICKABLE, true);
        Bundle merchAllcoateBundle = new Bundle();
        merchAllcoateBundle.putBoolean(ConstantUtils.allocate, true);
        merchAllcoateBundle.putBoolean(ConstantUtils.from_home, true);
        merchAllcoateBundle.putBoolean(ConstantUtils.CLICKABLE, true);
        Bundle staffAllcoateBundle = new Bundle();
        staffAllcoateBundle.putBoolean(ConstantUtils.from_home, true);
        staffAllcoateBundle.putBoolean(ConstantUtils.CLICKABLE, true);
        Bundle staffCollateBundle = new Bundle();
        staffCollateBundle.putBoolean(ConstantUtils.CLICKABLE, true);
        Bundle commonRefundBundle = new Bundle();
        commonRefundBundle.putString(ConstantUtils.CustomersTopSysNo, ConstantUtils.SYS_NO);
        commonRefundBundle.putBoolean(ConstantUtils.CLICKABLE, true);
        Bundle staticsBundle = new Bundle();
        staticsBundle.putBoolean(ConstantUtils.CLICKABLE, false);

        Bundle[] bundles = new Bundle[]{
                staffSearchBundle,
                orderSearchBundle,
                merchSearchBundle,
                merchAllcoateBundle,
                staffAllcoateBundle,
                staffCollateBundle,
                commonRefundBundle,
                staticsBundle
        };
        bean.setMenu_list(list);
        bean.setReses(reses);
        bean.setReses_select(reses_select);
//        bean.setIntents(intents);
        bean.setActions(actions);
        bean.setBundles(bundles);
    }

    @Override
    public void initMerchantRecMenu() {
        List<String> list = new ArrayList<String>();
        String[] string_array = context.getResources().getStringArray(R.array.merchant_rec_menu);
        for (int i = 0; i < string_array.length; i++) {
            list.add(string_array[i]);
        }
        List<String> actions = new ArrayList<>();
        actions.add(IOrdersProvider.ORDERS_ACT_STAFF_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_ORDER_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_STAFF_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_STAFF_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_COMMON_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_COMMON_REFUND_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_ORDER_DETIALS_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_COMMON_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_ORDER_DETIALS_SEARCH);

        Bundle staffSearchBundle = new Bundle();
        staffSearchBundle.putBoolean(ConstantUtils.fromHome, true);
        staffSearchBundle.putBoolean(ConstantUtils.fromMerch, true);
        staffSearchBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle orderSearchBundle = new Bundle();
        orderSearchBundle.putBoolean(ConstantUtils.CLICKABLE, true);
        orderSearchBundle.putBoolean(ConstantUtils.FROM_HOME, true);


        Bundle merchQRBundle = new Bundle();
        merchQRBundle.putString("flag", "intent_merchant_qr");
        merchQRBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle userDisBundle = new Bundle();
        userDisBundle.putBoolean(ConstantUtils.fromHome, true);
        userDisBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle rateBundle = new Bundle();
        rateBundle.putBoolean(ConstantUtils.fromMerchHome, true);
        rateBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle refundBundle = new Bundle();
        refundBundle.putString("CustomerSysNo", ConstantUtils.SYS_NO);
        refundBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle refundPrintBundle = new Bundle();
        refundPrintBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle dayCollectBundle = new Bundle();
        dayCollectBundle.putBoolean(ConstantUtils.DAY_COLLECT, true);
        dayCollectBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle staticsBundle = new Bundle();
        staticsBundle.putBoolean("FROM_MER_HOME", true);
        staticsBundle.putBoolean(ConstantUtils.CLICKABLE, true);
        Bundle[] bundles = new Bundle[]{
                staffSearchBundle,
                orderSearchBundle,
                merchQRBundle,
                userDisBundle,
                rateBundle,
                refundBundle,
                refundPrintBundle,
                dayCollectBundle,
                staticsBundle
        };

//        Intent intent_order = new Intent(context, OrdersSearchActivity.class);
//        intent_order.putExtra("fHome", true);
//        Intent intent_merchant_qr = new Intent(context, StaffSearchActivity.class);
//        Intent intent_staff = new Intent(context, StaffSearchActivity.class);
//        Intent intent_user_dis = new Intent(context, StaffSearchActivity.class);
//        Intent intent_rate = new Intent(context, CommonSearchActivity.class);
//        Intent intent_rateUP = new Intent(context, CommonSearchActivity.class);
//        Intent intentRefund = new Intent(context, CommonSearchRefundActivity.class);
//        Intent intentRefundPrint = new Intent(context, RefundPrintSearchActivity.class);
//        Intent intentDayCollect = new Intent(context, CommonSearchActivity.class);

        int day_collect = R.drawable.day_collect;
        if (!ConstantUtils.IPP3OrderByDayList) {
            day_collect = R.drawable.day_collect_grey;
            dayCollectBundle.putBoolean(ConstantUtils.CLICKABLE, false);
//            intentDayCollect.putExtra(ConstantUtils.ACTIVEBTN, false);
        }
        int order_print = R.drawable.print_order;
        if (!ConstantUtils.Order_statistics) {
            order_print = R.drawable.print_order_grey;
            refundPrintBundle.putBoolean(ConstantUtils.CLICKABLE, false);
//            intentRefundPrint.putExtra(ConstantUtils.ACTIVEBTN, false);
        }
        int staff_normal = R.drawable.staff_normal;
        int order_normal = R.drawable.order_normal;
        int merchant_qr_normal = R.drawable.merchant_qr_normal;
        int statics_normal = R.drawable.refund_search_icon_m;
        int statics_select = R.drawable.refund_search_icon_m;
        int staff_user_destribute = R.drawable.staff_user_destribute;
        int tenants_rate1 = R.drawable.rate_order_r;
//        int tenants_rate2 = R.drawable.up_rate_order_r;
        int tenants_rate3 = R.drawable.refund_search_icon;
        int staff_select = R.drawable.staff_select;
        int order_select = R.drawable.order_select;
        int merchant_qr_select = R.drawable.merchant_qr_select;
        if (!ConstantUtils.Staff_staff_list) {
            staff_normal = R.drawable.staff_normal_grey;
            staff_select = R.drawable.staff_normal_grey;
            staffSearchBundle.putBoolean(ConstantUtils.CLICKABLE, false);
//            intent_staff.putExtra(ConstantUtils.ACTIVEBTN, false);
        }
        if (!ConstantUtils.Order_order_search) {
            order_normal = R.drawable.order_normal_grey;
            order_select = R.drawable.order_normal_grey;
//            intent_order.putExtra(ConstantUtils.ACTIVEBTN, false);
            orderSearchBundle.putBoolean(ConstantUtils.CLICKABLE, false);

        }
        if (!ConstantUtils.Permission_permission_assignment) {
            staff_user_destribute = R.drawable.staff_user_destribute_grey;
//            intent_user_dis.putExtra(ConstantUtils.ACTIVEBTN, false);
            userDisBundle.putBoolean(ConstantUtils.CLICKABLE, false);
        }
        if (!ConstantUtils.OrderFund_orderfund) {
            tenants_rate1 = R.drawable.rate_order__r_grey;
//            intent_rate.putExtra(ConstantUtils.ACTIVEBTN, false);
            rateBundle.putBoolean(ConstantUtils.CLICKABLE, false);
        }
        if (!ConstantUtils.RefundSearch_refund_search) {
            tenants_rate3 = R.drawable.refund_search_icon_grey;
//            intentRefund.putExtra(ConstantUtils.ACTIVEBTN, false);
            refundBundle.putBoolean(ConstantUtils.CLICKABLE, false);
        }

//        Intent intent_statics = new Intent(context, RefundPrintSearchActivity.class);

        if (!ConstantUtils.IPP3OrderFundList_Shop_SP) {
//            intent_statics.putExtra(ConstantUtils.ACTIVEBTN, false);
            staticsBundle.putBoolean(ConstantUtils.CLICKABLE, false);
            statics_normal = R.drawable.refund_search_icon_m_grey;
            statics_select = R.drawable.refund_search_icon_m_grey;

        }
        if (!ConstantUtils.Qrcode_index) {
//            intent_merchant_qr.putExtra(ConstantUtils.ACTIVEBTN, false);
            merchQRBundle.putBoolean(ConstantUtils.CLICKABLE, false);
            merchant_qr_normal = R.drawable.merchant_qr_normal_grey;
            merchant_qr_select = R.drawable.merchant_qr_normal_grey;
        }
        int[] reses = new int[]{
                staff_normal, order_normal,
                merchant_qr_normal,
                staff_user_destribute, tenants_rate1, tenants_rate3, order_print, day_collect, statics_normal};
        int[] reses_select = new int[]{
                staff_select, order_select,
                merchant_qr_select,
                staff_user_destribute, tenants_rate1, tenants_rate3, order_print, day_collect, statics_select};


//        intent_rateUP.putExtra(ConstantUtils.fromMerchHome, true);
//        intent_rateUP.putExtra("top_rate", true);

//        Intent[] intents = new Intent[]{
//                intent_staff,
//                intent_order,
//                intent_merchant_qr,
//                intent_user_dis,
//                intent_rate,
//                intentRefund,
//                intentRefundPrint,
//                intentDayCollect,
//                intent_statics};
        bean.setMenu_list(list);
        bean.setReses(reses);
        bean.setReses_select(reses_select);
        bean.setBundles(bundles);
        bean.setActions(actions);
//        bean.setIntents(intents);
    }

    @Override
    public MerchantHomeBean loadInstance() {
        if (bean != null) {
            return bean;
        } else {
            return null;
        }
    }
}
