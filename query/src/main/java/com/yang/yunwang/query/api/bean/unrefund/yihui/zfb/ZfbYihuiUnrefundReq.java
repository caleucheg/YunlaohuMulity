
package com.yang.yunwang.query.api.bean.unrefund.yihui.zfb;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ZfbYihuiUnrefundReq {

    @SerializedName("out_trade_no")
    private String mOutTradeNo;
    @SerializedName("refund_fee")
    private String mRefundFee;
    @SerializedName("SOSysNo")
    private String mSOSysNo;
    @SerializedName("total_fee")
    private String mTotalFee;
    @SerializedName("YwMch_id2")
    private Long mYwMchId2;

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

    public String getRefundFee() {
        return mRefundFee;
    }

    public void setRefundFee(String refundFee) {
        mRefundFee = refundFee;
    }

    public String getSOSysNo() {
        return mSOSysNo;
    }

    public void setSOSysNo(String sOSysNo) {
        mSOSysNo = sOSysNo;
    }

    public String getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(String totalFee) {
        mTotalFee = totalFee;
    }

    public Long getYwMchId2() {
        return mYwMchId2;
    }

    public void setYwMchId2(Long ywMchId2) {
        mYwMchId2 = ywMchId2;
    }

}
