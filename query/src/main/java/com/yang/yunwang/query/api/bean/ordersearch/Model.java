
package com.yang.yunwang.query.api.bean.ordersearch;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Model implements Serializable{

    @SerializedName("Appid")
    private Object mAppid;
    @SerializedName("Attach")
    private Object mAttach;
    @SerializedName("Bank_type")
    private String mBankType;
    @SerializedName("Cash_fee")
    private Long mCashFee;
    @SerializedName("Cash_fee_type")
    private Object mCashFeeType;
    @SerializedName("Count")
    private Long mCount;
    @SerializedName("CustomerName")
    private String mCustomerName;
    @SerializedName("CustomerSysNo")
    private Long mCustomerSysNo;
    @SerializedName("CustomersTopSysNo")
    private Long mCustomersTopSysNo;
    @SerializedName("DisplayName")
    private String mDisplayName;
    @SerializedName("fee")
    private Long mFee;
    @SerializedName("Is_subscribe")
    private Object mIsSubscribe;
    @SerializedName("LoginName")
    private String mLoginName;
    @SerializedName("Mch_id")
    private Object mMchId;
    @SerializedName("Nonce_str")
    private Object mNonceStr;
    @SerializedName("Old_SysNo")
    private Long mOldSysNo;
    @SerializedName("Openid")
    private Object mOpenid;
    @SerializedName("Out_trade_no")
    private String mOutTradeNo;
    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("Rate")
    private Long mRate;
    @SerializedName("Rate_fee")
    private Long mRateFee;
    @SerializedName("refundCount")
    private Long mRefundCount;
    @SerializedName("refund_fee")
    private Long mRefundFee;
    @SerializedName("Status")
    private Long mStatus;
    @SerializedName("Sub_mch_id")
    private Object mSubMchId;
    @SerializedName("SysNo")
    private Long mSysNo;
    @SerializedName("SystemUserTopSysNo")
    private Long mSystemUserTopSysNo;
    @SerializedName("Time_end")
    private String mTimeEnd;
    @SerializedName("Time_Start")
    private String mTimeStart;
    @SerializedName("Total_fee")
    private Long mTotalFee;
    @SerializedName("Trade_type")
    private Object mTradeType;
    @SerializedName("Tradecount")
    private Object mTradecount;
    @SerializedName("Transaction_id")
    private Object mTransactionId;
    @SerializedName("UserRate")
    private Long mUserRate;
    @SerializedName("UserRate_fee")
    private Long mUserRateFee;
    @SerializedName("YwMch_id")
    private String mYwMchId;

    public Object getAppid() {
        return mAppid;
    }

    public void setAppid(Object appid) {
        mAppid = appid;
    }

    public Object getAttach() {
        return mAttach;
    }

    public void setAttach(Object attach) {
        mAttach = attach;
    }

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

    public Object getCashFeeType() {
        return mCashFeeType;
    }

    public void setCashFeeType(Object cashFeeType) {
        mCashFeeType = cashFeeType;
    }

    public Long getCount() {
        return mCount;
    }

    public void setCount(Long count) {
        mCount = count;
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

    public Long getCustomersTopSysNo() {
        return mCustomersTopSysNo;
    }

    public void setCustomersTopSysNo(Long customersTopSysNo) {
        mCustomersTopSysNo = customersTopSysNo;
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

    public Object getIsSubscribe() {
        return mIsSubscribe;
    }

    public void setIsSubscribe(Object isSubscribe) {
        mIsSubscribe = isSubscribe;
    }

    public String getLoginName() {
        return mLoginName;
    }

    public void setLoginName(String loginName) {
        mLoginName = loginName;
    }

    public Object getMchId() {
        return mMchId;
    }

    public void setMchId(Object mchId) {
        mMchId = mchId;
    }

    public Object getNonceStr() {
        return mNonceStr;
    }

    public void setNonceStr(Object nonceStr) {
        mNonceStr = nonceStr;
    }

    public Long getOldSysNo() {
        return mOldSysNo;
    }

    public void setOldSysNo(Long oldSysNo) {
        mOldSysNo = oldSysNo;
    }

    public Object getOpenid() {
        return mOpenid;
    }

    public void setOpenid(Object openid) {
        mOpenid = openid;
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

    public Long getRate() {
        return mRate;
    }

    public void setRate(Long rate) {
        mRate = rate;
    }

    public Long getRateFee() {
        return mRateFee;
    }

    public void setRateFee(Long rateFee) {
        mRateFee = rateFee;
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

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public Object getSubMchId() {
        return mSubMchId;
    }

    public void setSubMchId(Object subMchId) {
        mSubMchId = subMchId;
    }

    public Long getSysNo() {
        return mSysNo;
    }

    public void setSysNo(Long sysNo) {
        mSysNo = sysNo;
    }

    public Long getSystemUserTopSysNo() {
        return mSystemUserTopSysNo;
    }

    public void setSystemUserTopSysNo(Long systemUserTopSysNo) {
        mSystemUserTopSysNo = systemUserTopSysNo;
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

    public Object getTradeType() {
        return mTradeType;
    }

    public void setTradeType(Object tradeType) {
        mTradeType = tradeType;
    }

    public Object getTradecount() {
        return mTradecount;
    }

    public void setTradecount(Object tradecount) {
        mTradecount = tradecount;
    }

    public Object getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(Object transactionId) {
        mTransactionId = transactionId;
    }

    public Long getUserRate() {
        return mUserRate;
    }

    public void setUserRate(Long userRate) {
        mUserRate = userRate;
    }

    public Long getUserRateFee() {
        return mUserRateFee;
    }

    public void setUserRateFee(Long userRateFee) {
        mUserRateFee = userRateFee;
    }

    public String getYwMchId() {
        return mYwMchId;
    }

    public void setYwMchId(String ywMchId) {
        mYwMchId = ywMchId;
    }

    @Override
    public String toString() {
        return "Model{" +
                "mAppid=" + mAppid +
                ", mAttach=" + mAttach +
                ", mBankType='" + mBankType + '\'' +
                ", mCashFee=" + mCashFee +
                ", mCashFeeType=" + mCashFeeType +
                ", mCount=" + mCount +
                ", mCustomerName='" + mCustomerName + '\'' +
                ", mCustomerSysNo=" + mCustomerSysNo +
                ", mCustomersTopSysNo=" + mCustomersTopSysNo +
                ", mDisplayName='" + mDisplayName + '\'' +
                ", mFee=" + mFee +
                ", mIsSubscribe=" + mIsSubscribe +
                ", mLoginName='" + mLoginName + '\'' +
                ", mMchId=" + mMchId +
                ", mNonceStr=" + mNonceStr +
                ", mOldSysNo=" + mOldSysNo +
                ", mOpenid=" + mOpenid +
                ", mOutTradeNo='" + mOutTradeNo + '\'' +
                ", mPayType='" + mPayType + '\'' +
                ", mRate=" + mRate +
                ", mRateFee=" + mRateFee +
                ", mRefundCount=" + mRefundCount +
                ", mRefundFee=" + mRefundFee +
                ", mStatus=" + mStatus +
                ", mSubMchId=" + mSubMchId +
                ", mSysNo=" + mSysNo +
                ", mSystemUserTopSysNo=" + mSystemUserTopSysNo +
                ", mTimeEnd='" + mTimeEnd + '\'' +
                ", mTimeStart='" + mTimeStart + '\'' +
                ", mTotalFee=" + mTotalFee +
                ", mTradeType=" + mTradeType +
                ", mTradecount=" + mTradecount +
                ", mTransactionId=" + mTransactionId +
                ", mUserRate=" + mUserRate +
                ", mUserRateFee=" + mUserRateFee +
                ", mYwMchId='" + mYwMchId + '\'' +
                '}';
    }
}
