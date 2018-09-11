
package com.yang.yunwang.home.homeservice.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PassageWayReq {

    @SerializedName("PassageWay")
    private String mPassageWay;
    @SerializedName("PassageWayName")
    private String mPassageWayName;
    @SerializedName("Remarks")
    private String mRemarks;
    @SerializedName("state")
    private String mState;
    @SerializedName("SysNo")
    private String mSysNo;
    @SerializedName("Type")
    private String mType;
    @SerializedName("TypeName")
    private String mTypeName;

    public String getPassageWay() {
        return mPassageWay;
    }

    public void setPassageWay(String passageWay) {
        mPassageWay = passageWay;
    }

    public String getPassageWayName() {
        return mPassageWayName;
    }

    public void setPassageWayName(String passageWayName) {
        mPassageWayName = passageWayName;
    }

    public String getRemarks() {
        return mRemarks;
    }

    public void setRemarks(String remarks) {
        mRemarks = remarks;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getSysNo() {
        return mSysNo;
    }

    public void setSysNo(String sysNo) {
        mSysNo = sysNo;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getTypeName() {
        return mTypeName;
    }

    public void setTypeName(String typeName) {
        mTypeName = typeName;
    }

}
