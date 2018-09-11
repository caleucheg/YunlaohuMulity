
package com.yang.yunwang.query.api.bean.allcopage;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DeleteRoleReq {

    @SerializedName("CustomerServiceSysNo")
    private String mCustomerServiceSysNo;

    public String getSystemUserSysNo() {
        return systemUserSysNo;
    }

    public void setSystemUserSysNo(String systemUserSysNo) {
        this.systemUserSysNo = systemUserSysNo;
    }

    @SerializedName("SystemUserSysNo")
    private String systemUserSysNo;

    public String getCustomerServiceSysNo() {
        return mCustomerServiceSysNo;
    }

    public void setCustomerServiceSysNo(String customerServiceSysNo) {
        mCustomerServiceSysNo = customerServiceSysNo;
    }

}
