package com.yang.yunwang.home.mainhome.presenter;

import android.Manifest;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.basereq.BaseInfoService;
import com.yang.yunwang.base.basereq.bean.merchinfo.MerchInfoReq;
import com.yang.yunwang.base.basereq.bean.merchinfo.MerchInfoResp;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigReq;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigResp;
import com.yang.yunwang.base.basereq.bean.staffinfo.StaffInfoReq;
import com.yang.yunwang.base.basereq.bean.staffinfo.StaffInfoResp;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.JPUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.homeservice.bean.MerchAllcoateReq;
import com.yang.yunwang.home.homeservice.bean.MerchAllcoateResp;
import com.yang.yunwang.home.homeservice.bean.StaffAllcoateReq;
import com.yang.yunwang.home.homeservice.bean.StaffAllcoateResp;
import com.yang.yunwang.home.homeservice.bean.VersionCodeReq;
import com.yang.yunwang.home.homeservice.bean.VersionCodeResp;
import com.yang.yunwang.home.mainhome.contract.MainhomeContract;
import com.yang.yunwang.home.mainhome.model.merchant.MerchantModel;
import com.yang.yunwang.home.mainhome.model.merchant.intf.MerchantModelInterface;
import com.yang.yunwang.home.mainhome.model.staff.StaffModel;
import com.yang.yunwang.home.mainhome.model.staff.inter.StaffModelInterface;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/6/12.
 */

public class MainhomePresenter implements MainhomeContract.Presenter {
    private final MainhomeContract.View view;
    private final Context context;
    private final String customer_type;
    private final LocalActivityManager manager;
    private StaffModelInterface staffModel;
    private MerchantModelInterface merchantModel;

    public MainhomePresenter(MainhomeContract.View view, Context context, LocalActivityManager manager) {
        view.setPresenter(this);
        this.view = view;
        this.context = context;
        this.manager = manager;
        customer_type = ConstantUtils.CUSTOMERS_TYPE;
        if (customer_type != null && !customer_type.equals("")) {
//            服务商与商户角色
            merchantModel = new MerchantModel(context, manager);
        } else {
//            员工角色
            staffModel = new StaffModel(context, manager);
        }
    }

    @Override
    public void initData() {
        String newTypeA = CommonShare.getTypeData(context);
        KLog.i(newTypeA);
        if (TextUtils.equals("3", newTypeA)) {
            checkCarmerPermission();
            //Done switch version
            if (!DycLibIntent.hasModule()) {
                JPushInterface.resumePush(context);
            }
        }
        if (!TextUtils.isEmpty(ConstantUtils.HIGHER_SYS_NO)) {
            initPayConfig(ConstantUtils.HIGHER_SYS_NO);
        }
        if (!DycLibIntent.hasModule()) {
            if (!JPushInterface.getConnectionState(context)) {
                String newType = CommonShare.getTypeData(context);
                if (TextUtils.equals("3", newType)) {
                    JPushInterface.resumePush(context);
                    JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
                    JPushInterface.init(context);            // 初始化 JPush
                    String sysNo = CommonShare.getLoginData(context, "SysNo", "");
                    KLog.i(sysNo);
                    if (!TextUtils.isEmpty(sysNo)) {
                        KLog.i("setTag");
                        JPUtils.setTags(context, sysNo);
                    }
                }
            }
        }
        if (ConstantUtils.INIT_ALLOCATE) {
            String customer_type = ConstantUtils.CUSTOMERS_TYPE;
            if (customer_type != null && !customer_type.equals("")) {
                merchantModel.initTabList();
                merchantModel.initViewList();
            } else {

                staffModel.initTabList();
                staffModel.initViewList();
            }
            setAdapter();
        } else {
            initAllocateData();
        }
    }

