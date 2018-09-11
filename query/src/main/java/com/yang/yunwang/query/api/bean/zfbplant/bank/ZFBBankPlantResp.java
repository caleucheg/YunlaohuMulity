
package com.yang.yunwang.query.api.bean.zfbplant.bank;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ZFBBankPlantResp {

    @SerializedName("Code")
    private Long code;
    @SerializedName("Data")
    private Data data;
    @SerializedName("Description")
    private String description;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
