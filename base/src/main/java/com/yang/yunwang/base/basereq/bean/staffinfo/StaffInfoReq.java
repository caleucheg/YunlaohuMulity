
package com.yang.yunwang.base.basereq.bean.staffinfo;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class StaffInfoReq {

    @SerializedName("LoginName")
    private String mLoginName;


    public String getLoginName() {
        return mLoginName;
    }

    public void setLoginName(String loginName) {
        mLoginName = loginName;
    }



}
