
package com.yang.yunwang.query.api.bean.wxplant.bank;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WxBankPlantReq {

    @SerializedName("out_trade_no")
    private String outTradeNo;
    @SerializedName("Pay_Type")
    private String payType;
    @SerializedName("systemUserSysNo")
    private String systemUserSysNo;
    @SerializedName("transaction_id")
    private String transactionId;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSystemUserSysNo() {
        return systemUserSysNo;
    }

    public void setSystemUserSysNo(String systemUserSysNo) {
        this.systemUserSysNo = systemUserSysNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}
