
package com.yang.yunwang.query.api.bean.unrefund.yihui.zfb;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("Old_SysNo")
    private Object mOldSysNo;
    @SerializedName("OpenID")
    private Object mOpenID;
    @SerializedName("Out_refund_no")
    private Object mOutRefundNo;
    @SerializedName("Out_trade_no")
    private Object mOutTradeNo;
    @SerializedName("Pay_Type")
    private Object mPayType;
    @SerializedName("ResultData")
    private Object mResultData;
    @SerializedName("Status")
    private Object mStatus;
    @SerializedName("Time_Start")
    private Object mTimeStart;
    @SerializedName("WxPayData")
    private String mWxPayData;
    @SerializedName("YwMch_id")
    private Object mYwMchId;

    public Object getOldSysNo() {
        return mOldSysNo;
    }

    public void setOldSysNo(Object oldSysNo) {
        mOldSysNo = oldSysNo;
    }

    public Object getOpenID() {
        return mOpenID;
    }

    public void setOpenID(Object openID) {
        mOpenID = openID;
    }

    public Object getOutRefundNo() {
        return mOutRefundNo;
    }

    public void setOutRefundNo(Object outRefundNo) {
        mOutRefundNo = outRefundNo;
    }

    public Object getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(Object outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

    public Object getPayType() {
        return mPayType;
    }

    public void setPayType(Object payType) {
        mPayType = payType;
    }

    public Object getResultData() {
        return mResultData;
    }

    public void setResultData(Object resultData) {
        mResultData = resultData;
    }

    public Object getStatus() {
        return mStatus;
    }

    public void setStatus(Object status) {
        mStatus = status;
    }

    public Object getTimeStart() {
        return mTimeStart;
    }

    public void setTimeStart(Object timeStart) {
        mTimeStart = timeStart;
    }

    public String getWxPayData() {
        return mWxPayData;
    }

    public void setWxPayData(String wxPayData) {
        mWxPayData = wxPayData;
    }

    public Object getYwMchId() {
        return mYwMchId;
    }

    public void setYwMchId(Object ywMchId) {
        mYwMchId = ywMchId;
    }

}
