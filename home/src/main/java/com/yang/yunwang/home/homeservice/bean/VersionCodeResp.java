
package com.yang.yunwang.home.homeservice.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class VersionCodeResp {

    @SerializedName("SystemName")
    private String mSystemName;
    @SerializedName("Type")
    private String mType;
    @SerializedName("Version")
    private String mVersion;

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

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        mVersion = version;
    }

}
