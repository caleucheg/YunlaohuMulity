
package com.yang.yunwang.query.api.bean.orderprint;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OrderDetialReq {

    @SerializedName("Customer")
    private String mCustomer;
    @SerializedName("out_trade_no")
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

    @SerializedName("CustomerSysNo")
    private String customerSysNo;
    @SerializedName("DisplayName")
    private String displayName;

    public String getCustomerSysNo() {
        return customerSysNo;
    }

    public void setCustomerSysNo(String customerSysNo) {
        this.customerSysNo = customerSysNo;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @SerializedName("LoginName")
    private String loginName;


    public String getCustomer() {
        return mCustomer;
    }

    public void setCustomer(String customer) {
        mCustomer = customer;
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
