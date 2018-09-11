
package com.yang.yunwang.home.mainhome.bean.regist;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RegistCommonResp {

    @SerializedName("Code")
    private Long mCode;
    @SerializedName("Data")
    private Boolean mData;
    @SerializedName("Description")
    private String mDescription;

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public Boolean getData() {
        return mData;
    }

    public void setData(Boolean data) {
        mData = data;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

}
