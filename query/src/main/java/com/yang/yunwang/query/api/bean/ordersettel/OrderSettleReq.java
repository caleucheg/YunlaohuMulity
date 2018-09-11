
package com.yang.yunwang.query.api.bean.ordersettel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OrderSettleReq {


    @SerializedName("CustomersTopSysNo")
    private String mCustomersTopSysNo;
    @SerializedName("CustomerSysNo")
    private String mCustomerSysNo;
    @SerializedName("LoginName")
    private String mLoginName;
    @SerializedName("DisplayName")
    private String mDisplayName;
    @SerializedName("SystemUserTopSysNo")
    private String mSystemUserTopSysNo;

    public String getCustomersTopSysNo() {
        return mCustomersTopSysNo;
    }

    public void setCustomersTopSysNo(String mCustomersTopSysNo) {
        this.mCustomersTopSysNo = mCustomersTopSysNo;
    }

    public String getCustomerSysNo() {
        return mCustomerSysNo;
    }

    public void setCustomerSysNo(String mCustomerSysNo) {
        this.mCustomerSysNo = mCustomerSysNo;
    }

    public String getLoginName() {
        return mLoginName;
    }

    public void setLoginName(String mLoginName) {
        this.mLoginName = mLoginName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String mDisplayName) {
        this.mDisplayName = mDisplayName;
    }

    public String getSystemUserTopSysNo() {
        return mSystemUserTopSysNo;
    }

    public void setSystemUserTopSysNo(String mSystemUserTopSysNo) {
        this.mSystemUserTopSysNo = mSystemUserTopSysNo;
    }

    @SerializedName("Customer")
    private String mCustomer;
    @SerializedName("CustomerName")
    private String mCustomerName;
    @SerializedName("Out_trade_no")
    private String mOutTradeNo;
    @SerializedName("PagingInfo")
    private PagingInfo mPagingInfo;
    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;
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
