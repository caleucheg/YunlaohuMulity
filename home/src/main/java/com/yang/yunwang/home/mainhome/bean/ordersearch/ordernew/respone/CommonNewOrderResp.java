
package com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.respone;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CommonNewOrderResp {

    @SerializedName("Code")
    private Long mCode;
    @SerializedName("Data")
    private Data mData;
    @SerializedName("Description")
    private Object mDescription;

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

    public Object getDescription() {
        return mDescription;
    }

    public void setDescription(Object description) {
        mDescription = description;
    }

}
