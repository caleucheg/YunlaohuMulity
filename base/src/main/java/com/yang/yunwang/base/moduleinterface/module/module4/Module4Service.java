package com.yang.yunwang.base.moduleinterface.module.module4;

import com.yang.yunwang.base.moduleinterface.provider.IModule4Provider;
import com.yang.yunwang.base.moduleinterface.router.ModuleManager;

/**
 *  on 2017/4/13.
 *
 * description：
 * update by:
 * update day:
 */
public class Module4Service {
    private boolean hasModule1() {
        return ModuleManager.getInstance().hasModule(IModule4Provider.MODULE4_MAIN_SERVICE);
    }
}
