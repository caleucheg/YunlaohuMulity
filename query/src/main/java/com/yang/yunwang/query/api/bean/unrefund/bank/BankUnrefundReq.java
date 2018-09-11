
package com.yang.yunwang.query.api.bean.unrefund.bank;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BankUnrefundReq {

    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("refund_fee")
    private String mRefundFee;
    @SerializedName("SOSysNo")
    private String mSOSysNo;
    @SerializedName("total_fee")
    private String mTotalFee;
    @SerializedName("transaction_id")
    private String mTransactionId;
    @SerializedName("YwMch_id2")
    private Long mYwMchId2;

    public String getPayType() {
        return mPayType;
    }

    public void setPayType(String payType) {
        mPayType = payType;
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

    public String getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(String transactionId) {
        mTransactionId = transactionId;
    }

    public Long getYwMchId2() {
        return mYwMchId2;
    }

    public void setYwMchId2(Long ywMchId2) {
        mYwMchId2 = ywMchId2;
    }

}
