
package com.yang.yunwang.home.mainhome.bean.update;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StaffUpdateReq {

    @SerializedName("DisplayName")
    private String mDisplayName;
    @SerializedName("DwellAddress")
    private String mDwellAddress;
    @SerializedName("DwellAddressID")
    private String mDwellAddressID;
    @SerializedName("Email")
    private String mEmail;
    @SerializedName("PhoneNumber")
    private String mPhoneNumber;
    @SerializedName("Rate")
    private String mRate;
    @SerializedName("SysNo")
    private String mSysNo;

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getDwellAddress() {
        return mDwellAddress;
    }

    public void setDwellAddress(String dwellAddress) {
        mDwellAddress = dwellAddress;
    }

    public String getDwellAddressID() {
        return mDwellAddressID;
    }

    public void setDwellAddressID(String dwellAddressID) {
        mDwellAddressID = dwellAddressID;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getRate() {
        return mRate;
    }

    public void setRate(String rate) {
        mRate = rate;
    }

    public String getSysNo() {
        return mSysNo;
    }

    public void setSysNo(String sysNo) {
        mSysNo = sysNo;
    }

}
