
package com.yang.yunwang.home.mainhome.bean.homepage.staff;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StaffTotalInfoReq {

    @SerializedName("PayType")
    private Long mPayType;
    @SerializedName("SystemUserSysNo")
    private Long mSystemUserSysNo;
    @SerializedName("Time_end")
    private String mTimeEnd;
    @SerializedName("Time_Start")
    private String mTimeStart;

    public Long getPayType() {
        return mPayType;
    }

    public void setPayType(Long payType) {
        mPayType = payType;
    }

    public Long getSystemUserSysNo() {
        return mSystemUserSysNo;
    }

    public void setSystemUserSysNo(Long systemUserSysNo) {
        mSystemUserSysNo = systemUserSysNo;
    }

    public String getTimeEnd() {
        return mTimeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        mTimeEnd = timeEnd;
    }

    public String getTimeStart() {
        return mTimeStart;
    }

    public void setTimeStart(String timeStart) {
        mTimeStart = timeStart;
    }

}
