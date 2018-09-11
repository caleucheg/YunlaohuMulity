
package com.yang.yunwang.home.mainhome.bean.update.rate;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CommonRateReq {

    @SerializedName("CustomerSysNo")
    private String mCustomerSysNo;

    public String getCustomerSysNo() {
        return mCustomerSysNo;
    }

    public void setCustomerSysNo(String customerSysNo) {
        mCustomerSysNo = customerSysNo;
    }

}
