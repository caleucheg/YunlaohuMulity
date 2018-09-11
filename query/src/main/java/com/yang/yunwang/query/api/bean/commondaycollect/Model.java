
package com.yang.yunwang.query.api.bean.commondaycollect;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Model {

    @SerializedName("Cash_fee")
    private Long mCashFee;
    @SerializedName("Cash_refund_fee")
    private Long mCashRefundFee;
    @SerializedName("count")
    private Long mCount;
    @SerializedName("DisplayName")
    private String mDisplayName;
    @SerializedName("Fee")
    private Long mFee;
    @SerializedName("LoginName")
    private String mLoginName;
    @SerializedName("Money")
    private Long mMoney;
    @SerializedName("OrderTime")
    private String mOrderTime;
    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("Refund_fee")
    private Long mRefundFee;
    @SerializedName("Total_fee")
    private Long mTotalFee;
    @SerializedName("TradeCount")
    private Long mTradeCount;

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

    public Long getCount() {
        return mCount;
    }

    public void setCount(Long count) {
        mCount = count;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public Long getFee() {
        return mFee;
    }

    public void setFee(Long fee) {
        mFee = fee;
    }

    public String getLoginName() {
        return mLoginName;
    }

    public void setLoginName(String loginName) {
        mLoginName = loginName;
    }

    public Long getMoney() {
        return mMoney;
    }

    public void setMoney(Long money) {
        mMoney = money;
    }

    public String getOrderTime() {
        return mOrderTime;
    }

    public void setOrderTime(String orderTime) {
        mOrderTime = orderTime;
    }

    public String getPayType() {
        return mPayType;
    }

    public void setPayType(String payType) {
        mPayType = payType;
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

    public Long getTradeCount() {
        return mTradeCount;
    }

    public void setTradeCount(Long tradeCount) {
        mTradeCount = tradeCount;
    }

    @Override
    public String toString() {
        return "Model{" +
                "mCashFee=" + mCashFee +
                ", mCashRefundFee=" + mCashRefundFee +
                ", mCount=" + mCount +
                ", mDisplayName='" + mDisplayName + '\'' +
                ", mFee=" + mFee +
                ", mLoginName='" + mLoginName + '\'' +
                ", mMoney=" + mMoney +
                ", mOrderTime='" + mOrderTime + '\'' +
                ", mPayType='" + mPayType + '\'' +
                ", mRefundFee=" + mRefundFee +
                ", mTotalFee=" + mTotalFee +
                ", mTradeCount=" + mTradeCount +
                '}';
    }
}
