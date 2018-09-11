
package com.yang.yunwang.pay.api.scan.netbank.wx;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ResultData {

    @SerializedName("Attach")
    private String mAttach;
    @SerializedName("BankType")
    private String mBankType;
    @SerializedName("BuyerLogonId")
    private String mBuyerLogonId;
    @SerializedName("BuyerPayAmount")
    private String mBuyerPayAmount;
    @SerializedName("BuyerUserId")
    private String mBuyerUserId;
    @SerializedName("ChannelType")
    private String mChannelType;
    @SerializedName("CouponFee")
    private String mCouponFee;
    @SerializedName("Credit")
    private String mCredit;
    @SerializedName("Currency")
    private String mCurrency;
    @SerializedName("GmtPayment")
    private String mGmtPayment;
    @SerializedName("InvoiceAmount")
    private String mInvoiceAmount;
    @SerializedName("IsSubscribe")
    private String mIsSubscribe;
    @SerializedName("IsvOrgId")
    private String mIsvOrgId;
    @SerializedName("MerchantId")
    private String mMerchantId;
    @SerializedName("MerchantOrderNo")
    private String mMerchantOrderNo;
    @SerializedName("OpenId")
    private String mOpenId;
    @SerializedName("OrderNo")
    private String mOrderNo;
    @SerializedName("OutTradeNo")
    private String mOutTradeNo;
    @SerializedName("PayChannelOrderNo")
    private String mPayChannelOrderNo;
    @SerializedName("ReceiptAmount")
    private String mReceiptAmount;
    @SerializedName("RespInfo")
    private RespInfo mRespInfo;
    @SerializedName("SubAppId")
    private String mSubAppId;
    @SerializedName("SubOpenId")
    private String mSubOpenId;
    @SerializedName("TotalAmount")
    private String mTotalAmount;

    public String getAttach() {
        return mAttach;
    }

    public void setAttach(String attach) {
        mAttach = attach;
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

    public String getChannelType() {
        return mChannelType;
    }

    public void setChannelType(String channelType) {
        mChannelType = channelType;
    }

    public String getCouponFee() {
        return mCouponFee;
    }

    public void setCouponFee(String couponFee) {
        mCouponFee = couponFee;
    }

    public String getCredit() {
        return mCredit;
    }

    public void setCredit(String credit) {
        mCredit = credit;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public String getGmtPayment() {
        return mGmtPayment;
    }

    public void setGmtPayment(String gmtPayment) {
        mGmtPayment = gmtPayment;
    }

    public String getInvoiceAmount() {
        return mInvoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        mInvoiceAmount = invoiceAmount;
    }

    public String getIsSubscribe() {
        return mIsSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        mIsSubscribe = isSubscribe;
    }

    public String getIsvOrgId() {
        return mIsvOrgId;
    }

    public void setIsvOrgId(String isvOrgId) {
        mIsvOrgId = isvOrgId;
    }

    public String getMerchantId() {
        return mMerchantId;
    }

    public void setMerchantId(String merchantId) {
        mMerchantId = merchantId;
    }

    public String getMerchantOrderNo() {
        return mMerchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        mMerchantOrderNo = merchantOrderNo;
    }

    public String getOpenId() {
        return mOpenId;
    }

    public void setOpenId(String openId) {
        mOpenId = openId;
    }

    public String getOrderNo() {
        return mOrderNo;
    }

    public void setOrderNo(String orderNo) {
        mOrderNo = orderNo;
    }

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

    public String getPayChannelOrderNo() {
        return mPayChannelOrderNo;
    }

    public void setPayChannelOrderNo(String payChannelOrderNo) {
        mPayChannelOrderNo = payChannelOrderNo;
    }

    public String getReceiptAmount() {
        return mReceiptAmount;
    }

    public void setReceiptAmount(String receiptAmount) {
        mReceiptAmount = receiptAmount;
    }

    public RespInfo getRespInfo() {
        return mRespInfo;
    }

    public void setRespInfo(RespInfo respInfo) {
        mRespInfo = respInfo;
    }

    public String getSubAppId() {
        return mSubAppId;
    }

    public void setSubAppId(String subAppId) {
        mSubAppId = subAppId;
    }

    public String getSubOpenId() {
        return mSubOpenId;
    }

    public void setSubOpenId(String subOpenId) {
        mSubOpenId = subOpenId;
    }

    public String getTotalAmount() {
        return mTotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        mTotalAmount = totalAmount;
    }

}
