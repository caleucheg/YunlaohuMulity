
package com.yang.yunwang.home.mainhome.bean.homepage.services.activie;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ServiceActiveCusReq {

    @SerializedName("CustomersTopSysNo")
    private Long mCustomersTopSysNo;
    @SerializedName("Time_End")
    private String mTimeEnd;
    @SerializedName("Time_Start")
    private String mTimeStart;

    public Long getCustomersTopSysNo() {
        return mCustomersTopSysNo;
    }

    public void setCustomersTopSysNo(Long customersTopSysNo) {
        mCustomersTopSysNo = customersTopSysNo;
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
