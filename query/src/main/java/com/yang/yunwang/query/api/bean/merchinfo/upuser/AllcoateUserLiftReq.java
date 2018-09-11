
package com.yang.yunwang.query.api.bean.merchinfo.upuser;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AllcoateUserLiftReq {

    @SerializedName("CustomerServiceSysNo")
    private String mCustomerServiceSysNo;
    @SerializedName("PagingInfo")
    private PagingInfo mPagingInfo;

    public String getCustomerServiceSysNo() {
        return mCustomerServiceSysNo;
    }

    public void setCustomerServiceSysNo(String customerServiceSysNo) {
        mCustomerServiceSysNo = customerServiceSysNo;
    }

    public PagingInfo getPagingInfo() {
        return mPagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        mPagingInfo = pagingInfo;
    }

}
