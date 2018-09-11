
package com.yang.yunwang.home.homeservice.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RoleTypeResp {

    @SerializedName("CustomerName")
    private String mCustomerName;
    @SerializedName("CustomerSysNo")
    private String mCustomerSysNo;
    @SerializedName("CustomersTopSysNo")
    private String mCustomersTopSysNo;
    @SerializedName("CustomersType")
    private String mCustomersType;
    @SerializedName("Switch")
    private Long mSwitch;

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

    public String getCustomersType() {
        return mCustomersType;
    }

    public void setCustomersType(String customersType) {
        mCustomersType = customersType;
    }

    public Long getSwitch() {
        return mSwitch;
    }

    public void setSwitch(Long switchS) {
        mSwitch = switchS;
    }

}
