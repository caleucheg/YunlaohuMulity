package com.yang.yunwang.base.moduleinterface.module.module2;

import com.yang.yunwang.base.moduleinterface.provider.IPayProvider;
import com.yang.yunwang.base.moduleinterface.router.ModuleManager;

/**
 *  on 2017/4/13.
 *
 * description：
 * update by:
 * update day:
 */
public class Module2Service {
    private static boolean hasModule2() {
        return ModuleManager.getInstance().hasModule(IPayProvider.MODULE2_MAIN_SERVICE);
    }

//    public static Fragment getModule2Fragment(Object... args) {
//        if (!hasModule2()) return null;
//        return ServiceManager.getInstance().getModule2Provider().newInstance(args);
//    }
}
