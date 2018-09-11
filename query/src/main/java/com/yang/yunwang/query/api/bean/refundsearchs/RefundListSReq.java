
package com.yang.yunwang.query.api.bean.refundsearchs;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RefundListSReq {

    @SerializedName("Create_Time_end")
    private String mCreateTimeEnd;
    @SerializedName("Create_Time_Start")
    private String mCreateTimeStart;
    @SerializedName("CustomerSysNo")
    private String mCustomerSysNo;
    @SerializedName("Out_trade_no")
    private String mOutTradeNo;
    @SerializedName("PagingInfo")
    private PagingInfo mPagingInfo;
    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;
    @SerializedName("Time_end")
    private String mTimeEnd;
    @SerializedName("Time_Start")
    private String mTimeStart;

    public String getCreateTimeEnd() {
        return mCreateTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        mCreateTimeEnd = createTimeEnd;
    }

    public String getCreateTimeStart() {
        return mCreateTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        mCreateTimeStart = createTimeStart;
    }

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

    public String getPayType() {
        return mPayType;
    }

    public void setPayType(String payType) {
        mPayType = payType;
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
