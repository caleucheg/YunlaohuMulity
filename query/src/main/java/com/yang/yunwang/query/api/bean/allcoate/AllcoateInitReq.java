
package com.yang.yunwang.query.api.bean.allcoate;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AllcoateInitReq {

    @SerializedName("PagingInfo")
    private PagingInfo mPagingInfo;
    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;

    public String getCustomerServiceSysNo() {
        return customerServiceSysNo;
    }

    public void setCustomerServiceSysNo(String customerServiceSysNo) {
        this.customerServiceSysNo = customerServiceSysNo;
    }

    @SerializedName("CustomerServiceSysNo")
    private String customerServiceSysNo;



    public PagingInfo getPagingInfo() {
        return mPagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        mPagingInfo = pagingInfo;
    }

    public String getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(String systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
    }
    //{"SystemUserSysNo":"8681","CustomerServiceSysNo":"7015"}

}
