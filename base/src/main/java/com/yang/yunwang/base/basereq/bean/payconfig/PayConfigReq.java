
package com.yang.yunwang.base.basereq.bean.payconfig;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class PayConfigReq {

    @SerializedName("CustomerSysNo")
    private String mCustomerSysNo;

    public String getCustomerSysNo() {
        return mCustomerSysNo;
    }

    public void setCustomerSysNo(String customerSysNo) {
        mCustomerSysNo = customerSysNo;
    }

}
