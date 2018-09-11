
package com.yang.yunwang.query.api.bean.allcopage;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class InsertRoleReq {

    @SerializedName("CustomerServiceSysNo")
    private String mCustomerServiceSysNo;
    @SerializedName("EditUser")
    private String mEditUser;
    @SerializedName("InUser")
    private String mInUser;
    @SerializedName("SystemRoleSysNo")
    private String mSystemRoleSysNo;

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

    public String getEditUser() {
        return mEditUser;
    }

    public void setEditUser(String editUser) {
        mEditUser = editUser;
    }

    public String getInUser() {
        return mInUser;
    }

    public void setInUser(String inUser) {
        mInUser = inUser;
    }

    public String getSystemRoleSysNo() {
        return mSystemRoleSysNo;
    }

    public void setSystemRoleSysNo(String systemRoleSysNo) {
        mSystemRoleSysNo = systemRoleSysNo;
    }

}
