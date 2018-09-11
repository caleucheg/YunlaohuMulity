package com.yang.yunwang.base.moduleinterface.module.module4;

import android.app.Activity;

import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.provider.IModule4Provider;
import com.yang.yunwang.base.moduleinterface.router.ModuleManager;
import com.yang.yunwang.base.moduleinterface.router.MyRouter;

import java.io.Serializable;

/**
 *  on 2017/4/13.
 *
 * description：
 * update by:
 * update day:
 */
public class Module4Intent {

    private static boolean hasModule4() {
        return ModuleManager.getInstance().hasModule(IModule4Provider.MODULE4_MAIN_SERVICE);
    }

    public static void launchModule4(Activity activity, int requestCode, Serializable data) {
        if (!hasModule4()) return;
        MyBundle bundle = new MyBundle();
        bundle.put("data", data);
        MyRouter.newInstance(IModule4Provider.MODULE4_ACT_MAIN)
                .withBundle(bundle)
                .navigation(activity, requestCode);
    }
}
