
package com.yang.yunwang.home.homeservice.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StaffAllcoateReq {

    @SerializedName("SystemUserSysNo")
    private Long mSystemUserSysNo;

    public Long getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(Long systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
    }

}
