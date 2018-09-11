
package com.yang.yunwang.query.api.bean.susersettle.list;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Model {

    @SerializedName("Cash_fee")
    private Long mCashFee;
    @SerializedName("count")
    private Long mCount;
    @SerializedName("DisplayName")
    private String mDisplayName;
    @SerializedName("Fee")
    private Long mFee;
    @SerializedName("LoginName")
    private String mLoginName;
    @SerializedName("PhoneNumber")
    private String mPhoneNumber;
    @SerializedName("refund_fee")
    private Long mRefundFee;
    @SerializedName("SystemUserTopSysNo")
    private Long mSystemUserTopSysNo;
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

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public Long getRefundFee() {
        return mRefundFee;
    }

    public void setRefundFee(Long refundFee) {
        mRefundFee = refundFee;
    }

    public Long getSystemUserTopSysNo() {
        return mSystemUserTopSysNo;
    }

    public void setSystemUserTopSysNo(Long systemUserTopSysNo) {
        mSystemUserTopSysNo = systemUserTopSysNo;
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
