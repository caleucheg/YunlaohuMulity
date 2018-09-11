
package com.yang.yunwang.query.api.bean.allcoate;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Model implements Serializable{

    @SerializedName("AuthKey")
    private String mAuthKey;
    @SerializedName("Description")
    private String mDescription;
    @SerializedName("EditDate")
    private String mEditDate;
    @SerializedName("EditUser")
    private Long mEditUser;
    @SerializedName("InDate")
    private String mInDate;
    @SerializedName("InUser")
    private Long mInUser;
    @SerializedName("RoleName")
    private String mRoleName;
    @SerializedName("Status")
    private Long mStatus;
    @SerializedName("SysNo")
    private Long mSysNo;
    @SerializedName("Type")
    private Long mType;
    @SerializedName("URL")
    private String mURL;

    public String getAuthKey() {
        return mAuthKey;
    }

    public void setAuthKey(String authKey) {
        mAuthKey = authKey;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getEditDate() {
        return mEditDate;
    }

    public void setEditDate(String editDate) {
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

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public Long getSysNo() {
        return mSysNo;
    }

    public void setSysNo(Long sysNo) {
        mSysNo = sysNo;
    }

    public Long getType() {
        return mType;
    }

    public void setType(Long type) {
        mType = type;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String uRL) {
        mURL = uRL;
    }

}
