
package com.yang.yunwang.query.api.bean.commonsearch;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Model implements Serializable {

    @SerializedName("Bank_type")
    private String mBankType;
    @SerializedName("Cash_fee")
    private Long mCashFee;
    @SerializedName("Cash_fee_type")
    private String mCashFeeType;
    @SerializedName("Count")
    private Long mCount;
    @SerializedName("Customer")
    private String mCustomer;
    @SerializedName("CustomerName")
    private String mCustomerName;
    @SerializedName("CustomerSysNo")
    private Long mCustomerSysNo;
    @SerializedName("DisplayName")
    private String mDisplayName;
    @SerializedName("fee")
    private Long mFee;
    @SerializedName("LoginName")
    private String mLoginName;
    @SerializedName("Old_SysNo")
    private Long mOldSysNo;
    @SerializedName("Out_trade_no")
    private String mOutTradeNo;
    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("Rate")
    private Double mRate;
    @SerializedName("Rate_fee")
    private Long mRateFee;
    @SerializedName("refund_fee")
    private Long mRefundFee;
    @SerializedName("SysNo")
    private Long mSysNo;
    @SerializedName("Time_end")
    private String mTimeEnd;
    @SerializedName("Time_Start")
    private String mTimeStart;
    @SerializedName("Total_fee")
    private Long mTotalFee;
    @SerializedName("Total_Rate_fee")
    private Long mTotalRateFee;
    @SerializedName("Tradecount")
    private String mTradecount;
    @SerializedName("UserRate")
    private Double mUserRate;
    @SerializedName("UserRate_fee")
    private Long mUserRateFee;

    public String getBankType() {
        return mBankType;
    }

    public void setBankType(String bankType) {
        mBankType = bankType;
    }

    public Long getCashFee() {
        return mCashFee;
    }

    public void setCashFee(Long cashFee) {
        mCashFee = cashFee;
    }

    public String getCashFeeType() {
        return mCashFeeType;
    }

    public void setCashFeeType(String cashFeeType) {
        mCashFeeType = cashFeeType;
    }

    public Long getCount() {
        return mCount;
    }

    public void setCount(Long count) {
        mCount = count;
    }

    public String getCustomer() {
        return mCustomer;
    }

    public void setCustomer(String customer) {
        mCustomer = customer;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String customerName) {
        mCustomerName = customerName;
    }

    public Long getCustomerSysNo() {
        return mCustomerSysNo;
    }

    public void setCustomerSysNo(Long customerSysNo) {
        mCustomerSysNo = customerSysNo;
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

    public Long getOldSysNo() {
        return mOldSysNo;
    }

    public void setOldSysNo(Long oldSysNo) {
        mOldSysNo = oldSysNo;
    }

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

    public Double getRate() {
        return mRate;
    }

    public void setRate(Double rate) {
        mRate = rate;
    }

    public Long getRateFee() {
        return mRateFee;
    }

    public void setRateFee(Long rateFee) {
        mRateFee = rateFee;
    }

    public Long getRefundFee() {
        return mRefundFee;
    }

    public void setRefundFee(Long refundFee) {
        mRefundFee = refundFee;
    }

    public Long getSysNo() {
        return mSysNo;
    }

    public void setSysNo(Long sysNo) {
        mSysNo = sysNo;
    }

    public String getTimeEnd() {
        return mTimeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        mTimeEnd = timeEnd;
    }

    public String getTimeStart() {
        return mTimeStart;
    }

    public void setTimeStart(String timeStart) {
        mTimeStart = timeStart;
    }

    public Long getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(Long totalFee) {
        mTotalFee = totalFee;
    }

    public Long getTotalRateFee() {
        return mTotalRateFee;
    }

    public void setTotalRateFee(Long totalRateFee) {
        mTotalRateFee = totalRateFee;
    }

    public String getTradecount() {
        return mTradecount;
    }

    public void setTradecount(String tradecount) {
        mTradecount = tradecount;
    }

    public Double getUserRate() {
        return mUserRate;
    }

    public void setUserRate(Double userRate) {
        mUserRate = userRate;
    }

    public Long getUserRateFee() {
        return mUserRateFee;
    }

    public void setUserRateFee(Long userRateFee) {
        mUserRateFee = userRateFee;
    }

    @Override
    public String toString() {
        return "Model{" +
                "mBankType='" + mBankType + '\'' +
                ", mCashFee=" + mCashFee +
                ", mCashFeeType='" + mCashFeeType + '\'' +
                ", mCount=" + mCount +
                ", mCustomer='" + mCustomer + '\'' +
                ", mCustomerName='" + mCustomerName + '\'' +
                ", mCustomerSysNo=" + mCustomerSysNo +
                ", mDisplayName='" + mDisplayName + '\'' +
                ", mFee=" + mFee +
                ", mLoginName='" + mLoginName + '\'' +
                ", mOldSysNo=" + mOldSysNo +
                ", mOutTradeNo='" + mOutTradeNo + '\'' +
                ", mPayType='" + mPayType + '\'' +
                ", mRate=" + mRate +
                ", mRateFee=" + mRateFee +
                ", mRefundFee=" + mRefundFee +
                ", mSysNo=" + mSysNo +
                ", mTimeEnd='" + mTimeEnd + '\'' +
                ", mTimeStart='" + mTimeStart + '\'' +
                ", mTotalFee=" + mTotalFee +
                ", mTotalRateFee=" + mTotalRateFee +
                ", mTradecount='" + mTradecount + '\'' +
                ", mUserRate=" + mUserRate +
                ", mUserRateFee=" + mUserRateFee +
                '}';
    }
}
