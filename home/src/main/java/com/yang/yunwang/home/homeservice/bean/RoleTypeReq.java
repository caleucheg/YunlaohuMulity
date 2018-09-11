
package com.yang.yunwang.home.homeservice.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RoleTypeReq {

    @SerializedName("systemUserSysNo")
    private Long mSystemUserSysNo;

    public Long getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(Long systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
    }

}
