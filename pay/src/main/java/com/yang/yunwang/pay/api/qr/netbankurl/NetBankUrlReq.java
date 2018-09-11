
package com.yang.yunwang.pay.api.qr.netbankurl;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NetBankUrlReq {

    @SerializedName("ReqModel")
    private ReqModel mReqModel;
    @SerializedName("SystemUserSysNo")
    private String mSystemUserSysNo;

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
