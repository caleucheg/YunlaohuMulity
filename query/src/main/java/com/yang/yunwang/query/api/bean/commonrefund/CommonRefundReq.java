
package com.yang.yunwang.query.api.bean.commonrefund;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CommonRefundReq {

    @SerializedName("Create_Time_end")
    private String mCreateTimeEnd;
    @SerializedName("Create_Time_Start")
    private String mCreateTimeStart;
    @SerializedName("CustomerSysNo")
    private String mCustomerSysNo;
    @SerializedName("Out_trade_no")
    private String mOutTradeNo;
    @SerializedName("PagingInfo")
    private PagingInfo mPagingInfo;
    @SerializedName("Pay_Type")
    private String mPayType;


    @SerializedName("Customer")
    private String customer;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @SerializedName("CustomerName")
    private String customerName;

    public String getSystemUserSysNo() {
        return systemUserSysNo;
    }

    public void setSystemUserSysNo(String systemUserSysNo) {
        this.systemUserSysNo = systemUserSysNo;
    }

    @SerializedName("SystemUserSysNo")
    private String systemUserSysNo;

    public String getCustomersTopSysNo() {
        return customersTopSysNo;
    }

    public void setCustomersTopSysNo(String customersTopSysNo) {
        this.customersTopSysNo = customersTopSysNo;
    }

    @SerializedName("CustomersTopSysNo")
    private String customersTopSysNo;

    public String getCreateTimeEnd() {
        return mCreateTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        mCreateTimeEnd = createTimeEnd;
    }

    public String getCreateTimeStart() {
        return mCreateTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        mCreateTimeStart = createTimeStart;
    }

    public String getCustomerSysNo() {
        return mCustomerSysNo;
    }

    public void setCustomerSysNo(String customerSysNo) {
        mCustomerSysNo = customerSysNo;
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

}
