
package com.yang.yunwang.query.api.bean.commonsearch;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class CommonOrdersResp {

    @SerializedName("model")
    private ArrayList<Model> mModel;
    @SerializedName("totalCount")
    private Long mTotalCount;

    public ArrayList<Model> getModel() {
        return mModel;
    }

    public void setModel(ArrayList<Model> model) {
        mModel = model;
    }

    public Long getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(Long totalCount) {
        mTotalCount = totalCount;
    }

}
