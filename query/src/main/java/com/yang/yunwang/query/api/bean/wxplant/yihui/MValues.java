
package com.yang.yunwang.query.api.bean.wxplant.yihui;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MValues {

    @SerializedName("appid")
    private String mAppid;
    @SerializedName("attach")
    private String mAttach;
    @SerializedName("bank_type")
    private String mBankType;
    @SerializedName("cash_fee")
    private String mCashFee;
    @SerializedName("fee_type")
    private String mFeeType;
    @SerializedName("is_subscribe")
    private String mIsSubscribe;
    @SerializedName("mch_id")
    private String mMchId;
    @SerializedName("nonce_str")
    private String mNonceStr;
    @SerializedName("openid")
    private String mOpenid;
    @SerializedName("out_trade_no")
    private String mOutTradeNo;
    @SerializedName("result_code")
    private String mResultCode;
    @SerializedName("return_code")
    private String mReturnCode;
    @SerializedName("return_msg")
    private String mReturnMsg;
    @SerializedName("sign")
    private String mSign;
    @SerializedName("sub_mch_id")
    private String mSubMchId;
    @SerializedName("time_end")
    private String mTimeEnd;
    @SerializedName("total_fee")
    private String mTotalFee;
    @SerializedName("trade_state")
    private String mTradeState;
    @SerializedName("trade_state_desc")
    private String mTradeStateDesc;
    @SerializedName("trade_type")
    private String mTradeType;
    @SerializedName("transaction_id")
    private String mTransactionId;

    public String getAppid() {
        return mAppid;
    }

    public void setAppid(String appid) {
        mAppid = appid;
    }

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

    public String getCashFee() {
        return mCashFee;
    }

    public void setCashFee(String cashFee) {
        mCashFee = cashFee;
    }

    public String getFeeType() {
        return mFeeType;
    }

    public void setFeeType(String feeType) {
        mFeeType = feeType;
    }

    public String getIsSubscribe() {
        return mIsSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        mIsSubscribe = isSubscribe;
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

    public String getResultCode() {
        return mResultCode;
    }

    public void setResultCode(String resultCode) {
        mResultCode = resultCode;
    }

    public String getReturnCode() {
        return mReturnCode;
    }

    public void setReturnCode(String returnCode) {
        mReturnCode = returnCode;
    }

    public String getReturnMsg() {
        return mReturnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        mReturnMsg = returnMsg;
    }

    public String getSign() {
        return mSign;
    }

    public void setSign(String sign) {
        mSign = sign;
    }

    public String getSubMchId() {
        return mSubMchId;
    }

    public void setSubMchId(String subMchId) {
        mSubMchId = subMchId;
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

    public String getTradeState() {
        return mTradeState;
    }

    public void setTradeState(String tradeState) {
        mTradeState = tradeState;
    }

    public String getTradeStateDesc() {
        return mTradeStateDesc;
    }

    public void setTradeStateDesc(String tradeStateDesc) {
        mTradeStateDesc = tradeStateDesc;
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

    @Override
    public String toString() {
        return "MValues{" +
                "mAppid='" + mAppid + '\'' +
                ", mAttach='" + mAttach + '\'' +
                ", mBankType='" + mBankType + '\'' +
                ", mCashFee='" + mCashFee + '\'' +
                ", mFeeType='" + mFeeType + '\'' +
                ", mIsSubscribe='" + mIsSubscribe + '\'' +
                ", mMchId='" + mMchId + '\'' +
                ", mNonceStr='" + mNonceStr + '\'' +
                ", mOpenid='" + mOpenid + '\'' +
                ", mOutTradeNo='" + mOutTradeNo + '\'' +
                ", mResultCode='" + mResultCode + '\'' +
                ", mReturnCode='" + mReturnCode + '\'' +
                ", mReturnMsg='" + mReturnMsg + '\'' +
                ", mSign='" + mSign + '\'' +
                ", mSubMchId='" + mSubMchId + '\'' +
                ", mTimeEnd='" + mTimeEnd + '\'' +
                ", mTotalFee='" + mTotalFee + '\'' +
                ", mTradeState='" + mTradeState + '\'' +
                ", mTradeStateDesc='" + mTradeStateDesc + '\'' +
                ", mTradeType='" + mTradeType + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                '}';
    }
}
