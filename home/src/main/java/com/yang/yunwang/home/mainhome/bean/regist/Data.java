
package com.yang.yunwang.home.mainhome.bean.regist;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("CustomerServiceSysNo")
    private String mCustomerServiceSysNo;

    public String getCustomerServiceSysNo() {
        return mCustomerServiceSysNo;
    }

    public void setCustomerServiceSysNo(String customerServiceSysNo) {
        mCustomerServiceSysNo = customerServiceSysNo;
    }

}
