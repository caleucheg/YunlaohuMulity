
package com.yang.yunwang.query.api.bean.unrefund.yihui.wx;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WxYihuiUnrefundReq {

    @SerializedName("out_trade_no")
    private String mOutTradeNo;
    @SerializedName("refund_fee")
    private Long mRefundFee;
    @SerializedName("SOSysNo")
    private Long mSOSysNo;
    @SerializedName("total_fee")
    private Long mTotalFee;
    @SerializedName("YwMch_id2")
    private Long mYwMchId2;

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

    public Long getRefundFee() {
        return mRefundFee;
    }

    public void setRefundFee(Long refundFee) {
        mRefundFee = refundFee;
    }

    public Long getSOSysNo() {
        return mSOSysNo;
    }

    public void setSOSysNo(Long sOSysNo) {
        mSOSysNo = sOSysNo;
    }

    public Long getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(Long totalFee) {
        mTotalFee = totalFee;
    }

    public Long getYwMchId2() {
        return mYwMchId2;
    }

    public void setYwMchId2(Long ywMchId2) {
        mYwMchId2 = ywMchId2;
    }

}
