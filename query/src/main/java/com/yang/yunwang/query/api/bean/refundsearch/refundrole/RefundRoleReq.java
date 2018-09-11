
package com.yang.yunwang.query.api.bean.refundsearch.refundrole;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RefundRoleReq {

    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;

    public String getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(String systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
    }

}
