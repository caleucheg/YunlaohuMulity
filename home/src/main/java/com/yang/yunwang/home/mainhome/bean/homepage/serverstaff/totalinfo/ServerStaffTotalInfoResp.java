
package com.yang.yunwang.home.mainhome.bean.homepage.serverstaff.totalinfo;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ServerStaffTotalInfoResp {

    @SerializedName("Cash_fee")
    private Long mCashFee;
    @SerializedName("Refund_count")
    private Long mRefundCount;
    @SerializedName("Refund_fee")
    private Long mRefundFee;
    @SerializedName("Total_fee")
    private Long mTotalFee;
    @SerializedName("Tradecount")
    private Long mTradecount;

    public Long getCashFee() {
        return mCashFee;
    }

    public void setCashFee(Long cashFee) {
        mCashFee = cashFee;
    }

    public Long getRefundCount() {
        return mRefundCount;
    }

    public void setRefundCount(Long refundCount) {
        mRefundCount = refundCount;
    }

    public Long getRefundFee() {
        return mRefundFee;
    }

    public void setRefundFee(Long refundFee) {
        mRefundFee = refundFee;
    }

    public Long getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(Long totalFee) {
        mTotalFee = totalFee;
    }

    public Long getTradecount() {
        return mTradecount;
    }

    public void setTradecount(Long tradecount) {
        mTradecount = tradecount;
    }

}
