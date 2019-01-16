
package com.yang.yunwang.home.mainhome.bean.report.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class CommonReportResp {

    @SerializedName("Code")
    private Long mCode;
    @SerializedName("Data")
    private List<Datum> mData;
    @SerializedName("Description")
    private Object mDescription;

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public List<Datum> getData() {
        return mData;
    }

    public void setData(List<Datum> data) {
        mData = data;
    }

    public Object getDescription() {
        return mDescription;
    }

    public void setDescription(Object description) {
        mDescription = description;
    }

}
