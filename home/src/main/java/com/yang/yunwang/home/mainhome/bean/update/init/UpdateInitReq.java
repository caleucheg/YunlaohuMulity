
package com.yang.yunwang.home.mainhome.bean.update.init;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UpdateInitReq {

    @SerializedName("PagingInfo")
    private PagingInfo mPagingInfo;
    @SerializedName("SysNo")
    private String mSysNo;

    public PagingInfo getPagingInfo() {
        return mPagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        mPagingInfo = pagingInfo;
    }

    public String getSysNo() {
        return mSysNo;
    }

    public void setSysNo(String sysNo) {
        mSysNo = sysNo;
    }

}
