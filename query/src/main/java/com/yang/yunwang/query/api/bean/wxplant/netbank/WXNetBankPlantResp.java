
package com.yang.yunwang.query.api.bean.wxplant.netbank;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WXNetBankPlantResp {

    @SerializedName("Code")
    private Long mCode;
    @SerializedName("Data")
    private Data mData;
    @SerializedName("Description")
    private String mDescription;

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

}
