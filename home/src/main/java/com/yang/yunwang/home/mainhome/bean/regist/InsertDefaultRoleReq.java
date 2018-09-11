
package com.yang.yunwang.home.mainhome.bean.regist;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class InsertDefaultRoleReq {

    @SerializedName("CustomerServiceSysNo")
    private String mCustomerServiceSysNo;
    @SerializedName("EditUser")
    private String mEditUser;
    @SerializedName("InUser")
    private String mInUser;
    @SerializedName("Type")
    private String mType;

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

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
