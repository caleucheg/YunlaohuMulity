
package com.yang.yunwang.query.api.bean.unrefund.netbank;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ReqModel {

    @SerializedName("OutTradeNo")
    private String mOutTradeNo;
    @SerializedName("RefundAmount")
    private String mRefundAmount;
    @SerializedName("Total_fee")
    private String mTotalFee;

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

    public String getRefundAmount() {
        return mRefundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        mRefundAmount = refundAmount;
    }

    public String getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(String totalFee) {
        mTotalFee = totalFee;
    }

}
