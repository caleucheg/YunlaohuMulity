
package com.yang.yunwang.home.mainhome.bean.homepage.serverstaff.active;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ServerStaffActiveCusReq {

    @SerializedName("SystemUserTopSysNo")
    private Long mSystemUserTopSysNo;
    @SerializedName("Time_End")
    private String mTimeEnd;
    @SerializedName("Time_Start")
    private String mTimeStart;

    public Long getSystemUserTopSysNo() {
        return mSystemUserTopSysNo;
    }

    public void setSystemUserTopSysNo(Long systemUserTopSysNo) {
        mSystemUserTopSysNo = systemUserTopSysNo;
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
