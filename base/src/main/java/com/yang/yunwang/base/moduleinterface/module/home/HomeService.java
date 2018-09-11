package com.yang.yunwang.base.moduleinterface.module.home;

import android.app.Activity;

import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.moduleinterface.router.ModuleManager;
import com.yang.yunwang.base.moduleinterface.router.ServiceManager;

/**
 *  on 2017/4/16.
 *
 * description：
 * update by:
 * update day:
 */
public class HomeService {

    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IHomeProvider.HOME_MAIN_SERVICE);
    }

    public static void selectedTab(Activity activity, int position) {
        if (!hasModule()) return;
        ServiceManager.getInstance().getHomeProvider().selectedTab(activity, position);
    }

}
