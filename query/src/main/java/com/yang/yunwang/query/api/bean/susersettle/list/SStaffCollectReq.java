
package com.yang.yunwang.query.api.bean.susersettle.list;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SStaffCollectReq {

    @SerializedName("CustomersTopSysNo")
    private String mCustomersTopSysNo;
    @SerializedName("DisplayName")
    private String mDisplayName;
    @SerializedName("LoginName")
    private String mLoginName;
    @SerializedName("PagingInfo")
    private PagingInfo mPagingInfo;
    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("PhoneNumber")
    private String mPhoneNumber;
    @SerializedName("Time_end")
    private String mTimeEnd;
    @SerializedName("Time_Start")
    private String mTimeStart;

    public String getCustomersTopSysNo() {
        return mCustomersTopSysNo;
    }

    public void setCustomersTopSysNo(String customersTopSysNo) {
        mCustomersTopSysNo = customersTopSysNo;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getLoginName() {
        return mLoginName;
    }

    public void setLoginName(String loginName) {
        mLoginName = loginName;
    }

    public PagingInfo getPagingInfo() {
        return mPagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        mPagingInfo = pagingInfo;
    }

    public String getPayType() {
        return mPayType;
    }

    public void setPayType(String payType) {
        mPayType = payType;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getTimeEnd() {
        return mTimeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        mTimeEnd = timeEnd;
    }

    public String getTimeStart() {
        return mTimeStart;
    }

    public void setTimeStart(String timeStart) {
        mTimeStart = timeStart;
    }

}
