
package com.yang.yunwang.home.mainhome.bean.homepage.serverstaff.totalinfo;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ServerStaffTotalInfoReq {

    @SerializedName("Customer")
    private String mCustomer;
    @SerializedName("CustomerName")
    private String mCustomerName;
    @SerializedName("CustomersTopSysNo")
    private Long mCustomersTopSysNo;
    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("SystemUserTopSysNo")
    private Long mSystemUserTopSysNo;
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

    public Long getCustomersTopSysNo() {
        return mCustomersTopSysNo;
    }

    public void setCustomersTopSysNo(Long customersTopSysNo) {
        mCustomersTopSysNo = customersTopSysNo;
    }

    public String getPayType() {
        return mPayType;
    }

    public void setPayType(String payType) {
        mPayType = payType;
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

}
