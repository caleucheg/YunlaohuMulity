
package com.yang.yunwang.home.mainhome.bean.update;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MerchUpdateReq {

    @SerializedName("Customer_field_three")
    private Long mCustomerFieldThree;
    @SerializedName("CustomerName")
    private String mCustomerName;
    @SerializedName("DwellAddress")
    private String mDwellAddress;
    @SerializedName("DwellAddressID")
    private String mDwellAddressID;
    @SerializedName("Email")
    private String mEmail;
    @SerializedName("NickName")
    private String mNickName;
    @SerializedName("Phone")
    private String mPhone;
    @SerializedName("SysNo")
    private String mSysNo;
    @SerializedName("SystemClassID")
    private String mSystemClassID;
    @SerializedName("SystemClassName")
    private String mSystemClassName;

    public Long getCustomerFieldThree() {
        return mCustomerFieldThree;
    }

    public void setCustomerFieldThree(Long customerFieldThree) {
        mCustomerFieldThree = customerFieldThree;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String customerName) {
        mCustomerName = customerName;
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

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        mNickName = nickName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getSysNo() {
        return mSysNo;
    }

    public void setSysNo(String sysNo) {
        mSysNo = sysNo;
    }

    public String getSystemClassID() {
        return mSystemClassID;
    }

    public void setSystemClassID(String systemClassID) {
        mSystemClassID = systemClassID;
    }

    public String getSystemClassName() {
        return mSystemClassName;
    }

    public void setSystemClassName(String systemClassName) {
        mSystemClassName = systemClassName;
    }

}
