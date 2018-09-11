
package com.yang.yunwang.pay.api.qr.closerorderlog;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ColseOrderLogReq {

    @SerializedName("Out_trade_no")
    private String mOutTradeNo;
    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("PaymentEnd")
    private String mPaymentEnd;
    @SerializedName("State")
    private String mState;
    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;
    @SerializedName("Total_fee")
    private String mTotalFee;

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

    public String getPayType() {
        return mPayType;
    }

    public void setPayType(String payType) {
        mPayType = payType;
    }

    public String getPaymentEnd() {
        return mPaymentEnd;
    }

    public void setPaymentEnd(String paymentEnd) {
        mPaymentEnd = paymentEnd;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

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
