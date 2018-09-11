
package com.yang.yunwang.query.api.bean.personalqr;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PersonalQrReq {

    @SerializedName("DeletClear")
    private Long mDeletClear;
    @SerializedName("SysNo")
    private String mSysNo;

    public Long getDeletClear() {
        return mDeletClear;
    }

    public void setDeletClear(Long deletClear) {
        mDeletClear = deletClear;
    }

    public String getSysNo() {
        return mSysNo;
    }

    public void setSysNo(String sysNo) {
        mSysNo = sysNo;
    }

}
