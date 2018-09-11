
package com.yang.yunwang.home.homeservice.bean;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class VersionCodeReq {

    @SerializedName("SystemName")
    private String mSystemName;
    @SerializedName("Type")
    private String mType;

    public String getSystemName() {
        return mSystemName;
    }

    public void setSystemName(String systemName) {
        mSystemName = systemName;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
