
package com.yang.yunwang.query.api.bean.commonrefund;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Model implements Serializable {

    @SerializedName("count")
    private Long mCount;
    @SerializedName("CreateTime")
    private String mCreateTime;
    @SerializedName("Customer")
    private String mCustomer;
    @SerializedName("CustomerName")
    private String mCustomerName;
    @SerializedName("CustomerSysNo")
    private String mCustomerSysNo;
    @SerializedName("CustomersTopSysNo")
    private String mCustomersTopSysNo;
    @SerializedName("DisplayName")
    private String mDisplayName;
    @SerializedName("LoginName")
    private String mLoginName;
    @SerializedName("Out_trade_no")
    private String mOutTradeNo;
    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("Refund_fee")
    private String mRefundFee;
    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;
    @SerializedName("Time_Start")
    private String mTimeStart;
    @SerializedName("Total_fee")
    private String mTotalFee;

    public Long getCount() {
        return mCount;
    }

    public void setCount(Long count) {
        mCount = count;
    }

    public String getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(String createTime) {
        mCreateTime = createTime;
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

    public String getCustomerSysNo() {
        return mCustomerSysNo;
    }

    public void setCustomerSysNo(String customerSysNo) {
        mCustomerSysNo = customerSysNo;
    }

    public String getCustomersTopSysNo() {
        return mCustomersTopSysNo;
    }

    public void setCustomersTopSysNo(String customersTopSysNo) {
        mCustomersTopSysNo = customersTopSysNo;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getLoginName() {
        return mLoginName;
    }

    public void setLoginName(String loginName) {
        mLoginName = loginName;
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

    public String getRefundFee() {
        return mRefundFee;
    }

    public void setRefundFee(String refundFee) {
        mRefundFee = refundFee;
    }

    public String getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(String systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
    }

    public String getTimeStart() {
        return mTimeStart;
    }

    public void setTimeStart(String timeStart) {
        mTimeStart = timeStart;
    }

    public String getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(String totalFee) {
        mTotalFee = totalFee;
    }

}
