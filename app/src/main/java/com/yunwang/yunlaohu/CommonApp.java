package com.yunwang.yunlaohu;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yang.yunwang.base.BaseApplication;
import com.yang.yunwang.base.moduleinterface.config.ModuleOptions;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.moduleinterface.provider.IPayProvider;
import com.yang.yunwang.base.moduleinterface.router.ModuleManager;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.LG;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/6/8.
 */

public class CommonApp extends BaseApplication {

    @Override
    public void jumpLogin() {

    }

    @Override
    public void initRouter() {
        initARouter();
    }

    private void initARouter() {
        if (LG.isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
            ARouter.printStackTrace();
        }
        ARouter.init(this);

        ModuleOptions.ModuleBuilder builder = new ModuleOptions.ModuleBuilder(this)
                .addModule(IHomeProvider.HOME_MAIN_SERVICE, IHomeProvider.HOME_MAIN_SERVICE)
                .addModule(IPayProvider.MODULE2_MAIN_SERVICE, IPayProvider.MODULE2_MAIN_SERVICE)
//                .addModule(IDycLibProvider.MODULE3_MAIN_SERVICE, IDycLibProvider.MODULE3_MAIN_SERVICE)
                .addModule(IOrdersProvider.ORDRES_MAIN_SERVICE, IOrdersProvider.ORDRES_MAIN_SERVICE);


        ModuleManager.getInstance().init(builder.build());
        //TODO switch version
        initNotifi();
    }

    private void initNotifi() {
        String newType = CommonShare.getTypeData(this);
        if (TextUtils.equals("3", newType)) {
            Log.i("baseApp", "1111");
            JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
            JPushInterface.init(this);            // 初始化 JPush
        }
    }
}
