
package com.yang.yunwang.query.api.bean.wxplant.netbank;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WXNetBankPlantReq {

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
