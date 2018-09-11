package com.yang.yunwang.base.moduleinterface.module.module1;

import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.moduleinterface.router.ModuleManager;

/**
 *  on 2017/4/13.
 *
 * description：
 * update by:
 * update day:
 */
public class Module1Service {
    private static boolean hasModule1() {
        return ModuleManager.getInstance().hasModule(IOrdersProvider.ORDRES_MAIN_SERVICE);
    }

//    public static Fragment getModule1Frgment(Object... args) {
//        if(!hasModule1()) return null;
//        return ServiceManager.getInstance().getModule1Provider().newInstance(args);
//    }
}