    @Override
    public void getPayConfig(String higherSysNo) {
        String type = CommonShare.getTypeData(context);
        if (TextUtils.equals("3", type)) {
            PayConfigReq accessToken = new PayConfigReq();
            accessToken.setCustomerSysNo(higherSysNo);
            BaseInfoService.getInstance(context)
                    .getPayConfig(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new BaseObserver<List<PayConfigResp>>(context) {
                        @Override
                        protected void doOnNext(List<PayConfigResp> value) {
                            if (value.size() != 0) {
                                for (int i = 0; i < value.size(); i++) {
                                    String remarks = value.get(i).getRemarks();
                                    if (TextUtils.equals(remarks, "WX")) {
                                        ConstantUtils.GETED_WX_TYPE = value.get(i).getType() + "";
                                    } else if (TextUtils.equals(remarks, "AliPay")) {
                                        ConstantUtils.GETED_ZFB_TYPE = value.get(i).getType() + "";
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                        }
                    });
        }
    }

    @Override
    public void getServiceVersionCode() {
        getVersionCode();
    }

    @Override
    public void checkPassWord() {
        String position = CommonShare.getLoginData(context, "Position", "");
        String user = CommonShare.getLoginData(context, "User", "");
        final String password = CommonShare.getLoginData(context, "PassWord", "");
        if (!TextUtils.isEmpty(position)) {
            if (Integer.parseInt(position) == 0) {
                MerchInfoReq accessToken = new MerchInfoReq();
                accessToken.setCustomer(user);
                BaseInfoService.getInstance(context)
                        .getMerchInfo(accessToken)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<MerchInfoResp>(context) {
                            @Override
                            protected void doOnNext(MerchInfoResp value) {
                                if (value.getTotalCount() > 0) {
                                    String pass = value.getModel().get(0).getPwd();
                                    String passWordL = password.toLowerCase();
                                    String passL = pass.toLowerCase();
                                    if (TextUtils.equals(passL, passWordL)) {
                                        KLog.i("login----------success");
                                    } else {
                                        reLogin(context, view);
                                        KLog.i("login----------error");
                                    }
                                }
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                responeThrowable.printStackTrace();
                                KLog.i(responeThrowable.toString());
                            }
                        });
            } else {
                StaffInfoReq accessToken = new StaffInfoReq();
                accessToken.setLoginName(user);
                BaseInfoService.getInstance(context)
                        .getStaffInfo(accessToken)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<StaffInfoResp>(context) {
                            @Override
                            protected void doOnNext(StaffInfoResp value) {
                                if (value.getTotalCount() > 0) {
                                    String pass = value.getModel().get(0).getPassword();
                                    String passWordL = password.toLowerCase();
                                    String passL = pass.toLowerCase();
                                    if (TextUtils.equals(passL, passWordL)) {
                                        KLog.i("login----------success");
                                    } else {
                                        reLogin(context, view);
                                        KLog.i("login----------error");
                                    }
                                }
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                            }
                        });
            }

        }
    }

    private void reLogin(Context activity, MainhomeContract.View view) {
        Toast.makeText(activity, "密码已更改,登录失效,请重新登录.", Toast.LENGTH_LONG).show();
        view.jumpLoginPage();
        CommonShare.clear(context);
        JPushInterface.cleanTags(context, 12);
        ConstantUtils.SYS_NO = "";
        ConstantUtils.HIGHER_SYS_NO = "";
        ConstantUtils.CUSTOMER = "";
        ConstantUtils.CUSTOMERS_TYPE = "";
        ConstantUtils.NEW_TYPE = "-1";
        ConstantUtils.STAFF_TYPE = "";
        ConstantUtils.HIGHER_NAME = "";
        ConstantUtils.IS_ATFER_LOGIN_INIT = false;
        ConstantUtils.INIT_ALLOCATE = false;
        ConstantUtils.NEW_TYPE = "-1";
        ConstantUtils.initAllocate();
        view.finishAct();
    }


    private void getVersionCode() {
        String version = "";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = null;
            info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        getServiceVersionCode(version);
    }

    private void getServiceVersionCode(final String code) {

        VersionCodeReq accessToken = new VersionCodeReq();
        //Done switch version
        if (DycLibIntent.hasModule()){
            accessToken.setSystemName(context.getString(R.string.dversion_way_name));
        }else {
            accessToken.setSystemName(context.getString(R.string.version_way_name));
        }
        accessToken.setType(context.getString(R.string.version_type));
        HomeREService.getInstance(context)
                .getVersionCode(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<VersionCodeResp>>(context) {
                    @Override
                    protected void doOnNext(List<VersionCodeResp> value) {
                        String version = value.get(0).getVersion();
                        if (machCode(code, version)) {
                            showNormalDialog();
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }

    private boolean machCode(String code, String version) {
        KLog.i(code);
        KLog.i(version);
        String[] codes = code.split("\\.");
        String[] versions = version.split("\\.");
        boolean res = false;
        for (int i = 0; i < codes.length; i++) {
            KLog.i(Integer.parseInt(codes[i]) < Integer.parseInt(versions[i]));
            if (Integer.parseInt(codes[i]) < Integer.parseInt(versions[i])) {
                res = true;
            } else if (Integer.parseInt(codes[i]) > Integer.parseInt(versions[i])) {
                return false;
            }
        }
        KLog.i(res);
        return res;
    }

    private void showNormalDialog() {
        boolean b = CommonShare.getVersionCode(context);
        KLog.i(b);
        if (!b) {
            final AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(context);
            normalDialog.setIcon(R.mipmap.ic_launcher);
            normalDialog.setTitle("发现新版本");
            normalDialog.setMessage("发现新版本,请到应用商店或官网更新,避免影响正常使用.");
            normalDialog.setPositiveButton("知 道 了",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            normalDialog.setNegativeButton("不再提醒",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CommonShare.putVersionCode(context, true);
                        }
                    });
            normalDialog.show();
        }
    }

    private void initAllocateData() {
        view.showDialog();
        if (TextUtils.isEmpty(ConstantUtils.SYS_NO)) {
            Toast.makeText(context, "获取信息失败,请重新登录.", Toast.LENGTH_SHORT).show();
            view.jumpLoginPage();
        } else {
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
                                String customer_type = ConstantUtils.CUSTOMERS_TYPE;
                                if (customer_type != null && !customer_type.equals("")) {
                                    merchantModel.initTabList();
                                    merchantModel.initViewList();
                                } else {
                                    staffModel.initTabList();
                                    staffModel.initViewList();
                                }
                                setAdapter();
                                ConstantUtils.INIT_ALLOCATE = true;
                                view.dismissDialog();
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                try {
                                    Toast.makeText(context, "权限获取失败,请重新登录", Toast.LENGTH_SHORT).show();
                                    initAllocateError();
                                    view.dismissDialog();
                                } catch (Exception e) {
                                    KLog.i(e);
                                }
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
                                String customer_type = ConstantUtils.CUSTOMERS_TYPE;
                                if (customer_type != null && !customer_type.equals("")) {
                                    merchantModel.initTabList();
                                    merchantModel.initViewList();
                                } else {
                                    staffModel.initTabList();
                                    staffModel.initViewList();
                                }
                                setAdapter();
                                initAllocateError();
                                view.dismissDialog();
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                try {
                                    Toast.makeText(context, "权限获取失败,请重新登录", Toast.LENGTH_SHORT).show();
                                    view.dismissDialog();
                                    initAllocateError();
                                } catch (Exception e) {
                                    KLog.i(e);
                                }


                            }
                        });
            }
        }
    }

