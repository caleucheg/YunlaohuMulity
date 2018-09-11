
package com.yang.yunwang.query.api.bean.merchinfo;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MerchUpdateRateReq {

    @SerializedName("SysNo")
    private String mSysNo;
    @SerializedName("UserRate")
    private String mUserRate;

    public String getSysNo() {
        return mSysNo;
    }

    public void setSysNo(String sysNo) {
        mSysNo = sysNo;
    }

    public String getUserRate() {
        return mUserRate;
    }

    public void setUserRate(String userRate) {
        mUserRate = userRate;
    }

}
