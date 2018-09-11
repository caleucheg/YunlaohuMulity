
package com.yang.yunwang.home.homeservice.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class JidInsertReq {

    @SerializedName("Registration_Id")
    private String mRegistrationId;
    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;

    public String getRegistrationId() {
        return mRegistrationId;
    }

    public void setRegistrationId(String registrationId) {
        mRegistrationId = registrationId;
    }

    public String getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(String systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
    }

}
