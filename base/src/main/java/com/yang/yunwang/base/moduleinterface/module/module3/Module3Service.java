package com.yang.yunwang.base.moduleinterface.module.module3;

import com.yang.yunwang.base.moduleinterface.provider.IDycLibProvider;
import com.yang.yunwang.base.moduleinterface.router.ModuleManager;

/**
 *  on 2017/4/13.
 *
 * description：
 * update by:
 * update day:
 */
public class Module3Service {

    private static boolean hasModule3() {
        return ModuleManager.getInstance().hasModule(IDycLibProvider.MODULE3_MAIN_SERVICE);
    }

//    public static Fragment getModule2Fragment(Object... args) {
//        if (!hasModule3()) return null;
//        return ServiceManager.getInstance().getModule3Provider().newInstance(args);
//    }
}
