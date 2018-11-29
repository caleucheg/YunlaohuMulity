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
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

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
        configUnits();
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

    private void configUnits() {
        //AndroidAutoSize 默认开启对 dp 的支持, 调用 UnitsManager.setSupportDP(false); 可以关闭对 dp 的支持
        //主单位 dp 和 副单位可以同时开启的原因是, 对于旧项目中已经使用了 dp 进行布局的页面的兼容
        //让开发者的旧项目可以渐进式的从 dp 切换到副单位, 即新页面用副单位进行布局, 然后抽时间逐渐的将旧页面的布局单位从 dp 改为副单位
        //最后将 dp 全部改为副单位后, 再使用 UnitsManager.setSupportDP(false); 将 dp 的支持关闭, 彻底隔离修改 density 所造成的不良影响
        //如果项目完全使用副单位, 则可以直接以像素为单位填写 AndroidManifest 中需要填写的设计图尺寸, 不需再把像素转化为 dp
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                //当使用者想将旧项目从主单位过渡到副单位, 或从副单位过渡到主单位时
                //因为在使用主单位时, 建议在 AndroidManifest 中填写设计图的 dp 尺寸, 比如 360 * 640
                //而副单位有一个特性是可以直接在 AndroidManifest 中填写设计图的 px 尺寸, 比如 1080 * 1920
                //但在 AndroidManifest 中却只能填写一套设计图尺寸, 并且已经填写了主单位的设计图尺寸
                //所以当项目中同时存在副单位和主单位, 并且副单位的设计图尺寸与主单位的设计图尺寸不同时, 可以通过 UnitsManager#setDesignSize() 方法配置
                //如果副单位的设计图尺寸与主单位的设计图尺寸相同, 则不需要调用 UnitsManager#setDesignSize(), 框架会自动使用 AndroidManifest 中填写的设计图尺寸
//                .setDesignSize(2160, 3840)
                //AndroidAutoSize 默认开启对 sp 的支持, 调用 UnitsManager.setSupportSP(false); 可以关闭对 sp 的支持
                //如果关闭对 sp 的支持, 在布局时就应该使用副单位填写字体的尺寸
                //如果开启 sp, 对其他三方库控件影响不大, 也可以不关闭对 sp 的支持, 这里我就继续开启 sp, 请自行斟酌自己的项目是否需要关闭对 sp 的支持
                .setSupportSP(true)
                //AndroidAutoSize 默认不支持副单位, 调用 UnitsManager#setSupportSubunits() 可选择一个自己心仪的副单位, 并开启对副单位的支持
                //只能在 pt、in、mm 这三个冷门单位中选择一个作为副单位, 三个单位的适配效果其实都是一样的, 您觉的哪个单位看起顺眼就用哪个
                //您选择什么单位就在 layout 文件中用什么单位进行布局, 我选择用 mm 为单位进行布局, 因为 mm 翻译为中文是妹妹的意思
                //如果大家生活中没有妹妹, 那我们就让项目中最不缺的就是妹妹!
                .setSupportSubunits(Subunits.NONE);
    }
}
