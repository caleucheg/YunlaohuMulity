
package com.yang.yunwang.query.api.bean.merchinfo.merchrole;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class MerchRoleLiftResp implements Serializable{

    @SerializedName("CustomerServiceSysNo")
    private String mCustomerServiceSysNo;
    @SerializedName("Description")
    private String mDescription;
    @SerializedName("RoleName")
    private String mRoleName;
    @SerializedName("SystemRoleSysNo")
    private String mSystemRoleSysNo;
    @SerializedName("URL")
    private String mURL;

    public String getCustomerServiceSysNo() {
        return mCustomerServiceSysNo;
    }

    public void setCustomerServiceSysNo(String customerServiceSysNo) {
        mCustomerServiceSysNo = customerServiceSysNo;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getRoleName() {
        return mRoleName;
    }

    public void setRoleName(String roleName) {
        mRoleName = roleName;
    }

    public String getSystemRoleSysNo() {
        return mSystemRoleSysNo;
    }

    public void setSystemRoleSysNo(String systemRoleSysNo) {
        mSystemRoleSysNo = systemRoleSysNo;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String uRL) {
        mURL = uRL;
    }

}
