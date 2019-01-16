
package com.yang.yunwang.home.mainhome.bean.homepage.serverstaff.active;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("CustomerCount")
    private String mCustomerCount;
    @SerializedName("SystemUserTopSysNo")
    private String mSystemUserTopSysNo;

    public String getCustomerCount() {
        return mCustomerCount;
    }

    public void setCustomerCount(String customerCount) {
        mCustomerCount = customerCount;
    }

    public String getSystemUserTopSysNo() {
        return mSystemUserTopSysNo;
    }

    public void setSystemUserTopSysNo(String systemUserTopSysNo) {
        mSystemUserTopSysNo = systemUserTopSysNo;
    }

}
