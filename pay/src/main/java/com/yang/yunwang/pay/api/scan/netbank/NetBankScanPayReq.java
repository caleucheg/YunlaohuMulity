
package com.yang.yunwang.pay.api.scan.netbank;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NetBankScanPayReq {

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
