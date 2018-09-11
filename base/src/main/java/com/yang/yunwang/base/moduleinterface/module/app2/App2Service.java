package com.yang.yunwang.base.moduleinterface.module.app2;

import com.yang.yunwang.base.moduleinterface.provider.IAppProvider;
import com.yang.yunwang.base.moduleinterface.router.ModuleManager;

/**
 *  on 2017/4/13.
 *
 * description：
 * update by:
 * update day:
 */
public class App2Service {
    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IAppProvider.APP_MAIN_SERVICE);
    }

}
