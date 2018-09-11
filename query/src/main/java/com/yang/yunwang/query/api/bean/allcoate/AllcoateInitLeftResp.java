
package com.yang.yunwang.query.api.bean.allcoate;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class AllcoateInitLeftResp implements Serializable {

    @SerializedName("Description")
    private String mDescription;
    @SerializedName("EditDate")
    private Object mEditDate;
    @SerializedName("EditUser")
    private Long mEditUser;
    @SerializedName("InDate")
    private String mInDate;
    @SerializedName("InUser")
    private Long mInUser;
    @SerializedName("RoleName")
    private String mRoleName;
    @SerializedName("SysNo")
    private Long mSysNo;
    @SerializedName("SystemRoleSysNo")
    private Long mSystemRoleSysNo;
    @SerializedName("SystemUserSysNo")
    private Long mSystemUserSysNo;
    @SerializedName("URL")
    private String mURL;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Object getEditDate() {
        return mEditDate;
    }

    public void setEditDate(Object editDate) {
        mEditDate = editDate;
    }

    public Long getEditUser() {
        return mEditUser;
    }

    public void setEditUser(Long editUser) {
        mEditUser = editUser;
    }

    public String getInDate() {
        return mInDate;
    }

    public void setInDate(String inDate) {
        mInDate = inDate;
    }

    public Long getInUser() {
        return mInUser;
    }

    public void setInUser(Long inUser) {
        mInUser = inUser;
    }

    public String getRoleName() {
        return mRoleName;
    }

    public void setRoleName(String roleName) {
        mRoleName = roleName;
    }

    public Long getSysNo() {
        return mSysNo;
    }

    public void setSysNo(Long sysNo) {
        mSysNo = sysNo;
    }

    public Long getSystemRoleSysNo() {
        return mSystemRoleSysNo;
    }

    public void setSystemRoleSysNo(Long systemRoleSysNo) {
        mSystemRoleSysNo = systemRoleSysNo;
    }

    public Long getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(Long systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String uRL) {
        mURL = uRL;
    }

}
