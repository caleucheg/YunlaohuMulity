
package com.yang.yunwang.pay.api.scan.wxpay;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WxScanPayReq {

    @SerializedName("auth_code")
    private String mAuthCode;
    @SerializedName("CustomerSysNo")
    private String mCustomerSysNo;
    @SerializedName("Old_SysNo")
    private String mOldSysNo;
    @SerializedName("out_trade_no")
    private String mOutTradeNo;
    @SerializedName("POSID")
    private String mPOSID;
    @SerializedName("ProductTitle")
    private String mProductTitle;
    @SerializedName("Total_fee")
    private Long mTotalFee;

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

    public String getPOSID() {
        return mPOSID;
    }

    public void setPOSID(String pOSID) {
        mPOSID = pOSID;
    }

    public String getProductTitle() {
        return mProductTitle;
    }

    public void setProductTitle(String productTitle) {
        mProductTitle = productTitle;
    }

    public Long getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(Long totalFee) {
        mTotalFee = totalFee;
    }

}
