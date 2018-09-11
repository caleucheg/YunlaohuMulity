
package com.yang.yunwang.query.api.bean.refundsearch.refundrole;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("ApplicationName")
    private String mApplicationName;
    @SerializedName("ApplicationSysNo")
    private Long mApplicationSysNo;
    @SerializedName("RoleName")
    private String mRoleName;
    @SerializedName("RoleSysNo")
    private Long mRoleSysNo;
    @SerializedName("URL")
    private String mURL;

    public String getApplicationName() {
        return mApplicationName;
    }

    public void setApplicationName(String applicationName) {
        mApplicationName = applicationName;
    }

    public Long getApplicationSysNo() {
        return mApplicationSysNo;
    }

    public void setApplicationSysNo(Long applicationSysNo) {
        mApplicationSysNo = applicationSysNo;
    }

    public String getRoleName() {
        return mRoleName;
    }

    public void setRoleName(String roleName) {
        mRoleName = roleName;
    }

    public Long getRoleSysNo() {
        return mRoleSysNo;
    }

    public void setRoleSysNo(Long roleSysNo) {
        mRoleSysNo = roleSysNo;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String uRL) {
        mURL = uRL;
    }

}
