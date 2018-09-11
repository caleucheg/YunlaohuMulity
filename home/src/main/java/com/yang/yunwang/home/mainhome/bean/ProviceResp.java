
package com.yang.yunwang.home.mainhome.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ProviceResp {

    @SerializedName("AddressName")
    private String mAddressName;
    @SerializedName("Parent_id")
    private Long mParentId;
    @SerializedName("Priority")
    private Long mPriority;
    @SerializedName("SysNo")
    private Long mSysNo;

    public String getAddressName() {
        return mAddressName;
    }

    public void setAddressName(String addressName) {
        mAddressName = addressName;
    }

    public Long getParentId() {
        return mParentId;
    }

    public void setParentId(Long parentId) {
        mParentId = parentId;
    }

    public Long getPriority() {
        return mPriority;
    }

    public void setPriority(Long priority) {
        mPriority = priority;
    }

    public Long getSysNo() {
        return mSysNo;
    }

    public void setSysNo(Long sysNo) {
        mSysNo = sysNo;
    }

}
