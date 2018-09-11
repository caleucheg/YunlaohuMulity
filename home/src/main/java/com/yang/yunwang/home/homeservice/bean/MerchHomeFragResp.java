
package com.yang.yunwang.home.homeservice.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MerchHomeFragResp {

    @SerializedName("Cash_fee")
    private Long mCashFee;
    @SerializedName("cash_refund_fee")
    private Long mCashRefundFee;
    @SerializedName("refund_fee")
    private Long mRefundFee;
    @SerializedName("Total_fee")
    private Long mTotalFee;
    @SerializedName("Tradecount")
    private int mTradecount;

    public Long getCashFee() {
        return mCashFee;
    }

    public void setCashFee(Long cashFee) {
        mCashFee = cashFee;
    }

    public Long getCashRefundFee() {
        return mCashRefundFee;
    }

    public void setCashRefundFee(Long cashRefundFee) {
        mCashRefundFee = cashRefundFee;
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

    public int getTradecount() {
        return mTradecount;
    }

    public void setTradecount(int tradecount) {
        mTradecount = tradecount;
    }

}
