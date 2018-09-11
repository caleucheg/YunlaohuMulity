package com.yang.yunwang.base.moduleinterface.module.module5;

import com.yang.yunwang.base.moduleinterface.provider.IModule5Provider;
import com.yang.yunwang.base.moduleinterface.router.ModuleManager;

/**
 *  on 2017/4/13.
 *
 * description：
 * update by:
 * update day:
 */
public class Module5Service {
    private boolean hasModule1() {
        return ModuleManager.getInstance().hasModule(IModule5Provider.MODULE5_MAIN_SERVICE);
    }
}
