
package com.yang.yunwang.pay.api.scan.netbank.wx;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RespInfo {

    @SerializedName("ResultCode")
    private String mResultCode;
    @SerializedName("ResultMsg")
    private String mResultMsg;
    @SerializedName("ResultStatus")
    private String mResultStatus;

    public String getResultCode() {
        return mResultCode;
    }

    public void setResultCode(String resultCode) {
        mResultCode = resultCode;
    }

    public String getResultMsg() {
        return mResultMsg;
    }

    public void setResultMsg(String resultMsg) {
        mResultMsg = resultMsg;
    }

    public String getResultStatus() {
        return mResultStatus;
    }

    public void setResultStatus(String resultStatus) {
        mResultStatus = resultStatus;
    }

}
