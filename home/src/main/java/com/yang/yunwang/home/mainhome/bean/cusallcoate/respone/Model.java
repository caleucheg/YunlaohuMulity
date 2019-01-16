
package com.yang.yunwang.home.mainhome.bean.cusallcoate.respone;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Model {

    @SerializedName("DataCount")
    private Long mDataCount;
    @SerializedName("DisplayName")
    private String mDisplayName;
    @SerializedName("Fee")
    private String mFee;
    @SerializedName("LoginName")
    private String mLoginName;
    @SerializedName("Refund_Count")
    private String mRefundCount;
    @SerializedName("Refund_fee")
    private String mRefundFee;
    @SerializedName("Total_Count")
    private String mTotalCount;
    @SerializedName("Total_fee")
    private String mTotalFee;

    public Long getDataCount() {
        return mDataCount;
    }

    public void setDataCount(Long dataCount) {
        mDataCount = dataCount;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getFee() {
        return mFee;
    }

    public void setFee(String fee) {
        mFee = fee;
    }

    public String getLoginName() {
        return mLoginName;
    }

    public void setLoginName(String loginName) {
        mLoginName = loginName;
    }

    public String getRefundCount() {
        return mRefundCount;
    }

    public void setRefundCount(String refundCount) {
        mRefundCount = refundCount;
    }

    public String getRefundFee() {
        return mRefundFee;
    }

    public void setRefundFee(String refundFee) {
        mRefundFee = refundFee;
    }

    public String getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(String totalCount) {
        mTotalCount = totalCount;
    }

    public String getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(String totalFee) {
        mTotalFee = totalFee;
    }

}
