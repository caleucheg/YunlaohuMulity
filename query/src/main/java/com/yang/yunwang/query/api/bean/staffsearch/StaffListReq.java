
package com.yang.yunwang.query.api.bean.staffsearch;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StaffListReq {

    @SerializedName("CustomerServiceSysNo")
    private String mCustomerServiceSysNo;
    @SerializedName("LoginName")
    private String mLoginName;
    @SerializedName("PagingInfo")
    private PagingInfo mPagingInfo;
    @SerializedName("PhoneNumber")
    private String mPhoneNumber;
    @SerializedName("DisplayName")
    private String mDisplayName;
    @SerializedName("SortingInfo")
    private SortingInfo mSortingInfo;

    public SortingInfo getSortingInfo() {
        return mSortingInfo;
    }

    public void setSortingInfo(SortingInfo sortingInfo) {
        mSortingInfo = sortingInfo;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String mDisplayName) {
        this.mDisplayName = mDisplayName;
    }

    public String getCustomerServiceSysNo() {
        return mCustomerServiceSysNo;
    }

    public void setCustomerServiceSysNo(String customerServiceSysNo) {
        mCustomerServiceSysNo = customerServiceSysNo;
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

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

}
