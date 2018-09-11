
package com.yang.yunwang.home.mainhome.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ResetPwdResp {

    @SerializedName("Code")
    private Long mCode;
    @SerializedName("Data")
    private String mData;
    @SerializedName("Description")
    private String mDescription;

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        mData = data;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

}
