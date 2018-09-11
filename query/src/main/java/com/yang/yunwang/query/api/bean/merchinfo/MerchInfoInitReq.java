
package com.yang.yunwang.query.api.bean.merchinfo;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MerchInfoInitReq {

    public String getCustomerSysNo() {
        return customerSysNo;
    }

    public void setCustomerSysNo(String customerSysNo) {
        this.customerSysNo = customerSysNo;
    }

    @SerializedName("CustomerSysNo")
    private String customerSysNo;
    @SerializedName("CustomerServiceSysNo")
    private String mCustomerServiceSysNo;

    public String getCustomerServiceSysNo() {
        return mCustomerServiceSysNo;
    }

    public void setCustomerServiceSysNo(String customerServiceSysNo) {
        mCustomerServiceSysNo = customerServiceSysNo;
    }

}
