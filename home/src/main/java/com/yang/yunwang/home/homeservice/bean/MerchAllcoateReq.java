
package com.yang.yunwang.home.homeservice.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MerchAllcoateReq {

    @SerializedName("CustomerServiceSysNo")
    private Long mCustomerServiceSysNo;

    public Long getCustomerServiceSysNo() {
        return mCustomerServiceSysNo;
    }

    public void setCustomerServiceSysNo(Long customerServiceSysNo) {
        mCustomerServiceSysNo = customerServiceSysNo;
    }

}