    private void initAllocateError() {
        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        if (customer_type != null && !customer_type.equals("")) {
            merchantModel.initTabList();
            merchantModel.initViewList();
        } else {
            staffModel.initTabList();
            staffModel.initViewList();
        }
        setAdapter();
    }

    private void saveAllocate(ArrayList tl) {
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

    private void setAdapter() {
        if (staffModel != null && staffModel.loadInstance().getTab_list().size() != 0 && staffModel.loadInstance().getTab_res().length != 0 && staffModel.loadInstance().getView_list().size() != 0) {
            view.setAdapter(staffModel.loadInstance().getTab_list(), staffModel.loadInstance().getTab_res(), staffModel.loadInstance().getView_list());
        }
        if (merchantModel != null && merchantModel.loadInstance().getTab_list().size() != 0 && merchantModel.loadInstance().getTab_res().length != 0 && merchantModel.loadInstance().getView_list().size() != 0) {
            view.setAdapter(merchantModel.loadInstance().getTab_list(), merchantModel.loadInstance().getTab_res(), merchantModel.loadInstance().getView_list());
        }
    }

    private void checkCarmerPermission() {
        KLog.i("check");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                view.showAlert();
            }
        }
    }

    private void initPayConfig(String higherSysNo) {
        String type = CommonShare.getTypeData(context);
        if (TextUtils.equals("1", type) || TextUtils.equals("3", type)) {
            PayConfigReq accessToken = new PayConfigReq();
            accessToken.setCustomerSysNo(higherSysNo);
            BaseInfoService.getInstance(context)
                    .getPayConfig(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new BaseObserver<List<PayConfigResp>>(context) {
                        @Override
                        protected void doOnNext(List<PayConfigResp> value) {
                            if (value.size() != 0) {
                                for (int i = 0; i < value.size(); i++) {
                                    String remarks = value.get(i).getRemarks();
                                    if (TextUtils.equals(remarks, "WX")) {
                                        ConstantUtils.GETED_WX_TYPE = value.get(i).getType() + "";
                                    } else if (TextUtils.equals(remarks, "AliPay")) {
                                        ConstantUtils.GETED_ZFB_TYPE = value.get(i).getType() + "";
                                    }
                                    CommonShare.putWxType(context, ConstantUtils.GETED_WX_TYPE);
                                    CommonShare.putZfbType(context, ConstantUtils.GETED_ZFB_TYPE);
                                }
                            } else {
                                ConstantUtils.GETED_WX_TYPE = CommonShare.getWxType(context);
                                ConstantUtils.GETED_ZFB_TYPE = CommonShare.getZfbType(context);
                            }
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                            ConstantUtils.GETED_WX_TYPE = CommonShare.getWxType(context);
                            ConstantUtils.GETED_ZFB_TYPE = CommonShare.getZfbType(context);
                        }
                    });
        }
    }

}
