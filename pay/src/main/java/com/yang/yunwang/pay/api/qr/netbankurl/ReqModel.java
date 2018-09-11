
package com.yang.yunwang.pay.api.qr.netbankurl;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ReqModel {

    @SerializedName("ChannelType")
    private String mChannelType;
    @SerializedName("TotalAmount")
    private String mTotalAmount;

    public String getChannelType() {
        return mChannelType;
    }

    public void setChannelType(String channelType) {
        mChannelType = channelType;
    }

    public String getTotalAmount() {
        return mTotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        mTotalAmount = totalAmount;
    }

}
