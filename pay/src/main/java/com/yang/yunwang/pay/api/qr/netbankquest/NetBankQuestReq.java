
package com.yang.yunwang.pay.api.qr.netbankquest;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NetBankQuestReq {

    @SerializedName("ChannelType")
    private String mChannelType;
    @SerializedName("ReqModel")
    private ReqModel mReqModel;
    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;

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

}
