
package com.yang.yunwang.pay.api.qr.commonurl;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CommonUrlReq {

    @SerializedName("systemUserSysNo")
    private String mSystemUserSysNo;
    @SerializedName("Total_fee")
    private String mTotalFee;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @SerializedName("Pay_Type")
    private String payType;
    @SerializedName("out_trade_no")
    private String outTradeNo;

    public String getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(String systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
    }

    public String getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(String totalFee) {
        mTotalFee = totalFee;
    }

}
