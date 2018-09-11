
package com.yang.yunwang.pay.api.scan.netbank;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ReqModel {

    @SerializedName("AuthCode")
    private String mAuthCode;
    @SerializedName("ChannelType")
    private String mChannelType;
    @SerializedName("OutTradeNo")
    private String mOutTradeNo;
    @SerializedName("TotalAmount")
    private String mTotalAmount;

    public String getAuthCode() {
        return mAuthCode;
    }

    public void setAuthCode(String authCode) {
        mAuthCode = authCode;
    }

    public String getChannelType() {
        return mChannelType;
    }

    public void setChannelType(String channelType) {
        mChannelType = channelType;
    }

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

    public String getTotalAmount() {
        return mTotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        mTotalAmount = totalAmount;
    }

}
