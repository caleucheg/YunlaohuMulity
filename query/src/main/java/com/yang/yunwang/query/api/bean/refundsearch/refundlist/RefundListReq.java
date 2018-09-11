
package com.yang.yunwang.query.api.bean.refundsearch.refundlist;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RefundListReq {

    @SerializedName("CustomerSysNo")
    private String mCustomerSysNo;
    @SerializedName("Out_trade_no")
    private String mOutTradeNo;
    @SerializedName("PagingInfo")
    private PagingInfo mPagingInfo;
    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;
    @SerializedName("Time_end")
    private String mTimeEnd;
    @SerializedName("Time_Start")
    private String mTimeStart;

    public String getCustomerSysNo() {
        return mCustomerSysNo;
    }

    public void setCustomerSysNo(String customerSysNo) {
        mCustomerSysNo = customerSysNo;
    }

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

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
