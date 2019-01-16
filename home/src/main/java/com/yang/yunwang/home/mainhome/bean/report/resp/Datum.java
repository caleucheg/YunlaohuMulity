
package com.yang.yunwang.home.mainhome.bean.report.resp;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("Fee")
    private String mFee;
    @SerializedName("RMACount")
    private String mRMACount;
    @SerializedName("Refund_fee")
    private String mRefundFee;
    @SerializedName("Remarks")
    private String mRemarks;
    @SerializedName("Total_fee")
    private String mTotalFee;
    @SerializedName("TradeCount")
    private String mTradeCount;

    public String getFee() {
        return mFee;
    }

    public void setFee(String fee) {
        mFee = fee;
    }

    public String getRMACount() {
        return mRMACount;
    }

    public void setRMACount(String rMACount) {
        mRMACount = rMACount;
    }

    public String getRefundFee() {
        return mRefundFee;
    }

    public void setRefundFee(String refundFee) {
        mRefundFee = refundFee;
    }

    public String getRemarks() {
        return mRemarks;
    }

    public void setRemarks(String remarks) {
        mRemarks = remarks;
    }

    public String getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(String totalFee) {
        mTotalFee = totalFee;
    }

    public String getTradeCount() {
        return mTradeCount;
    }

    public void setTradeCount(String tradeCount) {
        mTradeCount = tradeCount;
    }

}
