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
public class DycLibIntent {
    public static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IDycLibProvider.MODULE3_MAIN_SERVICE);
    }
}
