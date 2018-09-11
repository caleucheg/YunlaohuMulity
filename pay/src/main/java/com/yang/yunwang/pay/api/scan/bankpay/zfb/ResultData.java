
package com.yang.yunwang.pay.api.scan.bankpay.zfb;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ResultData {

    @SerializedName("appid")
    private String mAppid;
    @SerializedName("bank_type")
    private String mBankType;
    @SerializedName("buyer_logon_id")
    private String mBuyerLogonId;
    @SerializedName("buyer_pay_amount")
    private String mBuyerPayAmount;
    @SerializedName("buyer_user_id")
    private String mBuyerUserId;
    @SerializedName("charset")
    private String mCharset;
    @SerializedName("fee_type")
    private String mFeeType;
    @SerializedName("fund_bill_list")
    private String mFundBillList;
    @SerializedName("invoice_amount")
    private String mInvoiceAmount;
    @SerializedName("mch_id")
    private String mMchId;
    @SerializedName("nonce_str")
    private String mNonceStr;
    @SerializedName("openid")
    private String mOpenid;
    @SerializedName("out_trade_no")
    private String mOutTradeNo;
    @SerializedName("out_transaction_id")
    private String mOutTransactionId;
    @SerializedName("pay_result")
    private String mPayResult;
    @SerializedName("point_amount")
    private String mPointAmount;
    @SerializedName("receipt_amount")
    private String mReceiptAmount;
    @SerializedName("result_code")
    private String mResultCode;
    @SerializedName("sign")
    private String mSign;
    @SerializedName("sign_type")
    private String mSignType;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("time_end")
    private String mTimeEnd;
    @SerializedName("total_fee")
    private String mTotalFee;
    @SerializedName("trade_type")
    private String mTradeType;
    @SerializedName("transaction_id")
    private String mTransactionId;
    @SerializedName("uuid")
    private String mUuid;
    @SerializedName("version")
    private String mVersion;

    public String getAppid() {
        return mAppid;
    }

    public void setAppid(String appid) {
        mAppid = appid;
    }

    public String getBankType() {
        return mBankType;
    }

    public void setBankType(String bankType) {
        mBankType = bankType;
    }

    public String getBuyerLogonId() {
        return mBuyerLogonId;
    }

    public void setBuyerLogonId(String buyerLogonId) {
        mBuyerLogonId = buyerLogonId;
    }

    public String getBuyerPayAmount() {
        return mBuyerPayAmount;
    }

    public void setBuyerPayAmount(String buyerPayAmount) {
        mBuyerPayAmount = buyerPayAmount;
    }

    public String getBuyerUserId() {
        return mBuyerUserId;
    }

    public void setBuyerUserId(String buyerUserId) {
        mBuyerUserId = buyerUserId;
    }

    public String getCharset() {
        return mCharset;
    }

    public void setCharset(String charset) {
        mCharset = charset;
    }

    public String getFeeType() {
        return mFeeType;
    }

    public void setFeeType(String feeType) {
        mFeeType = feeType;
    }

    public String getFundBillList() {
        return mFundBillList;
    }

    public void setFundBillList(String fundBillList) {
        mFundBillList = fundBillList;
    }

    public String getInvoiceAmount() {
        return mInvoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        mInvoiceAmount = invoiceAmount;
    }

    public String getMchId() {
        return mMchId;
    }

    public void setMchId(String mchId) {
        mMchId = mchId;
    }

    public String getNonceStr() {
        return mNonceStr;
    }

    public void setNonceStr(String nonceStr) {
        mNonceStr = nonceStr;
    }

    public String getOpenid() {
        return mOpenid;
    }

    public void setOpenid(String openid) {
        mOpenid = openid;
    }

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

    public String getOutTransactionId() {
        return mOutTransactionId;
    }

    public void setOutTransactionId(String outTransactionId) {
        mOutTransactionId = outTransactionId;
    }

    public String getPayResult() {
        return mPayResult;
    }

    public void setPayResult(String payResult) {
        mPayResult = payResult;
    }

    public String getPointAmount() {
        return mPointAmount;
    }

    public void setPointAmount(String pointAmount) {
        mPointAmount = pointAmount;
    }

    public String getReceiptAmount() {
        return mReceiptAmount;
    }

    public void setReceiptAmount(String receiptAmount) {
        mReceiptAmount = receiptAmount;
    }

    public String getResultCode() {
        return mResultCode;
    }

    public void setResultCode(String resultCode) {
        mResultCode = resultCode;
    }

    public String getSign() {
        return mSign;
    }

    public void setSign(String sign) {
        mSign = sign;
    }

    public String getSignType() {
        return mSignType;
    }

    public void setSignType(String signType) {
        mSignType = signType;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTimeEnd() {
        return mTimeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        mTimeEnd = timeEnd;
    }

    public String getTotalFee() {
        return mTotalFee;
    }

    public void setTotalFee(String totalFee) {
        mTotalFee = totalFee;
    }

    public String getTradeType() {
        return mTradeType;
    }

    public void setTradeType(String tradeType) {
        mTradeType = tradeType;
    }

    public String getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(String transactionId) {
        mTransactionId = transactionId;
    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
    }

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        mVersion = version;
    }

}
