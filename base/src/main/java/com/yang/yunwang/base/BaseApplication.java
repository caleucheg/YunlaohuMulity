package com.yang.yunwang.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.basereq.BaseInfoService;
import com.yang.yunwang.base.basereq.bean.merchinfo.MerchInfoReq;
import com.yang.yunwang.base.basereq.bean.merchinfo.MerchInfoResp;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigReq;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigResp;
import com.yang.yunwang.base.basereq.bean.staffinfo.StaffInfoReq;
import com.yang.yunwang.base.basereq.bean.staffinfo.StaffInfoResp;
import com.yang.yunwang.base.dao.PayLogBean;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.uncatchex.CrashHandler;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.InsertLogUtils;
import com.yang.yunwang.base.util.LG;
import com.yang.yunwang.base.util.PayLogQueu;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//import com.socks.library.KLog;

/**
 * on 2017/4/16.
 * <p>
 * description：
 * update by:
 * update day:
 */
public abstract class BaseApplication extends Application {
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo netInfo;
    private static BaseApplication mContext;
    //监听网络状态变化的广播接收器
    private BroadcastReceiver myNetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                netInfo = mConnectivityManager.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isAvailable()) {
                    //网络连接
                    int size = PayLogQueu.getInstance().size();
                    for (int i = 0; i < size; i++) {
                        Log.i("baseApp", size + "-in-for-insert_log_size");
                        PayLogBean bean = PayLogQueu.getInstance().poll();
                        if (bean != null) {
                            InsertLogUtils.reinsertLog(bean, getApplicationContext());
                        }
                    }
                    Log.i("baseApp", size + "--insert_log_size");
                } else {
                    //网络断开
                    Toast.makeText(context, "网络已断开,请重新连接", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    private boolean isAlive = true;
    private boolean isScreenLock;

    public static BaseApplication getApplication() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LG.isDebug = BuildConfig.DEBUG;
//        KLog.init(BuildConfig.DEBUG, "YUN_LOG");
        Log.i("baseApp", "onccc");
        this.mContext = this;
        initRouter();
        // 异常处理！
        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(getApplicationContext());
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myNetReceiver, mFilter);
//        KLog.init(false, "YUN_LOG");
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
        rALifeC();
//        initNotifi();
    }


    private void rALifeC() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (!isAlive && isScreenLock) {
                    if (TextUtils.isEmpty(ConstantUtils.SYS_NO)) {
                        checkStoreConfig(BaseApplication.this);
                    } else {
                        KLog.i(ConstantUtils.SYS_NO);
                    }
                    checkPassWord(activity);
                    if (!TextUtils.isEmpty(ConstantUtils.HIGHER_SYS_NO)) {
                        getPayConfig(ConstantUtils.HIGHER_SYS_NO);
                    }
                }
                isAlive = true;
                isScreenLock = true;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                PowerManager manager = (PowerManager) getSystemService(Context.POWER_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    isScreenLock = manager.isInteractive();
                } else {
                    isScreenLock = manager.isScreenOn();
                }

                isAlive = isAppOnForeground();
                Log.i("baseApp", isScreenLock + "---" + isAlive + "---");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void checkStoreConfig(Context context) {
        String user = CommonShare.getLoginData(context, "User", "");
        KLog.i(ConstantUtils.SYS_NO + "------" + CommonShare.getLoginData(context, "SysNo", ""));

        if (!user.equals("")) {
            ConstantUtils.USER = user;
            ConstantUtils.SYS_NO = CommonShare.getLoginData(context, "SysNo", "");
            ConstantUtils.CUSTOMER = CommonShare.getLoginData(context, "Customer", "");
            ConstantUtils.CUSTOMERS_TYPE = CommonShare.getLoginData(context, "CustomersType", "");
            ConstantUtils.NEW_TYPE = CommonShare.getTypeData(context);
        } else {
            HomeIntent.login();
        }
    }


    private void getPayConfig(String higherSysNo) {
        String type = CommonShare.getTypeData(this);
        if (TextUtils.equals("1", type) || TextUtils.equals("3", type)) {
            PayConfigReq accessToken = new PayConfigReq();
            accessToken.setCustomerSysNo(higherSysNo);
            BaseInfoService.getInstance(this)
                    .getPayConfig(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new BaseObserver<List<PayConfigResp>>(this) {
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

    private void checkPassWord(final Activity activity) {
        String position = CommonShare.getLoginData(this, "Position", "");
        String user = CommonShare.getLoginData(this, "User", "");
        final String password = CommonShare.getLoginData(this, "PassWord", "");
        if (!TextUtils.isEmpty(position)) {
            if (Integer.parseInt(position) == 0) {
                MerchInfoReq accessToken = new MerchInfoReq();
                accessToken.setCustomer(user);
                BaseInfoService.getInstance(this)
                        .getMerchInfo(accessToken)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<MerchInfoResp>(this) {
                            @Override
                            protected void doOnNext(MerchInfoResp value) {
                                if (value.getTotalCount() > 0) {
                                    String pass = value.getModel().get(0).getPwd();
                                    String passWordL = password.toLowerCase();
                                    String passL = pass.toLowerCase();
                                    if (TextUtils.equals(passL, passWordL)) {
                                        Log.i("baseApp", "login----------success");
                                    } else {
                                        reLogin(activity);
                                        Log.i("baseApp", "login----------error");
                                    }
                                }
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                responeThrowable.printStackTrace();
                                Log.i("baseApp", responeThrowable.toString());
                            }
                        });
            } else {
                StaffInfoReq accessToken = new StaffInfoReq();
                accessToken.setLoginName(user);
                BaseInfoService.getInstance(this)
                        .getStaffInfo(accessToken)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<StaffInfoResp>(this) {
                            @Override
                            protected void doOnNext(StaffInfoResp value) {
                                if (value.getTotalCount() > 0) {
                                    String pass = value.getModel().get(0).getPassword();
                                    String passWordL = password.toLowerCase();
                                    String passL = pass.toLowerCase();
                                    if (TextUtils.equals(passL, passWordL)) {
                                        Log.i("baseApp", "login----------success");
                                    } else {
                                        reLogin(activity);
                                        Log.i("baseApp", "login----------error");
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

    private void reLogin(Activity activity) {
        Toast.makeText(activity, "密码已更改,登录失效,请重新登录.", Toast.LENGTH_SHORT).show();
        jumpLogin();
        CommonShare.clear(this);
        JPushInterface.cleanTags(this, 12);
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
        activity.finish();
    }

    public abstract void jumpLogin();

    public abstract void initRouter();

    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
