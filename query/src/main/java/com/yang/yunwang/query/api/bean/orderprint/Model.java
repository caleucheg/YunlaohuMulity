
package com.yang.yunwang.query.api.bean.orderprint;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Model {

    @SerializedName("Cash_fee")
    private Long mCashFee;
    @SerializedName("Count")
    private Long mCount;
    @SerializedName("CustomerName")
    private Object mCustomerName;
    @SerializedName("DisplayName")
    private Object mDisplayName;
    @SerializedName("fee")
    private Long mFee;
    @SerializedName("LoginName")
    private Object mLoginName;
    @SerializedName("Old_SysNo")
    private String mOldSysNo;
    @SerializedName("Out_trade_no")
    private String mOutTradeNo;
    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("refundCount")
    private Long mRefundCount;
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
    @SerializedName("Transaction_id")
    private String mTransactionId;

    public Long getCashFee() {
        return mCashFee;
    }

    public void setCashFee(Long cashFee) {
        mCashFee = cashFee;
    }

    public Long getCount() {
        return mCount;
    }

    public void setCount(Long count) {
        mCount = count;
    }

    public Object getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(Object customerName) {
        mCustomerName = customerName;
    }

    public Object getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(Object displayName) {
        mDisplayName = displayName;
    }

    public Long getFee() {
        return mFee;
    }

    public void setFee(Long fee) {
        mFee = fee;
    }

    public Object getLoginName() {
        return mLoginName;
    }

    public void setLoginName(Object loginName) {
        mLoginName = loginName;
    }

    public String getOldSysNo() {
        return mOldSysNo;
    }

    public void setOldSysNo(String oldSysNo) {
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

    public String getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(String transactionId) {
        mTransactionId = transactionId;
    }

}
