
package com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.request;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CommonNewOrderReq {

    @SerializedName("Customer")
    private String mCustomer;
    @SerializedName("CustomerName")
    private String mCustomerName;
    @SerializedName("CustomerSysNo")
    private Long mCustomerSysNo;
    @SerializedName("CustomersTopSysNo")
    private Long mCustomersTopSysNo;
    @SerializedName("DisplayName")
    private String mDisplayName;
    @SerializedName("LoginName")
    private String mLoginName;
    @SerializedName("Out_trade_no")
    private String mOutTradeNo;
    @SerializedName("PagingInfo")
    private PagingInfo mPagingInfo;
    @SerializedName("Remarks")
    private String mRemarks;
    @SerializedName("SystemUserSysNo")
    private Long mSystemUserSysNo;
    @SerializedName("SystemUserTopSysNo")
    private Long mSystemUserTopSysNo;
    @SerializedName("Time_End")
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

    public String getRemarks() {
        return mRemarks;
    }

    public void setRemarks(String remarks) {
        mRemarks = remarks;
    }

    public Long getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(Long systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
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

    @Override
    public String toString() {
        return "CommonNewOrderReq{" +
                "mCustomer='" + mCustomer + '\'' +
                ", mCustomerName='" + mCustomerName + '\'' +
                ", mCustomerSysNo=" + mCustomerSysNo +
                ", mCustomersTopSysNo=" + mCustomersTopSysNo +
                ", mDisplayName='" + mDisplayName + '\'' +
                ", mLoginName='" + mLoginName + '\'' +
                ", mOutTradeNo='" + mOutTradeNo + '\'' +
                ", mPagingInfo=" + mPagingInfo +
                ", mRemarks='" + mRemarks + '\'' +
                ", mSystemUserSysNo=" + mSystemUserSysNo +
                ", mSystemUserTopSysNo=" + mSystemUserTopSysNo +
                ", mTimeEnd='" + mTimeEnd + '\'' +
                ", mTimeStart='" + mTimeStart + '\'' +
                '}';
    }
}
