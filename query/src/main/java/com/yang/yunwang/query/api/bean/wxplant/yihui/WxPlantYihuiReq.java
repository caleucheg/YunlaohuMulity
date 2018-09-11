
package com.yang.yunwang.query.api.bean.wxplant.yihui;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WxPlantYihuiReq {

    @SerializedName("out_trade_no")
    private String mOutTradeNo;
    @SerializedName("systemUserSysNo")
    private String mSystemUserSysNo;

    public String getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(String mTransactionId) {
        this.mTransactionId = mTransactionId;
    }

    @SerializedName("transaction_id")
    private String mTransactionId;

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
