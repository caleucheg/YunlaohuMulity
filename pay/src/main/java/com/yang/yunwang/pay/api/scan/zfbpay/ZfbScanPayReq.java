
package com.yang.yunwang.pay.api.scan.zfbpay;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ZfbScanPayReq {

    @SerializedName("Auth_code")
    private String mAuthCode;
    @SerializedName("CustomerSysNo")
    private String mCustomerSysNo;
    @SerializedName("Old_SysNo")
    private String mOldSysNo;
    @SerializedName("out_trade_no")
    private String mOutTradeNo;
    @SerializedName("Total_amount")
    private Long mTotalAmount;

    public String getAuthCode() {
        return mAuthCode;
    }

    public void setAuthCode(String authCode) {
        mAuthCode = authCode;
    }

    public String getCustomerSysNo() {
        return mCustomerSysNo;
    }

    public void setCustomerSysNo(String customerSysNo) {
        mCustomerSysNo = customerSysNo;
    }

    public String getOldSysNo() {
        return mOldSysNo;
    }

    public void setOldSysNo(String oldSysNo) {
        mOldSysNo = oldSysNo;
    }

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

    public Long getTotalAmount() {
        return mTotalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        mTotalAmount = totalAmount;
    }

}
