
package com.yang.yunwang.query.api.bean.merchsearch;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MerchListReq {

    @SerializedName("Customer")
    private String mCustomer;
    @SerializedName("CustomerName")
    private String mCustomerName;
    @SerializedName("CustomersTopSysNo")
    private String mCustomersTopSysNo;
    @SerializedName("PagingInfo")
    private PagingInfo mPagingInfo;
    @SerializedName("SortingInfo")
    private SortingInfo mSortingInfo;

    public SortingInfo getSortingInfo() {
        return mSortingInfo;
    }

    public void setSortingInfo(SortingInfo sortingInfo) {
        mSortingInfo = sortingInfo;
    }

    public String getSystemUserSysNo() {
        return systemUserSysNo;
    }

    public void setSystemUserSysNo(String systemUserSysNo) {
        this.systemUserSysNo = systemUserSysNo;
    }

    @SerializedName("SystemUserSysNo")
    private String systemUserSysNo;

    public String getCustomer() {
        return mCustomer;
    }

    public void setCustomer(String customer) {
        mCustomer = customer;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String customerName) {
        mCustomerName = customerName;
    }

    public String getCustomersTopSysNo() {
        return mCustomersTopSysNo;
    }

    public void setCustomersTopSysNo(String customersTopSysNo) {
        mCustomersTopSysNo = customersTopSysNo;
    }

    public PagingInfo getPagingInfo() {
        return mPagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        mPagingInfo = pagingInfo;
    }

}
