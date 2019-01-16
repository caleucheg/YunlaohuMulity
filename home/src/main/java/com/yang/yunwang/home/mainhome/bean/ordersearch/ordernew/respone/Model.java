
package com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.respone;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Model {

    @SerializedName("Bank_type")
    private String mBankType;
    @SerializedName("Cash_fee")
    private String mCashFee;
    @SerializedName("Customer")
    private String mCustomer;
    @SerializedName("CustomerName")
    private String mCustomerName;
    @SerializedName("CustomerSysNo")
    private String mCustomerSysNo;
    @SerializedName("CustomersTopSysNo")
    private String mCustomersTopSysNo;
    @SerializedName("DataCount")
    private Long mDataCount;
    @SerializedName("DisplayName")
    private String mDisplayName;
    @SerializedName("LoginName")
    private String mLoginName;
    @SerializedName("Old_SysNo")
    private String mOldSysNo;
    @SerializedName("Out_trade_no")
    private String mOutTradeNo;
    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("Remarks")
    private String mRemarks;
    @SerializedName("SysNo")
    private String mSysNo;
    @SerializedName("Time_end")
    private String mTimeEnd;
    @SerializedName("Time_Start")
    private String mTimeStart;
    @SerializedName("Total_fee")
    private String mTotalFee;
    @SerializedName("YwMch_id")
    private Object mYwMchId;

    public String getBankType() {
        return mBankType;
    }

    public void setBankType(String bankType) {
        mBankType = bankType;
    }

    public String getCashFee() {
        return mCashFee;
    }

    public void setCashFee(String cashFee) {
        mCashFee = cashFee;
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

    public String getLoginName() {
        return mLoginName;
    }

    public void setLoginName(String loginName) {
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

    public String getRemarks() {
        return mRemarks;
    }

    public void setRemarks(String remarks) {
        mRemarks = remarks;
    }

    public String getSysNo() {
        return mSysNo;
    }

    public void setSysNo(String sysNo) {
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

    public String getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(String totalFee) {
        mTotalFee = totalFee;
    }

    public Object getYwMchId() {
        return mYwMchId;
    }

    public void setYwMchId(Object ywMchId) {
        mYwMchId = ywMchId;
    }

}
