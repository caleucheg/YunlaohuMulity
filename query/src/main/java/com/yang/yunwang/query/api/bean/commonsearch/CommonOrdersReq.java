
package com.yang.yunwang.query.api.bean.commonsearch;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CommonOrdersReq {

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
    @SerializedName("PagingInfo")
    private PagingInfo mPagingInfo;
    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;
    @SerializedName("SystemUserTopSysNo")
    private String mSystemUserTopSysNo;
    @SerializedName("Time_end")
    private String mTimeEnd;
    @SerializedName("Time_Start")
    private String mTimeStart;

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

    public PagingInfo getPagingInfo() {
        return mPagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        mPagingInfo = pagingInfo;
    }

    public String getPayType() {
        return mPayType;
    }

    public void setPayType(String payType) {
        mPayType = payType;
    }

    public String getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(String systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
    }

    public String getSystemUserTopSysNo() {
        return mSystemUserTopSysNo;
    }

    public void setSystemUserTopSysNo(String systemUserTopSysNo) {
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

}
