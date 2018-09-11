package com.yang.yunwang.dyc;

import com.alibaba.android.arouter.launcher.ARouter;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseApplication;
import com.yang.yunwang.base.moduleinterface.config.ModuleOptions;
import com.yang.yunwang.base.moduleinterface.provider.IDycLibProvider;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.moduleinterface.provider.IPayProvider;
import com.yang.yunwang.base.moduleinterface.router.ModuleManager;
import com.yang.yunwang.base.util.LG;

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
        KLog.i("init");
        ModuleOptions.ModuleBuilder builder = new ModuleOptions.ModuleBuilder(this)
                .addModule(IHomeProvider.HOME_MAIN_SERVICE, IHomeProvider.HOME_MAIN_SERVICE)
                .addModule(IPayProvider.MODULE2_MAIN_SERVICE, IPayProvider.MODULE2_MAIN_SERVICE)
                .addModule(IDycLibProvider.MODULE3_MAIN_SERVICE, IDycLibProvider.MODULE3_MAIN_SERVICE)
                .addModule(IOrdersProvider.ORDRES_MAIN_SERVICE, IOrdersProvider.ORDRES_MAIN_SERVICE);
        ModuleManager.getInstance().init(builder.build());
    }
}
