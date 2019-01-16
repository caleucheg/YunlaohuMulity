
package com.yang.yunwang.home.mainhome.bean.homepage.services.activie;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("CustomerCount")
    private String mCustomerCount;
    @SerializedName("CustomersTopSysNo")
    private String mCustomersTopSysNo;

    public String getCustomerCount() {
        return mCustomerCount;
    }

    public void setCustomerCount(String customerCount) {
        mCustomerCount = customerCount;
    }

    public String getCustomersTopSysNo() {
        return mCustomersTopSysNo;
    }

    public void setCustomersTopSysNo(String customersTopSysNo) {
        mCustomersTopSysNo = customersTopSysNo;
    }

}
