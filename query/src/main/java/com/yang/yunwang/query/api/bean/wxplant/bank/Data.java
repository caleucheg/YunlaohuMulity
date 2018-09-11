
package com.yang.yunwang.query.api.bean.wxplant.bank;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("Old_SysNo")
    private Object oldSysNo;
    @SerializedName("OpenID")
    private Object openID;
    @SerializedName("Out_refund_no")
    private Object outRefundNo;
    @SerializedName("Out_trade_no")
    private Object outTradeNo;
    @SerializedName("Pay_Type")
    private Object payType;
    @SerializedName("ResultData")
    private Object resultData;
    @SerializedName("Status")
    private Object status;
    @SerializedName("Time_Start")
    private Object timeStart;
    @SerializedName("WxPayData")
    private WxPayData wxPayData;
    @SerializedName("YwMch_id")
    private Object ywMchId;

    public Object getOldSysNo() {
        return oldSysNo;
    }

    public void setOldSysNo(Object oldSysNo) {
        this.oldSysNo = oldSysNo;
    }

    public Object getOpenID() {
        return openID;
    }

    public void setOpenID(Object openID) {
        this.openID = openID;
    }

    public Object getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(Object outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public Object getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(Object outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Object getPayType() {
        return payType;
    }

    public void setPayType(Object payType) {
        this.payType = payType;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Object timeStart) {
        this.timeStart = timeStart;
    }

    public WxPayData getWxPayData() {
        return wxPayData;
    }

    public void setWxPayData(WxPayData wxPayData) {
        this.wxPayData = wxPayData;
    }

    public Object getYwMchId() {
        return ywMchId;
    }

    public void setYwMchId(Object ywMchId) {
        this.ywMchId = ywMchId;
    }

}
