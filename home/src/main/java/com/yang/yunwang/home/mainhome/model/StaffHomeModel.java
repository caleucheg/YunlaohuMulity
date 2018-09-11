package com.yang.yunwang.home.mainhome.model;

import android.content.Context;
import android.os.Bundle;

import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.moduleinterface.provider.IPayProvider;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.bean.StaffHomeBean;
import com.yang.yunwang.home.mainhome.contract.StaffHomeContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public class StaffHomeModel implements StaffHomeContract.Model {

    private StaffHomeBean staffHomeBean;
    private Context context;

    public StaffHomeModel(Context context) {
        this.context = context;
        staffHomeBean = new StaffHomeBean();
    }

    @Override
    public void setInfos(String sys_no, String customer) {
        staffHomeBean.setSys_no(sys_no);
        staffHomeBean.setCustomer(customer);
    }


    /**
     * 服务商员工 上部菜单
     */
    @Override
    public void initServiceMainFunctionList() {
        String[] lists = context.getResources().getStringArray(R.array.merchant_main_function_list);
        int staff_search_unselect = R.drawable.staff_search_unselect;
        int staff_search_selected = R.drawable.staff_search_selected;
        int merchant_search_unselect = R.drawable.merchant_search_unselect;
        int merchant_search_selected = R.drawable.merchant_search_selected;
        List<String> actions = new ArrayList<>();
        actions.add(IOrdersProvider.ORDERS_ACT_ORDER_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_MERCH_SEARCH);


        Bundle staffSearchBundle = new Bundle();
        staffSearchBundle.putBoolean(ConstantUtils.merchStaff, true);
        staffSearchBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle orderSearchBundle = new Bundle();
        orderSearchBundle.putBoolean(ConstantUtils.CLICKABLE, true);
        Bundle[] bundles = new Bundle[]{
                orderSearchBundle,
                staffSearchBundle

        };

//        Intent intent_orders = new Intent(context, OrdersSearchActivity.class);
//        intent_orders.putExtra(ConstantUtils.ACTIVEBTN, true);
//        Intent intent_merchant_search = new Intent(context, MerchantSearchActivity.class);
//        intent_merchant_search.putExtra(ConstantUtils.ACTIVEBTN, true);
        if (!ConstantUtils.Order_order_search) {
            staff_search_selected = R.drawable.staff_search_unselect_grey;
            staff_search_unselect = R.drawable.staff_search_unselect_grey;
//            intent_orders.putExtra(ConstantUtils.ACTIVEBTN, false);
            orderSearchBundle.putBoolean(ConstantUtils.CLICKABLE, false);
        }
        if (!ConstantUtils.Business_business) {
            merchant_search_unselect = R.drawable.merchant_search_unselect_grey;
            merchant_search_selected = R.drawable.merchant_search_unselect_grey;
//            intent_merchant_search.putExtra(ConstantUtils.ACTIVEBTN, false);
            staffSearchBundle.putBoolean(ConstantUtils.CLICKABLE, false);
        }
        int[] reses = new int[]{staff_search_unselect, merchant_search_unselect};
        int[] reses_selected = new int[]{staff_search_selected, merchant_search_selected};

//        Intent[] main_intents = new Intent[]{intent_orders, intent_merchant_search};
        List<String> main_function_list = new ArrayList<String>();
        for (int i = 0; i < lists.length; i++) {
            main_function_list.add(lists[i]);
        }
        staffHomeBean.setMain_function_list(main_function_list);
        staffHomeBean.setMain_function_res(reses);
        staffHomeBean.setMain_function_res_selected(reses_selected);
//        staffHomeBean.setMain_intents(main_intents);
        staffHomeBean.setMianActions(actions);
        staffHomeBean.setMainBundles(bundles);
    }

    /**
     * 服务商员工 下部菜单
     */
    @Override
    public void initServiceSubFunctionList() {
        String[] lists = context.getResources().getStringArray(R.array.merchant_sub_function_list);
        int up_rate_order = R.drawable.up_rate_order;
        int rate_order = R.drawable.rate_order;
        List<String> actions = new ArrayList<>();
        actions.add(IOrdersProvider.ORDERS_ACT_COMMON_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_COMMON_SEARCH);


        Bundle orderSearchBundle = new Bundle();
        orderSearchBundle.putString("rate_type", "upRate");
        orderSearchBundle.putBoolean("top_rate", true);
        orderSearchBundle.putBoolean(ConstantUtils.CLICKABLE, true);
        Bundle staffSearchBundle = new Bundle();
        staffSearchBundle.putBoolean("from_home", true);
        staffSearchBundle.putString("rate_type", "shopRate");
        staffSearchBundle.putBoolean(ConstantUtils.merchStaff, true);
        staffSearchBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle[] bundles = new Bundle[]{
                orderSearchBundle,
                staffSearchBundle

        };

//        Intent intent_orders = new Intent(context, CommonSearchActivity.class);
//        Intent intent_merchant_search = new Intent(context, CommonSearchActivity.class);
        if (!ConstantUtils.OrderFund_orderfund) {
//            intent_orders.putExtra(ConstantUtils.ACTIVEBTN, false);
            orderSearchBundle.putBoolean(ConstantUtils.CLICKABLE, false);
            up_rate_order = R.drawable.up_rate_order_grey;
        }
        if (!ConstantUtils.OrderFund_orderfund_Top_1) {
//            intent_merchant_search.putExtra(ConstantUtils.ACTIVEBTN, false);
            staffSearchBundle.putBoolean(ConstantUtils.CLICKABLE, false);
            rate_order = R.drawable.rate_order_grey;
        }
        int[] reses = new int[]{up_rate_order, rate_order};
        int[] reses_selected = new int[]{up_rate_order, rate_order};


//        Intent[] main_intents = new Intent[]{intent_orders, intent_merchant_search};
        List<String> main_function_list = new ArrayList<String>();
        Collections.addAll(main_function_list, lists);
        staffHomeBean.setSub_function_list(main_function_list);
        staffHomeBean.setSub_function_res(reses);
        staffHomeBean.setSub_function_res_selected(reses_selected);
//        staffHomeBean.setSub_intents(main_intents);
        staffHomeBean.setSubActions(actions);
        staffHomeBean.setSubBundles(bundles);
    }

    @Override
    public void initMainFunctionList() {
        String[] lists = context.getResources().getStringArray(R.array.staff_main_function_list);
//        Intent intent_scan = new Intent(context, ScanCodeActivity.class);
//        Intent intent_qrcode = new Intent(context, QRCodeActivity.class);
//        Intent intent_orders = new Intent(context, OrdersSearchActivity.class);

        List<String> actions = new ArrayList<>();
        actions.add(IPayProvider.PAY_ACT_SCAN_CODE);
        actions.add(IPayProvider.PAY_ACT_QR_CODE);
        actions.add(IOrdersProvider.ORDERS_ACT_ORDER_SEARCH);
        Bundle scanCodeBundle = new Bundle();
        scanCodeBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle qrCodeBundle = new Bundle();
        qrCodeBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle orderSearchBundle = new Bundle();
        orderSearchBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle[] bundles = new Bundle[]{
                scanCodeBundle,
                qrCodeBundle,
                orderSearchBundle


        };


        int staff_scan_unselect = R.drawable.staff_scan_unselect;
        int staff_qr_unselect = R.drawable.staff_qr_unselect;
        int staff_search_unselect = R.drawable.staff_search_unselect;
        int staff_scan_selected = R.drawable.staff_scan_selected;
        int staff_qr_selected = R.drawable.staff_qr_selected;
        int staff_search_selected = R.drawable.staff_search_selected;
        if (!ConstantUtils.Pay_scan_code_payment_Alipay && !ConstantUtils.Pay_scan_code_payment) {
            staff_scan_unselect = R.drawable.staff_scan_unselect_grey;
            staff_qr_unselect = R.drawable.staff_qr_unselect_grey;
            staff_qr_selected = R.drawable.staff_qr_unselect_grey;
            staff_scan_selected = R.drawable.staff_scan_unselect_grey;
//            intent_scan.putExtra(ConstantUtils.ACTIVEBTN, false);
            scanCodeBundle.putBoolean(ConstantUtils.CLICKABLE, false);
            qrCodeBundle.putBoolean(ConstantUtils.CLICKABLE, false);
//            intent_qrcode.putExtra(ConstantUtils.ACTIVEBTN, false);
        }
        if (!ConstantUtils.Order_order_search) {
            staff_search_selected = R.drawable.staff_search_unselect_grey;
            staff_search_unselect = R.drawable.staff_search_unselect_grey;
            orderSearchBundle.putBoolean(ConstantUtils.CLICKABLE, false);
//            intent_orders.putExtra(ConstantUtils.ACTIVEBTN, false);
        }
        int[] reses = new int[]{staff_scan_unselect, staff_qr_unselect, staff_search_unselect};
        int[] reses_selected = new int[]{staff_scan_selected, staff_qr_selected, staff_search_selected};
//        Intent[] main_intents = new Intent[]{intent_scan, intent_qrcode, intent_orders};
        List<String> main_function_list = new ArrayList<String>();
        for (int i = 0; i < lists.length; i++) {
            main_function_list.add(lists[i]);
        }
        staffHomeBean.setMain_function_list(main_function_list);
        staffHomeBean.setMain_function_res(reses);
        staffHomeBean.setMain_function_res_selected(reses_selected);
//        staffHomeBean.setMain_intents(main_intents);
        staffHomeBean.setMainBundles(bundles);
        staffHomeBean.setMianActions(actions);
    }

    @Override
    public void initSubFunctionList() {
        String[] lists = context.getResources().getStringArray(R.array.staff_sub_function_list);
//        Intent intent_wxorders = new Intent(context, WXOrdersSearchActivity.class);
//        Intent intent_zfborders = new Intent(context, ZFBOrdersSearchActivity.class);
//        Intent intent_refund = new Intent(context, UnRefundActivity.class);
//        Intent intent_refundsearch = new Intent(context, RefundSearchActivity.class);
//        Intent intent_statistics = new Intent(context, PersonnelQRActivity.class);
//        intent_statistics.putExtra("sysNO", ConstantUtils.SYS_NO);
//        Intent intent_rate = new Intent(context, CommonSearchActivity.class);
//        Intent intentRefundPrint = new Intent(context, RefundPrintSearchActivity.class);
//        Intent[] sub_intents = new Intent[]{intent_wxorders,
//                intent_zfborders, intent_refund, intent_refundsearch,
//                intent_statistics, intent_rate, intentRefundPrint};
        List<String> actions = new ArrayList<>();
        actions.add(IOrdersProvider.ORDERS_ACT_WX_PLANT);
        actions.add(IOrdersProvider.ORDERS_ACT_ZFB_PLANT_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_UNREFUND_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_REFUND_SEARCH);
        //TODO switch
        actions.add(IOrdersProvider.ORDERS_ACT_PERSONAL_QR);
        actions.add(IOrdersProvider.ORDERS_ACT_COMMON_SEARCH);
        actions.add(IOrdersProvider.ORDERS_ACT_ORDER_DETIALS_SEARCH);

        Bundle wxPlantBundle = new Bundle();
        wxPlantBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle zfbPalntBundle = new Bundle();
        zfbPalntBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle unRefundBundle = new Bundle();
        unRefundBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle refundSearchBundle = new Bundle();
        refundSearchBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle persnQRBundle = new Bundle();
        persnQRBundle.putString("sysNO", ConstantUtils.SYS_NO);
        persnQRBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle commonSearchBundle = new Bundle();
        commonSearchBundle.putBoolean("isUserRate", true);
        commonSearchBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle refundPrintBundle = new Bundle();
        refundPrintBundle.putBoolean(ConstantUtils.PRINT_F_STAFF, true);
        refundPrintBundle.putBoolean(ConstantUtils.CLICKABLE, true);

        Bundle[] bundles = new Bundle[]{
                wxPlantBundle,
                zfbPalntBundle,
                unRefundBundle,
                refundSearchBundle,
                persnQRBundle,
                commonSearchBundle,
                refundPrintBundle
        };

//        intentRefundPrint.putExtra(ConstantUtils.PRINT_F_STAFF, true);
        int order_print = R.drawable.staff_print;
        int wx_orders_unselect = R.drawable.wx_orders_unselect;
        int zfb_orders_unselect = R.drawable.zfb_orders_unselect;
        int refunds_unselect = R.drawable.refunds_unselect;
        int refunds_search_unselect = R.drawable.refunds_search_unselect;
        int staff_qr_normal = R.drawable.paycode_image;
        int rate_order = R.drawable.rate_order;
        int wx_orders_selected = R.drawable.wx_orders_selected;
        int zfb_orders_selected = R.drawable.zfb_orders_selected;
        int refunds_selected = R.drawable.refunds_selected;
        int refunds_search_selected = R.drawable.refunds_search_selected;
        int staff_qr_select = R.drawable.paycode_image;
        if (!ConstantUtils.Wxpay_native) {
            staff_qr_normal = R.drawable.paycode_image_grey;
            staff_qr_select = R.drawable.paycode_image_grey;
//            intent_statistics.putExtra(ConstantUtils.ACTIVEBTN, false);
            persnQRBundle.putBoolean(ConstantUtils.CLICKABLE, false);
        }
        if (!ConstantUtils.Order_platform_order_search) {
            wx_orders_selected = R.drawable.wx_orders_unselect_grey;
            wx_orders_unselect = R.drawable.wx_orders_unselect_grey;
            wxPlantBundle.putBoolean(ConstantUtils.CLICKABLE, false);
//            intent_wxorders.putExtra(ConstantUtils.ACTIVEBTN, false);
        }

        if (!ConstantUtils.Order_order_search_alipay) {
            zfb_orders_selected = R.drawable.zfb_orders_unselect_grey;
            zfb_orders_unselect = R.drawable.zfb_orders_unselect_grey;
//            intent_zfborders.putExtra(ConstantUtils.ACTIVEBTN, false);
            zfbPalntBundle.putBoolean(ConstantUtils.CLICKABLE, false);
        }
        if (!ConstantUtils.Refund_refund) {
            refunds_selected = R.drawable.refunds_unselect_grey;
            refunds_unselect = R.drawable.refunds_unselect_grey;
            unRefundBundle.putBoolean(ConstantUtils.CLICKABLE, false);
//            intent_refund.putExtra(ConstantUtils.ACTIVEBTN, false);
        }
        if (!ConstantUtils.RefundSearch_refund_search) {
            refunds_search_selected = R.drawable.refunds_search_unselect_grey;
            refunds_search_unselect = R.drawable.refunds_search_unselect_grey;
//            intent_refundsearch.putExtra(ConstantUtils.ACTIVEBTN, false);
            refundSearchBundle.putBoolean(ConstantUtils.CLICKABLE, false);
        }
        if (!ConstantUtils.OrderFund_orderfund) {
            rate_order = R.drawable.rate_order_grey;
            commonSearchBundle.putBoolean(ConstantUtils.CLICKABLE, false);
//            intent_rate.putExtra(ConstantUtils.ACTIVEBTN, false);
        }


        if (!ConstantUtils.Order_statistics) {
//            intentRefundPrint.putExtra(ConstantUtils.ACTIVEBTN, false);
            refundPrintBundle.putBoolean(ConstantUtils.CLICKABLE, false);
            order_print = R.drawable.staff_print_grey;
        }
        int[] reses = new int[]{wx_orders_unselect, zfb_orders_unselect,
                refunds_unselect, refunds_search_unselect,
                staff_qr_normal, rate_order, order_print};
        int[] reses_selected = new int[]{wx_orders_selected, zfb_orders_selected,
                refunds_selected, refunds_search_selected,
                staff_qr_select, rate_order, order_print};


        List<String> main_function_list = new ArrayList<String>();
        for (int i = 0; i < lists.length; i++) {
            main_function_list.add(lists[i]);
        }
        staffHomeBean.setSub_function_list(main_function_list);
        staffHomeBean.setSub_function_res(reses);
        staffHomeBean.setSub_function_res_selected(reses_selected);
        staffHomeBean.setSubActions(actions);
        staffHomeBean.setSubBundles(bundles);
//        staffHomeBean.setSub_intents(sub_intents);
    }

    @Override
    public void initBannerList() {

    }

    @Override
    public StaffHomeBean loadInstance() {
        if (staffHomeBean != null) {
            return staffHomeBean;
        } else {
            return null;
        }
    }
}
