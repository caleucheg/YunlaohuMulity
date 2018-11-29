
package com.yang.yunwang.home.mainhome.bean.ordersearch;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OrderSearchReq {

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
    @SerializedName("CustomersTopSysNo")
    private String mCustomersTopSysNo;
    @SerializedName("LoginName")
    private String mLoginName;
    @SerializedName("DisplayName")
    private String mDisplayName;
    @SerializedName("CustomerSysNo")
    private String mCustomerSysNo;
    @SerializedName("SystemUserTopSysNo")
    private String mSystemUserTopSysNo;

    public String getCustomer() {
        return mCustomer;
    }

    public void setCustomer(String mCustomer) {
        this.mCustomer = mCustomer;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String mCustomerName) {
        this.mCustomerName = mCustomerName;
    }

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String mOutTradeNo) {
        this.mOutTradeNo = mOutTradeNo;
    }

    public PagingInfo getPagingInfo() {
        return mPagingInfo;
    }

    public void setPagingInfo(PagingInfo mPagingInfo) {
        this.mPagingInfo = mPagingInfo;
    }

    public String getPayType() {
        return mPayType;
    }

    public void setPayType(String mPayType) {
        this.mPayType = mPayType;
    }

    public String getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(String mSystemUserSysNo) {
        this.mSystemUserSysNo = mSystemUserSysNo;
    }

    public String getTimeEnd() {
        return mTimeEnd;
    }

    public void setTimeEnd(String mTimeEnd) {
        this.mTimeEnd = mTimeEnd;
    }

    public String getTimeStart() {
        return mTimeStart;
    }

    public void setTimeStart(String mTimeStart) {
        this.mTimeStart = mTimeStart;
    }

    public String getCustomersTopSysNo() {
        return mCustomersTopSysNo;
    }

    public void setCustomersTopSysNo(String mCustomersTopSysNo) {
        this.mCustomersTopSysNo = mCustomersTopSysNo;
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

    public String getCustomerSysNo() {
        return mCustomerSysNo;
    }

    public void setCustomerSysNo(String mCustomerSysNo) {
        this.mCustomerSysNo = mCustomerSysNo;
    }

    public String getSystemUserTopSysNo() {
        return mSystemUserTopSysNo;
    }

    public void setSystemUserTopSysNo(String mSystemUserTopSysNo) {
        this.mSystemUserTopSysNo = mSystemUserTopSysNo;
    }

    @Override
    public String toString() {
        return "OrderSearchReq{" +
                "mCustomer='" + mCustomer + '\'' +
                ", mCustomerName='" + mCustomerName + '\'' +
                ", mOutTradeNo='" + mOutTradeNo + '\'' +
                ", mPagingInfo=" + mPagingInfo +
                ", mPayType='" + mPayType + '\'' +
                ", mSystemUserSysNo='" + mSystemUserSysNo + '\'' +
                ", mTimeEnd='" + mTimeEnd + '\'' +
                ", mTimeStart='" + mTimeStart + '\'' +
                ", mCustomersTopSysNo='" + mCustomersTopSysNo + '\'' +
                ", mLoginName='" + mLoginName + '\'' +
                ", mDisplayName='" + mDisplayName + '\'' +
                ", mCustomerSysNo='" + mCustomerSysNo + '\'' +
                ", mSystemUserTopSysNo='" + mSystemUserTopSysNo + '\'' +
                '}';
    }
}
