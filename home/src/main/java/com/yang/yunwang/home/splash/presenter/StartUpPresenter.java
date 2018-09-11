package com.yang.yunwang.home.splash.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.socks.library.KLog;
import com.yang.yunwang.base.basereq.bean.staffinfo.StaffInfoReq;
import com.yang.yunwang.base.dao.PassageWay;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.JPUtils;
import com.yang.yunwang.base.util.JsonUtils;
import com.yang.yunwang.base.util.utils.jpush.PushSpeakService;
import com.yang.yunwang.home.BuildConfig;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.homeservice.bean.MerchAllcoateReq;
import com.yang.yunwang.home.homeservice.bean.MerchAllcoateResp;
import com.yang.yunwang.home.homeservice.bean.StaffAllcoateReq;
import com.yang.yunwang.home.homeservice.bean.StaffAllcoateResp;
import com.yang.yunwang.home.splash.contract.StartUpContract;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/6/11.
 */

public class StartUpPresenter implements StartUpContract.Presenter {

    private boolean isSavedWx = false;
    private final StartUpContract.View view;
    private final Context context;

    public StartUpPresenter(StartUpContract.View view, Context context) {
        view.setPresenter(this);
        this.view = view;
        this.context = context;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void init() {
        initMark();
    }

    @Override
    public void getAllcoate() {
        jumpHomePage();
    }

    private void initMark() {
        String s = CommonShare.getPassageDataW(context);
        if (JsonUtils.isGoodJson(s) && !TextUtils.isEmpty(s)) {
            KLog.i(s);
            KLog.i("good-json");
            getStoreRemarks(s);
        } else {
            initMarkWX();
        }
    }

    private void getStoreRemarks(String s) {
        if (TextUtils.isEmpty(s)) {
            try {
                saveWxPassage(context.getString(R.string.store_wx_json));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        } else {
            try {
                saveWxPassage(s);
            } catch (JSONException e1) {
                e1.printStackTrace();
                try {
                    saveWxPassage(context.getString(R.string.store_wx_json));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private void initMarkWX() {
        KLog.i("initmarks");
        HomeREService.getInstance(context)
                .getPassageWay(new StaffInfoReq())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new BaseObserver<JsonArray>(context) {
                    @Override
                    protected void doOnNext(JsonArray value) {
                        try {
                            String result=value.toString();
                            if (!TextUtils.isEmpty(result) && result.length() > 2) {
                                if (JsonUtils.isGoodJson(result)) {
                                    ConstantUtils.isGetPassageMark = true;
                                    CommonShare.putPassageDataW(context, result);
                                    saveWxPassage(result);
                                } else {
                                    try {
                                        saveWxPassage(context.getString(R.string.store_wx_json));
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            } else {
                                String s = CommonShare.getPassageDataW(context);
                                if (TextUtils.isEmpty(s)) {
                                    try {
                                        saveWxPassage(context.getString(R.string.store_wx_json));
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                } else {
                                    try {
                                        saveWxPassage(s);
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                        try {
                                            saveWxPassage(context.getString(R.string.store_wx_json));
                                        } catch (JSONException e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            KLog.e("exception", e.getMessage());
                            String s = CommonShare.getPassageDataW(context);
                            if (TextUtils.isEmpty(s)) {
                                try {
                                    saveWxPassage(context.getString(R.string.store_wx_json));
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            } else {
                                try {
                                    saveWxPassage(s);
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                    try {
                                        saveWxPassage(context.getString(R.string.store_wx_json));
                                    } catch (JSONException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        String s = CommonShare.getPassageDataW(context);
                        if (TextUtils.isEmpty(s)) {
                            try {
                                saveWxPassage(context.getString(R.string.store_wx_json));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            try {
                                saveWxPassage(s);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                                try {
                                    saveWxPassage(context.getString(R.string.store_wx_json));
                                } catch (JSONException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }

                    }
                });


    }

    private void saveWxPassage(String result) throws JSONException {
        synchronized (context) {
            KLog.i(result);
            if (!isSavedWx) {
                isSavedWx = true;
                ConstantUtils.PASSAGWE_WAYS.clear();
                ConstantUtils.WX_Passageway.clear();
                ConstantUtils.WX_Type.clear();
                ConstantUtils.WX_TypeName.clear();
                ConstantUtils.WX_PassageWayName.clear();
                ConstantUtils.ZFB_Passageway.clear();
                ConstantUtils.ZFB_Type.clear();
                ConstantUtils.ZFB_TypeName.clear();
                ConstantUtils.ZFB_PassageWayName.clear();
                ArrayList<String> listPassageWay = new ArrayList();
                ArrayList<String> listType = new ArrayList();
                ArrayList<String> listTypeName = new ArrayList();
                ArrayList<String> listPassageWayName = new ArrayList();
                ArrayList<String> zListPassageWay = new ArrayList();
                ArrayList<String> zListType = new ArrayList();
                ArrayList<String> zListTypeName = new ArrayList();
                ArrayList<String> zListPassageWayName = new ArrayList();
                JsonParser parser = new JsonParser();
                JsonArray jsonArrayS = parser.parse(result).getAsJsonArray();
                Gson gson = new Gson();
                for (JsonElement user : jsonArrayS) {
                    filterState(listPassageWay, listType, listTypeName, listPassageWayName, gson, user, zListPassageWay, zListPassageWayName, zListType, zListTypeName);
                    KLog.i(ConstantUtils.PASSAGWE_WAYS);
                }
                KLog.i(result);
                ConstantUtils.WX_Passageway = listPassageWay;
                ConstantUtils.WX_Type = listType;
                ConstantUtils.WX_TypeName = listTypeName;
                ConstantUtils.WX_PassageWayName = listPassageWayName;
                ConstantUtils.ZFB_Passageway = zListPassageWay;
                ConstantUtils.ZFB_Type = zListType;
                ConstantUtils.ZFB_TypeName = zListTypeName;
                ConstantUtils.ZFB_PassageWayName = zListPassageWayName;
                KLog.i(ConstantUtils.WX_Passageway);
                KLog.i(ConstantUtils.WX_Type);
                KLog.i(ConstantUtils.WX_TypeName);
                KLog.i(ConstantUtils.WX_PassageWayName);
                KLog.i(ConstantUtils.ZFB_Passageway);
                KLog.i(ConstantUtils.ZFB_Type);
                KLog.i(ConstantUtils.ZFB_TypeName);
                KLog.i(ConstantUtils.ZFB_PassageWayName);
            } else {
                KLog.i("ERROR1");
            }
        }
    }

    private void filterState(ArrayList<String> listPassageWay, ArrayList<String> listType, ArrayList<String> listTypeName, ArrayList<String> listPassageWayName, Gson gson, JsonElement user, ArrayList<String> zListPassageWay, ArrayList<String> zListPassageWayName, ArrayList<String> zListType, ArrayList<String> zListTypeName) {
        PassageWay userBean = gson.fromJson(user, PassageWay.class);
        if (BuildConfig.DEBUG) {
            if (!ConstantUtils.PASSAGWE_WAYS.contains(userBean)) {
                ConstantUtils.PASSAGWE_WAYS.add(userBean);
            } else {
                KLog.i("ERROR2");
            }
            String remarks = userBean.getRemarks();
            if (TextUtils.equals("WX", remarks)) {
                if (!listType.contains(userBean.getType())) {
                    listPassageWay.add(userBean.getPassageWay());
                    listType.add(userBean.getType());
                    listTypeName.add(userBean.getTypeName());
                    listPassageWayName.add(userBean.getPassageWayName());
                } else {
                    KLog.i("ERROR3");
                }
            } else if (TextUtils.equals("AliPay", remarks)) {
                if (!zListType.contains(userBean.getType())) {
                    zListPassageWay.add(userBean.getPassageWay());
                    zListType.add(userBean.getType());
                    zListTypeName.add(userBean.getTypeName());
                    zListPassageWayName.add(userBean.getPassageWayName());
                } else {
                    KLog.i("ERROR4");
                }
            } else {
                KLog.i("ERROR5");
            }

        } else {
            if (TextUtils.equals(userBean.getState(), "0")) {
                if (!ConstantUtils.PASSAGWE_WAYS.contains(userBean)) {
                    ConstantUtils.PASSAGWE_WAYS.add(userBean);
                } else {
                    KLog.i("ERROR2");
                }
                String remarks = userBean.getRemarks();
                if (TextUtils.equals("WX", remarks)) {
                    if (!listType.contains(userBean.getType())) {
                        listPassageWay.add(userBean.getPassageWay());
                        listType.add(userBean.getType());
                        listTypeName.add(userBean.getTypeName());
                        listPassageWayName.add(userBean.getPassageWayName());
                    } else {
                        KLog.i("ERROR3");
                    }
                } else if (TextUtils.equals("AliPay", remarks)) {
                    if (!zListType.contains(userBean.getType())) {
                        zListPassageWay.add(userBean.getPassageWay());
                        zListType.add(userBean.getType());
                        zListTypeName.add(userBean.getTypeName());
                        zListPassageWayName.add(userBean.getPassageWayName());
                    } else {
                        KLog.i("ERROR4");
                    }
                } else {
                    KLog.i("ERROR5");
                }
            }
        }
    }

    private void jumpHomePage() {
        String user = CommonShare.getLoginData(context, "User", "");
        if (!user.equals("")) {
            ConstantUtils.USER = user;
            ConstantUtils.SYS_NO = CommonShare.getLoginData(context, "SysNo", "");
            ConstantUtils.CUSTOMER = CommonShare.getLoginData(context, "Customer", "");
            ConstantUtils.CUSTOMERS_TYPE = CommonShare.getLoginData(context, "CustomersType", "");
            ConstantUtils.IS_ATFER_LOGIN_INIT = true;
            ConstantUtils.NEW_TYPE = CommonShare.getTypeData(context);
            //DONE switch version
            if (!DycLibIntent.hasModule()){
                KLog.i("startService");
                if (TextUtils.equals("3", ConstantUtils.NEW_TYPE)) {
                    JPUtils.setTags(context, ConstantUtils.SYS_NO);
                    CommonShare.putJPushIDBoolean(context, false);
                    Intent service = new Intent(context, PushSpeakService.class);
                    context.startService(service);
                }
            }
            initAllocateData();
        } else {
            view.jumpLogin();
        }
    }

    private void initAllocateData() {
        view.showDialog();
        if (ConstantUtils.CUSTOMERS_TYPE != null && !TextUtils.equals("", ConstantUtils.CUSTOMERS_TYPE)) {
            //  商户/服务商角色
            MerchAllcoateReq accessToken = new MerchAllcoateReq();
            accessToken.setCustomerServiceSysNo(Long.parseLong(ConstantUtils.SYS_NO));
            HomeREService.getInstance(context)
                    .getMerchAllcoate(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<List<MerchAllcoateResp>>(context) {
                        @Override
                        protected void doOnNext(List<MerchAllcoateResp> value) {

                            ArrayList tl = new ArrayList();
                            for (int i = 0; i < value.size(); i++) {
                                tl.add(value.get(i).getURL());
                            }
                            KLog.i(tl);
                            saveAllocate(tl);
                            ConstantUtils.INIT_ALLOCATE = true;
                            view.jumpMain();
                            view.dismissDialog();
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                            Toast.makeText(context, "权限获取失败,请重新登录", Toast.LENGTH_SHORT).show();
                            view.jumpLogin();
                            view.dismissDialog();
                        }
                    });

        } else {
            StaffAllcoateReq accessToken = new StaffAllcoateReq();
            accessToken.setSystemUserSysNo(Long.parseLong(ConstantUtils.SYS_NO));
            HomeREService.getInstance(context)
                    .getStaffAllcoate(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<List<StaffAllcoateResp>>(context) {
                        @Override
                        protected void doOnNext(List<StaffAllcoateResp> value) {

                            ArrayList tl = new ArrayList();
                            for (int i = 0; i < value.size(); i++) {
                                tl.add(value.get(i).getURL());
                            }
                            KLog.i(tl);
                            saveAllocate(tl);
                            ConstantUtils.INIT_ALLOCATE = true;
                            view.jumpMain();
                            view.dismissDialog();
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                            Toast.makeText(context, "权限获取失败,请重新登录", Toast.LENGTH_SHORT).show();
                            view.jumpLogin();
                            view.dismissDialog();
                        }
                    });

        }

    }

    private void saveAllocate(ArrayList tl) {
        KLog.i("saveAllocate---" + tl);
        ConstantUtils.Business_business = tl.contains(ConstantUtils.business_business);
        ConstantUtils.OrderExtend_order_extend = tl.contains(ConstantUtils.orderExtend_order_extend);
        ConstantUtils.Permission_permission_business = tl.contains(ConstantUtils.permission_permission_business);
        ConstantUtils.Summary_summary_search = tl.contains(ConstantUtils.summary_summary_search);
        ConstantUtils.OrderFund_orderfund_Top_1 = tl.contains(ConstantUtils.orderFund_orderfund_Top_1);
        ConstantUtils.OrderFund_orderfund = tl.contains(ConstantUtils.orderFund_orderfund);
        ConstantUtils.Wxpay_native = tl.contains(ConstantUtils.wxpay_native);
        ConstantUtils.Qrcode_index = tl.contains(ConstantUtils.qrcode_index);
        ConstantUtils.Staff_staff_list = tl.contains(ConstantUtils.staff_staff_list);
        ConstantUtils.Staff_staff_register = tl.contains(ConstantUtils.staff_staff_register);
        ConstantUtils.Permission_permission_assignment = tl.contains(ConstantUtils.permission_permission_assignment);
        ConstantUtils.Pay_scan_code_payment_Alipay = tl.contains(ConstantUtils.pay_scan_code_payment_Alipay);
        ConstantUtils.Pay_scan_code_payment = tl.contains(ConstantUtils.pay_scan_code_payment);
        ConstantUtils.Refund_refund = tl.contains(ConstantUtils.refund_refund);
        ConstantUtils.RefundSearch_refund_search = tl.contains(ConstantUtils.refundSearch_refund_search);
        ConstantUtils.Order_order_search_alipay = tl.contains(ConstantUtils.order_order_search_alipay);
        ConstantUtils.Order_platform_order_search = tl.contains(ConstantUtils.order_platform_order_search);
        ConstantUtils.Order_order_search = tl.contains(ConstantUtils.order_order_search);
        ConstantUtils.Conff_zfbConfig = tl.contains(ConstantUtils.conff_zfbConfig);
        ConstantUtils.Conff_wxConfig = tl.contains(ConstantUtils.conff_wxConfig);
        ConstantUtils.Order_statistics = tl.contains(ConstantUtils.order_statistics);
        ConstantUtils.IPP3OrderByDayList = tl.contains(ConstantUtils.IPP3OrderByDayListS);
        ConstantUtils.IPP3OrderFundList_Shop_SP = tl.contains(ConstantUtils.IPP3OrderFundList_Shop_SP_S);
    }

}
