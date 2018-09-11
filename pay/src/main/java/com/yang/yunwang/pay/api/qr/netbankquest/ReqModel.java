
package com.yang.yunwang.pay.api.qr.netbankquest;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ReqModel {

    @SerializedName("OutTradeNo")
    private String mOutTradeNo;

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

}
