
package com.yang.yunwang.query.api.bean.wxplant.netbank;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WxNetTranIDReq {

    @SerializedName("Out_trade_no")
    private String mOutTradeNo;
    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

    public String getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(String systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
    }

}
