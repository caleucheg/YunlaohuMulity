package com.yang.yunwang.base.moduleinterface.provider;

/**
 *  on 2017/4/16.
 *
 * description：
 * update by:
 * update day:
 */
public interface IPayProvider extends IBaseProvider{
    //服务
    String MODULE2_MAIN_SERVICE = "/module2/main/service";
    //作为Fragment被添加时候的key
    String MODULE2_KEY_FRAGMENT = "module2_key_fragment";
    String PAY_ACT_SCAN_CODE = "/pay/act/scan/code";
    String PAY_ACT_SCAN_RESULT = "/pay/act/scan/result";
    String PAY_ACT_QR_CODE = "/pay/act/qr/code";
}
