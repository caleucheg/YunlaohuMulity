
package com.yang.yunwang.query.api.bean.unrefund.netbank;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NetBankUnrefundReq {

    @SerializedName("ChannelType")
    private String mChannelType;
    @SerializedName("ReqModel")
    private ReqModel mReqModel;
    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;
    @SerializedName("Transaction_id")
    private String mTransactionId;

    public String getChannelType() {
        return mChannelType;
    }

    public void setChannelType(String channelType) {
        mChannelType = channelType;
    }

    public ReqModel getReqModel() {
        return mReqModel;
    }

    public void setReqModel(ReqModel reqModel) {
        mReqModel = reqModel;
    }

    public String getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(String systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
    }

    public String getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(String transactionId) {
        mTransactionId = transactionId;
    }

}
