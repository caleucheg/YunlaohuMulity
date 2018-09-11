package com.yang.yunwang.base.moduleinterface.module.module2;

import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.provider.IPayProvider;
import com.yang.yunwang.base.moduleinterface.router.ModuleManager;
import com.yang.yunwang.base.moduleinterface.router.MyRouter;

/**
 *  on 2017/4/13.
 *
 * description：
 * update by:
 * update day:
 */
public class PayIntent {
    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IPayProvider.MODULE2_MAIN_SERVICE);
    }
    public static void scanCode(MyBundle myBundle){
        MyRouter.newInstance(IPayProvider.PAY_ACT_SCAN_CODE)
                .withBundle(myBundle)
                .navigation();
    }

    public static void scanResult(MyBundle myBundle){
        MyRouter.newInstance(IPayProvider.PAY_ACT_SCAN_RESULT)
                .withBundle(myBundle)
                .navigation();
    }

    public static void qrCode(MyBundle myBundle){
        MyRouter.newInstance(IPayProvider.PAY_ACT_QR_CODE)
                .withBundle(myBundle)
                .navigation();
    }
}
