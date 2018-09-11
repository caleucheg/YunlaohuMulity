
package com.yang.yunwang.pay.api.scan.snowno;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SnowNoReq {

    @SerializedName("orderType")
    private String mOrderType;
    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;

    public String getOrderType() {
        return mOrderType;
    }

    public void setOrderType(String orderType) {
        mOrderType = orderType;
    }

    public String getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(String systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
    }

}
