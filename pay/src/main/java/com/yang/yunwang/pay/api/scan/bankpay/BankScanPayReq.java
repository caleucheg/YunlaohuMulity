
package com.yang.yunwang.pay.api.scan.bankpay;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BankScanPayReq {

    @SerializedName("Auth_Code")
    private String mAuthCode;
    @SerializedName("Commodity")
    private String mCommodity;
    @SerializedName("Old_SysNo")
    private String mOldSysNo;
    @SerializedName("out_trade_no")
    private String mOutTradeNo;
    @SerializedName("Total_Fee")
    private Long mTotalFee;

    public String getAuthCode() {
        return mAuthCode;
    }

    public void setAuthCode(String authCode) {
        mAuthCode = authCode;
    }

    public String getCommodity() {
        return mCommodity;
    }

    public void setCommodity(String commodity) {
        mCommodity = commodity;
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

    public Long getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(Long totalFee) {
        mTotalFee = totalFee;
    }

}
